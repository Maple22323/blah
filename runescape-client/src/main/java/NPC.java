import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cm")
@Implements("NPC")
public final class NPC extends Actor {
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "Lia;"
   )
   @Export("composition")
   NPCComposition composition;

   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "(IBI)V",
      garbageValue = "-1334440997"
   )
   final void method1701(int var1, byte var2) {
      int var3 = super.pathX[0];
      int var4 = super.pathY[0];
      if(var1 == 0) {
         --var3;
         ++var4;
      }

      if(var1 == 1) {
         ++var4;
      }

      if(var1 == 2) {
         ++var3;
         ++var4;
      }

      if(var1 == 3) {
         --var3;
      }

      if(var1 == 4) {
         ++var3;
      }

      if(var1 == 5) {
         --var3;
         --var4;
      }

      if(var1 == 6) {
         --var4;
      }

      if(var1 == 7) {
         ++var3;
         --var4;
      }

      if(super.animation != -1 && NPCComposition.getAnimation(super.animation).priority == 1) {
         super.animation = -1;
      }

      if(super.queueSize < 9) {
         ++super.queueSize;
      }

      for(int var5 = super.queueSize; var5 > 0; --var5) {
         super.pathX[var5] = super.pathX[var5 - 1];
         super.pathY[var5] = super.pathY[var5 - 1];
         super.field1258[var5] = super.field1258[var5 - 1];
      }

      super.pathX[0] = var3;
      super.pathY[0] = var4;
      super.field1258[0] = var2;
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      signature = "(IIZI)V",
      garbageValue = "-380509994"
   )
   final void method1694(int var1, int var2, boolean var3) {
      if(super.animation != -1 && NPCComposition.getAnimation(super.animation).priority == 1) {
         super.animation = -1;
      }

      if(!var3) {
         int var4 = var1 - super.pathX[0];
         int var5 = var2 - super.pathY[0];
         if(var4 >= -8 && var4 <= 8 && var5 >= -8 && var5 <= 8) {
            if(super.queueSize < 9) {
               ++super.queueSize;
            }

            for(int var6 = super.queueSize; var6 > 0; --var6) {
               super.pathX[var6] = super.pathX[var6 - 1];
               super.pathY[var6] = super.pathY[var6 - 1];
               super.field1258[var6] = super.field1258[var6 - 1];
            }

            super.pathX[0] = var1;
            super.pathY[0] = var2;
            super.field1258[0] = 1;
            return;
         }
      }

      super.queueSize = 0;
      super.field1213 = 0;
      super.field1267 = 0;
      super.pathX[0] = var1;
      super.pathY[0] = var2;
      super.x = super.field1221 * 64 + super.pathX[0] * 128;
      super.y = super.field1221 * 64 + super.pathY[0] * 128;
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(B)Leh;",
      garbageValue = "0"
   )
   protected final Model getModel() {
      if(this.composition == null) {
         return null;
      } else {
         Sequence var1 = super.animation != -1 && super.actionAnimationDisable == 0?NPCComposition.getAnimation(super.animation):null;
         Sequence var2 = super.poseAnimation != -1 && (super.poseAnimation != super.idlePoseAnimation || var1 == null)?NPCComposition.getAnimation(super.poseAnimation):null;
         Model var3 = this.composition.method4728(var1, super.actionFrame, var2, super.poseFrame);
         if(var3 == null) {
            return null;
         } else {
            var3.method2558();
            super.field1228 = var3.modelHeight;
            if(super.graphic != -1 && super.field1215 != -1) {
               Model var4 = class244.getSpotAnimType(super.graphic).method4404(super.field1215);
               if(var4 != null) {
                  var4.method2569(0, -super.field1250, 0);
                  Model[] var5 = new Model[]{var3, var4};
                  var3 = new Model(var5, 2);
               }
            }

            if(this.composition.field3555 == 1) {
               var3.field1936 = true;
            }

            return var3;
         }
      }
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      signature = "(I)Z",
      garbageValue = "246459077"
   )
   @Export("hasConfig")
   final boolean hasConfig() {
      return this.composition != null;
   }

   @ObfuscatedName("w")
   @ObfuscatedSignature(
      signature = "(B)[Ljv;",
      garbageValue = "2"
   )
   static class277[] method1698() {
      return new class277[]{class277.field3749, class277.field3745, class277.field3744};
   }
}
