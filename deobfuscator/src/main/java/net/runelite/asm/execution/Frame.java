/*
 * Copyright (c) 2016, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by Adam <Adam@sigterm.info>
 * 4. Neither the name of the Adam <Adam@sigterm.info> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Adam <Adam@sigterm.info> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Adam <Adam@sigterm.info> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.asm.execution;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.runelite.asm.Method;
import net.runelite.asm.attributes.Code;
import net.runelite.asm.attributes.code.Exception;
import net.runelite.asm.attributes.code.Instruction;
import net.runelite.asm.attributes.code.Instructions;
import net.runelite.asm.attributes.code.Label;
import net.runelite.asm.attributes.code.instruction.types.InvokeInstruction;
import net.runelite.asm.attributes.code.instruction.types.MappableInstruction;
import net.runelite.asm.attributes.code.instructions.InvokeStatic;

public class Frame
{
	private Execution execution;
	private Method method;
	private boolean executing = true;
	private Instruction cur; // current instruction
	private Stack stack;
	private Variables variables;
	private List<InstructionContext> instructions = new ArrayList<>(); // instructions executed in this frame
	private MethodContext ctx;
	protected Method nonStatic; // next non static method up the stack
	public Frame other; // in the other execution for mapping
	public Frame returnTo; // is this the same as caller?
	public Frame otherStatic;

	public Frame(Execution execution, Method method)
	{
		this.execution = execution;
		this.method = method;
		
		Code code = method.getCode();

		stack = new Stack(code.getMaxStack());
		variables = new Variables(code.getMaxLocals());
		ctx = new MethodContext(execution, method);
		nonStatic = method;
	}

	public Frame(Execution execution, Method method, Instruction i)
	{
		this.execution = execution;
		this.method = method;

		Code code = method.getCode();

		stack = new Stack(code.getMaxStack());
		variables = new Variables(code.getMaxLocals());

		ctx = new MethodContext(execution, method);
		nonStatic = method;

		cur = i;
	}

	@Override
	public String toString()
	{
		return "Frame{" + "cur=" + cur + ", last=" + this.lastInstruction() + "}";
	}
	
	public void initialize()
	{
		// initialize LVT
		int pos = 0;
		if (!method.isStatic())
			variables.set(pos++, new VariableContext(new Type(method.getMethods().getClassFile().getName())).markParameter());
		
		//NameAndType nat = method.getNameAndType();
		for (int i = 0; i < method.getDescriptor().size(); ++i)
		{
			variables.set(pos, new VariableContext(new Type(method.getDescriptor().getTypeOfArg(i))).markParameter());
			pos += method.getDescriptor().getTypeOfArg(i).getSlots();
		}
		
		Code code = method.getCode();
		cur = code.getInstructions().getInstructions().get(0);
	}
	
	public void initialize(InstructionContext ctx)
	{
		// initialize frame from invoking context
		assert ctx.getInstruction() instanceof InvokeInstruction;

		// this assert fails. evidently it's possible to invokevirtual a static method.
		//assert ctx.getInstruction() instanceof InvokeStatic == this.method.isStatic();
		
		if (this.getMethod().isStatic())
		{
			this.nonStatic = ctx.getFrame().nonStatic;
		}
		
		// initialize LVT. the last argument is popped first, and is at arguments[0]
		List<StackContext> pops = ctx.getPops();
		pops = Lists.reverse(new ArrayList<>(pops)); // reverse the list so first argument is at index 0
		
		int lvtOffset = 0;
		if (!(ctx.getInstruction() instanceof InvokeStatic))
		{
			StackContext s = pops.remove(0); // object
			
			// sometimes there are invokevirtuals on static methods. still must pop the object from the stack,
			// but don't set as a parameter
			if (!method.isStatic())
			{
				VariableContext vctx = new VariableContext(ctx, s);
				vctx.markParameter();

				variables.set(lvtOffset++, vctx);
			}
		}

		for (int i = 0; i < method.getDescriptor().size(); ++i)
		{
			StackContext argument = pops.remove(0);

			VariableContext vctx = new VariableContext(ctx, argument);
			vctx.markParameter();

			variables.set(lvtOffset, vctx);
			lvtOffset += method.getDescriptor().getTypeOfArg(i).getSlots();
		}
		
		assert pops.isEmpty();
		
		Code code = method.getCode();
		cur = code.getInstructions().getInstructions().get(0);
	}

	protected Frame(Frame other)
	{
		this.execution = other.execution;
		this.method = other.method;
		this.executing = other.executing;
		this.cur = other.cur;
		this.stack = new Stack(other.stack);
		this.variables = new Variables(other.variables);
		this.ctx = other.ctx;
		this.nonStatic = other.nonStatic;
		if (other.returnTo != null)
		{
			this.returnTo = new Frame(other.returnTo);
			this.returnTo.instructions.addAll(other.returnTo.instructions);
		}
		this.otherStatic = other.otherStatic;
	}
	
	public Frame dup()
	{
		Frame other = new Frame(this);
		execution.addFrame(other);
		return other;
	}
	
	public void stop()
	{
		executing = false;
	}
	
	public Execution getExecution()
	{
		return execution;
	}

	public boolean isExecuting()
	{
		return executing;
	}
	
	public Method getMethod()
	{
		return method;
	}
	
	public Stack getStack()
	{
		return stack;
	}
	
	public Variables getVariables()
	{
		return variables;
	}

	public MethodContext getMethodCtx()
	{
		return ctx;
	}
	
	private void addInstructionContext(InstructionContext i)
	{
		instructions.add(i);
	}
	
	public List<InstructionContext> getInstructions()
	{
		return instructions;
	}
	
	public void execute()
	{
		Instructions ins = method.getCode().getInstructions();
		List<Instruction> instructions = ins.getInstructions();
		
		assert !execution.paused;
		
		while (executing)
		{
			Instruction oldCur = cur;
			InstructionContext ictx;
			
			try
			{
				ictx = cur.execute(this);
				this.addInstructionContext(ictx);
			}
			catch (Throwable ex)
			{
				System.err.println("Error executing instruction " + cur);
				System.err.println("Frame stack (grows downward):");
				while (stack.getSize() > 0)
				{
					StackContext stacki = stack.pop();
					InstructionContext pushed = stacki.getPushed();
					Frame frame = pushed.getFrame();
					
					System.err.println(pushed.getInstruction());
				}
				System.err.println("end of stack");
				ex.printStackTrace();
				throw ex;
			}
			
			assert ictx.getInstruction() == oldCur;
			ctx.contexts.put(oldCur, ictx);
			
			execution.executed.add(oldCur);

			execution.accept(ictx);
			
			processExceptions(ictx);
			
			if (!executing)
			{
				// this can be mappable, too, but we stop executing anyway
				checkMappable(ictx); // to set paused
				break;
			}
			
			if (oldCur == cur)
			{
				this.nextInstruction();
			}
			else
			{
				/* jump */
			}
			
			// it is important we move cur first as when the step executor
			// resumes it will start there
			if (checkMappable(ictx))
				return;
		}
	}
	
	private boolean checkMappable(InstructionContext ictx)
	{
		if (execution.step && ictx.getInstruction() instanceof MappableInstruction)
		{
			MappableInstruction mi = (MappableInstruction) ictx.getInstruction();
			if (mi.canMap(ictx))
			{
				execution.paused = true;
				return true;
			}
		}

		return false;
	}
	
	public void nextInstruction()
	{
		Instructions ins = method.getCode().getInstructions();
		List<Instruction> instructions = ins.getInstructions();
		
		int idx = instructions.indexOf(cur);
		assert idx != -1;
		cur = instructions.get(idx + 1);
	}
	
	private InstructionContext lastInstruction()
	{
		return instructions.isEmpty() ? null : instructions.get(instructions.size() - 1);
	}
	
	private void processExceptions(InstructionContext ictx)
	{
		if (this.execution.step)
			return; // no frame.other
		
		Code code = method.getCode();
		
		for (Exception e : code.getExceptions().getExceptions())
		{
			if (e.getStart().next() == ictx.getInstruction())
			{				
				Frame f = dup();
				Stack stack = f.getStack();
				
				while (stack.getSize() > 0)
					stack.pop();
				
				InstructionContext ins = new InstructionContext(ictx.getInstruction(), f);
				StackContext ctx = new StackContext(ins, new Type("java/lang/Exception"), Value.UNKNOWN);
				stack.push(ctx);
				
				ins.push(ctx);
			
				f.jump(ictx, e.getHandler());
			}
		}
	}
	
	public void jump(InstructionContext from, Label to)
	{
		assert to != null;
		assert to.getInstructions() == method.getCode().getInstructions();
		assert method.getCode().getInstructions().getInstructions().contains(to);
		
		if (ctx.hasJumped(from, to))
		{
			executing = false;
			return;
		}
		
		cur = to.next();
	}
}
