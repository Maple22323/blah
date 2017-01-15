import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hr")
@Implements("Rasterizer2D")
public class class219 extends CacheableNode {
   @ObfuscatedName("ag")
   @Export("graphicsPixelsHeight")
   public static int graphicsPixelsHeight;
   @ObfuscatedName("b")
   @Export("graphicsPixels")
   public static int[] graphicsPixels;
   @ObfuscatedName("af")
   protected static int field3171 = 0;
   @ObfuscatedName("al")
   public static int field3172 = 0;
   @ObfuscatedName("an")
   public static int field3173 = 0;
   @ObfuscatedName("aj")
   @Export("graphicsPixelsWidth")
   public static int graphicsPixelsWidth;
   @ObfuscatedName("aa")
   protected static int field3175 = 0;

   @ObfuscatedName("cn")
   @Export("setRasterBuffer")
   public static void setRasterBuffer(int[] var0, int var1, int var2) {
      graphicsPixels = var0;
      graphicsPixelsWidth = var1;
      graphicsPixelsHeight = var2;
      method3965(0, 0, var1, var2);
   }

   @ObfuscatedName("cz")
   public static void method3964() {
      field3171 = 0;
      field3172 = 0;
      field3175 = graphicsPixelsWidth;
      field3173 = graphicsPixelsHeight;
   }

   @ObfuscatedName("cw")
   public static void method3965(int var0, int var1, int var2, int var3) {
      if(var0 < 0) {
         var0 = 0;
      }

      if(var1 < 0) {
         var1 = 0;
      }

      if(var2 > graphicsPixelsWidth) {
         var2 = graphicsPixelsWidth;
      }

      if(var3 > graphicsPixelsHeight) {
         var3 = graphicsPixelsHeight;
      }

      field3171 = var0;
      field3172 = var1;
      field3175 = var2;
      field3173 = var3;
   }

   @ObfuscatedName("ck")
   public static void method3967(int[] var0) {
      var0[0] = field3171;
      var0[1] = field3172;
      var0[2] = field3175;
      var0[3] = field3173;
   }

   @ObfuscatedName("cf")
   public static void method3968(int[] var0) {
      field3171 = var0[0];
      field3172 = var0[1];
      field3175 = var0[2];
      field3173 = var0[3];
   }

   @ObfuscatedName("dh")
   public static void method3975(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = 0;
      int var9 = 65536 / var3;
      int var10 = var6;
      int var11 = 256 - var6;
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var8 += (field3172 - var1) * var9;
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var12 = var4 & 16711680;
      int var13 = var4 & '\uff00';
      int var14 = var4 & 255;
      int var15 = var12 * var6 >> 8;
      int var16 = var13 * var6 >> 8;
      int var17 = var14 * var6 >> 8;
      int var18 = graphicsPixelsWidth - var2;
      int var19 = var0 + var1 * graphicsPixelsWidth;

      for(int var20 = 0; var20 < var3; ++var20) {
         int var21;
         int var22;
         int var23;
         for(var21 = -var2; var21 < 0; ++var21) {
            var22 = graphicsPixels[var19];
            var23 = var22 & 16711680;
            int var24 = var23 >= var12?var23:(var11 == 0?var12:var15 + (var23 * var11 >> 8) & 16711680);
            int var25 = var22 & '\uff00';
            int var26 = var25 >= var13?var25:(var11 == 0?var13:var16 + (var25 * var11 >> 8) & '\uff00');
            int var27 = var22 & 255;
            int var28 = var27 >= var14?var27:(var11 == 0?var14:var17 + (var27 * var11 >> 8));
            graphicsPixels[var19++] = var24 + var26 + var28;
         }

         if(var9 > 0) {
            var8 += var9;
            var21 = 65536 - var8 >> 8;
            var22 = var8 >> 8;
            if(var6 != var7) {
               var10 = var6 * (65536 - var8) + var7 * var8 >> 16;
               var11 = 256 - var10;
            }

            if(var4 != var5) {
               var23 = ((var4 & 16711935) * var21 + (var5 & 16711935) * var22 & -16711936) + ((var4 & '\uff00') * var21 + (var5 & '\uff00') * var22 & 16711680) >>> 8;
               var12 = var23 & 16711680;
               var13 = var23 & '\uff00';
               var14 = var23 & 255;
               var15 = var12 * var10 >> 8;
               var16 = var13 * var10 >> 8;
               var17 = var14 * var10 >> 8;
            }
         }

         var19 += var18;
      }

   }

   @ObfuscatedName("ct")
   public static void method3976(int var0, int var1, int var2, int var3, int var4, int var5) {
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      var4 = ((var4 & 16711935) * var5 >> 8 & 16711935) + ((var4 & '\uff00') * var5 >> 8 & '\uff00');
      int var6 = 256 - var5;
      int var7 = graphicsPixelsWidth - var2;
      int var8 = var0 + var1 * graphicsPixelsWidth;

      for(int var9 = 0; var9 < var3; ++var9) {
         for(int var10 = -var2; var10 < 0; ++var10) {
            int var11 = graphicsPixels[var8];
            var11 = ((var11 & 16711935) * var6 >> 8 & 16711935) + ((var11 & '\uff00') * var6 >> 8 & '\uff00');
            graphicsPixels[var8++] = var4 + var11;
         }

         var8 += var7;
      }

   }

   @ObfuscatedName("dl")
   public static void method3977(int var0, int var1, int var2, int var3, int var4) {
      method3979(var0, var1, var2, var4);
      method3979(var0, var1 + var3 - 1, var2, var4);
      method3981(var0, var1, var3, var4);
      method3981(var0 + var2 - 1, var1, var3, var4);
   }

   @ObfuscatedName("ds")
   public static void method3978(int var0, int var1, int var2, int var3, int var4, int var5) {
      method3980(var0, var1, var2, var4, var5);
      method3980(var0, var1 + var3 - 1, var2, var4, var5);
      if(var3 >= 3) {
         method3982(var0, var1 + 1, var3 - 2, var4, var5);
         method3982(var0 + var2 - 1, var1 + 1, var3 - 2, var4, var5);
      }

   }

   @ObfuscatedName("dj")
   public static void method3979(int var0, int var1, int var2, int var3) {
      if(var1 >= field3172 && var1 < field3173) {
         if(var0 < field3171) {
            var2 -= field3171 - var0;
            var0 = field3171;
         }

         if(var0 + var2 > field3175) {
            var2 = field3175 - var0;
         }

         int var4 = var0 + var1 * graphicsPixelsWidth;

         for(int var5 = 0; var5 < var2; ++var5) {
            graphicsPixels[var4 + var5] = var3;
         }
      }

   }

   @ObfuscatedName("de")
   static void method3980(int var0, int var1, int var2, int var3, int var4) {
      if(var1 >= field3172 && var1 < field3173) {
         if(var0 < field3171) {
            var2 -= field3171 - var0;
            var0 = field3171;
         }

         if(var0 + var2 > field3175) {
            var2 = field3175 - var0;
         }

         int var5 = 256 - var4;
         int var6 = (var3 >> 16 & 255) * var4;
         int var7 = (var3 >> 8 & 255) * var4;
         int var8 = (var3 & 255) * var4;
         int var9 = var0 + var1 * graphicsPixelsWidth;

         for(int var10 = 0; var10 < var2; ++var10) {
            int var11 = (graphicsPixels[var9] >> 16 & 255) * var5;
            int var12 = (graphicsPixels[var9] >> 8 & 255) * var5;
            int var13 = (graphicsPixels[var9] & 255) * var5;
            int var14 = (var6 + var11 >> 8 << 16) + (var7 + var12 >> 8 << 8) + (var8 + var13 >> 8);
            graphicsPixels[var9++] = var14;
         }
      }

   }

   @ObfuscatedName("dn")
   public static void method3981(int var0, int var1, int var2, int var3) {
      if(var0 >= field3171 && var0 < field3175) {
         if(var1 < field3172) {
            var2 -= field3172 - var1;
            var1 = field3172;
         }

         if(var1 + var2 > field3173) {
            var2 = field3173 - var1;
         }

         int var4 = var0 + var1 * graphicsPixelsWidth;

         for(int var5 = 0; var5 < var2; ++var5) {
            graphicsPixels[var4 + var5 * graphicsPixelsWidth] = var3;
         }
      }

   }

   @ObfuscatedName("dp")
   static void method3982(int var0, int var1, int var2, int var3, int var4) {
      if(var0 >= field3171 && var0 < field3175) {
         if(var1 < field3172) {
            var2 -= field3172 - var1;
            var1 = field3172;
         }

         if(var1 + var2 > field3173) {
            var2 = field3173 - var1;
         }

         int var5 = 256 - var4;
         int var6 = (var3 >> 16 & 255) * var4;
         int var7 = (var3 >> 8 & 255) * var4;
         int var8 = (var3 & 255) * var4;
         int var9 = var0 + var1 * graphicsPixelsWidth;

         for(int var10 = 0; var10 < var2; ++var10) {
            int var11 = (graphicsPixels[var9] >> 16 & 255) * var5;
            int var12 = (graphicsPixels[var9] >> 8 & 255) * var5;
            int var13 = (graphicsPixels[var9] & 255) * var5;
            int var14 = (var6 + var11 >> 8 << 16) + (var7 + var12 >> 8 << 8) + (var8 + var13 >> 8);
            graphicsPixels[var9] = var14;
            var9 += graphicsPixelsWidth;
         }
      }

   }

   @ObfuscatedName("do")
   public static void method3983(int var0, int var1, int var2, int var3, int var4) {
      var2 -= var0;
      var3 -= var1;
      if(var3 == 0) {
         if(var2 >= 0) {
            method3979(var0, var1, var2 + 1, var4);
         } else {
            method3979(var0 + var2, var1, -var2 + 1, var4);
         }
      } else if(var2 == 0) {
         if(var3 >= 0) {
            method3981(var0, var1, var3 + 1, var4);
         } else {
            method3981(var0, var1 + var3, -var3 + 1, var4);
         }
      } else {
         if(var2 + var3 < 0) {
            var0 += var2;
            var2 = -var2;
            var1 += var3;
            var3 = -var3;
         }

         int var5;
         int var6;
         if(var2 > var3) {
            var1 <<= 16;
            var1 += '耀';
            var3 <<= 16;
            var5 = (int)Math.floor((double)var3 / (double)var2 + 0.5D);
            var2 += var0;
            if(var0 < field3171) {
               var1 += var5 * (field3171 - var0);
               var0 = field3171;
            }

            if(var2 >= field3175) {
               var2 = field3175 - 1;
            }

            while(var0 <= var2) {
               var6 = var1 >> 16;
               if(var6 >= field3172 && var6 < field3173) {
                  graphicsPixels[var0 + var6 * graphicsPixelsWidth] = var4;
               }

               var1 += var5;
               ++var0;
            }
         } else {
            var0 <<= 16;
            var0 += '耀';
            var2 <<= 16;
            var5 = (int)Math.floor((double)var2 / (double)var3 + 0.5D);
            var3 += var1;
            if(var1 < field3172) {
               var0 += var5 * (field3172 - var1);
               var1 = field3172;
            }

            if(var3 >= field3173) {
               var3 = field3173 - 1;
            }

            while(var1 <= var3) {
               var6 = var0 >> 16;
               if(var6 >= field3171 && var6 < field3175) {
                  graphicsPixels[var6 + var1 * graphicsPixelsWidth] = var4;
               }

               var0 += var5;
               ++var1;
            }
         }
      }

   }

   @ObfuscatedName("dw")
   public static void method3984(int var0, int var1, int var2, int[] var3, int[] var4) {
      int var5 = var0 + var1 * graphicsPixelsWidth;

      for(var1 = 0; var1 < var3.length; ++var1) {
         int var6 = var5 + var3[var1];

         for(var0 = -var4[var1]; var0 < 0; ++var0) {
            graphicsPixels[var6++] = var2;
         }

         var5 += graphicsPixelsWidth;
      }

   }

   @ObfuscatedName("du")
   @ObfuscatedSignature(
      signature = "(IIIIII)V",
      garbageValue = "9135624"
   )
   public static void method3991(int var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = 0;
      int var7 = 65536 / var3;
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var6 += (field3172 - var1) * var7;
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var8 = graphicsPixelsWidth - var2;
      int var9 = var0 + var1 * graphicsPixelsWidth;

      for(int var10 = -var3; var10 < 0; ++var10) {
         int var11 = 65536 - var6 >> 8;
         int var12 = var6 >> 8;
         int var13 = ((var4 & 16711935) * var11 + (var5 & 16711935) * var12 & -16711936) + ((var4 & '\uff00') * var11 + (var5 & '\uff00') * var12 & 16711680) >>> 8;

         for(int var14 = -var2; var14 < 0; ++var14) {
            graphicsPixels[var9++] = var13;
         }

         var9 += var8;
         var6 += var7;
      }

   }

   @ObfuscatedName("cc")
   public static void method3993(int var0, int var1, int var2, int var3, int var4) {
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var5 = graphicsPixelsWidth - var2;
      int var6 = var0 + var1 * graphicsPixelsWidth;

      for(int var7 = -var3; var7 < 0; ++var7) {
         for(int var8 = -var2; var8 < 0; ++var8) {
            graphicsPixels[var6++] = var4;
         }

         var6 += var5;
      }

   }

   @ObfuscatedName("ca")
   public static void method4000(int var0, int var1, int var2, int var3) {
      if(field3171 < var0) {
         field3171 = var0;
      }

      if(field3172 < var1) {
         field3172 = var1;
      }

      if(field3175 > var2) {
         field3175 = var2;
      }

      if(field3173 > var3) {
         field3173 = var3;
      }

   }

   @ObfuscatedName("cx")
   public static void method4007() {
      int var0 = 0;

      int var1;
      for(var1 = graphicsPixelsWidth * graphicsPixelsHeight - 7; var0 < var1; graphicsPixels[var0++] = 0) {
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
         graphicsPixels[var0++] = 0;
      }

      for(var1 += 7; var0 < var1; graphicsPixels[var0++] = 0) {
         ;
      }

   }

   @ObfuscatedName("dk")
   public static void method4015(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = 0;
      int var9 = var4 == var5 && var6 == var7?-1:65536 / var3;
      int var10 = var6;
      int var11 = 256 - var6;
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var8 += (field3172 - var1) * var9;
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var12 = var4 >> 16;
      int var13 = (var4 & '\uff00') >> 8;
      int var14 = var4 & 255;
      int var15 = graphicsPixelsWidth - var2;
      int var16 = var0 + var1 * graphicsPixelsWidth;

      for(int var17 = 0; var17 < var3; ++var17) {
         int var18;
         int var19;
         int var20;
         for(var18 = -var2; var18 < 0; ++var18) {
            var19 = graphicsPixels[var16];
            var20 = var19 >> 16;
            int var21 = (var19 & '\uff00') >> 8;
            int var22 = var19 & 255;
            int var23;
            int var24;
            int var25;
            if(var11 == 0) {
               var23 = var20 < 127?var12 * var20 >> 7:255 - ((255 - var12) * (255 - var20) >> 7);
               var24 = var21 < 127?var13 * var21 >> 7:255 - ((255 - var13) * (255 - var21) >> 7);
               var25 = var22 < 127?var14 * var22 >> 7:255 - ((255 - var14) * (255 - var22) >> 7);
            } else {
               var23 = var20 < 127?(var12 * var20 * var10 >> 7) + var20 * var11 >> 8:(255 - ((255 - var12) * (255 - var20) >> 7)) * var10 + var20 * var11 >> 8;
               var24 = var21 < 127?(var13 * var21 * var10 >> 7) + var21 * var11 >> 8:(255 - ((255 - var13) * (255 - var21) >> 7)) * var10 + var21 * var11 >> 8;
               var25 = var22 < 127?(var14 * var22 * var10 >> 7) + var22 * var11 >> 8:(255 - ((255 - var14) * (255 - var22) >> 7)) * var10 + var22 * var11 >> 8;
            }

            graphicsPixels[var16++] = (var23 << 16) + (var24 << 8) + var25;
         }

         if(var9 > 0) {
            var8 += var9;
            var18 = 65536 - var8 >> 8;
            var19 = var8 >> 8;
            if(var6 != var7) {
               var10 = var6 * (65536 - var8) + var7 * var8 >> 16;
               var11 = 256 - var10;
            }

            if(var4 != var5) {
               var20 = ((var4 & 16711935) * var18 + (var5 & 16711935) * var19 & -16711936) + ((var4 & '\uff00') * var18 + (var5 & '\uff00') * var19 & 16711680) >>> 8;
               var12 = var20 >> 16;
               var13 = (var20 & '\uff00') >> 8;
               var14 = var20 & 255;
            }
         }

         var16 += var15;
      }

   }

   @ObfuscatedName("di")
   public static void method4042(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = 0;
      int var9 = 65536 / var3;
      int var10 = var6;
      int var11 = 256 - var6;
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var8 += (field3172 - var1) * var9;
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var12 = var4 & 16711680;
      int var13 = var4 & '\uff00';
      int var14 = var4 & 255;
      int var15 = var12 * var6 >> 8;
      int var16 = var13 * var6 >> 8;
      int var17 = var14 * var6 >> 8;
      int var18 = graphicsPixelsWidth - var2;
      int var19 = var0 + var1 * graphicsPixelsWidth;

      for(int var20 = 0; var20 < var3; ++var20) {
         int var21;
         int var22;
         int var23;
         for(var21 = -var2; var21 < 0; ++var21) {
            var22 = graphicsPixels[var19];
            var23 = var22 & 16711680;
            int var24 = var23 <= var12?var23:(var11 == 0?var12:var15 + (var23 * var11 >> 8) & 16711680);
            int var25 = var22 & '\uff00';
            int var26 = var25 <= var13?var25:(var11 == 0?var13:var16 + (var25 * var11 >> 8) & '\uff00');
            int var27 = var22 & 255;
            int var28 = var27 <= var14?var27:(var11 == 0?var14:var17 + (var27 * var11 >> 8));
            graphicsPixels[var19++] = var24 + var26 + var28;
         }

         if(var9 > 0) {
            var8 += var9;
            var21 = 65536 - var8 >> 8;
            var22 = var8 >> 8;
            if(var6 != var7) {
               var10 = var6 * (65536 - var8) + var7 * var8 >> 16;
               var11 = 256 - var10;
            }

            if(var4 != var5) {
               var23 = ((var4 & 16711935) * var21 + (var5 & 16711935) * var22 & -16711936) + ((var4 & '\uff00') * var21 + (var5 & '\uff00') * var22 & 16711680) >>> 8;
               var12 = var23 & 16711680;
               var13 = var23 & '\uff00';
               var14 = var23 & 255;
               var15 = var12 * var10 >> 8;
               var16 = var13 * var10 >> 8;
               var17 = var14 * var10 >> 8;
            }
         }

         var19 += var18;
      }

   }

   @ObfuscatedName("dd")
   public static void method4044(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = 0;
      int var9 = var4 == var5 && var6 == var7?-1:65536 / var3;
      int var10 = var6;
      int var11 = 256 - var6;
      int var12 = var4;
      if(var0 < field3171) {
         var2 -= field3171 - var0;
         var0 = field3171;
      }

      if(var1 < field3172) {
         var8 += (field3172 - var1) * var9;
         var3 -= field3172 - var1;
         var1 = field3172;
      }

      if(var0 + var2 > field3175) {
         var2 = field3175 - var0;
      }

      if(var1 + var3 > field3173) {
         var3 = field3173 - var1;
      }

      int var13 = graphicsPixelsWidth - var2;
      int var14 = var0 + var1 * graphicsPixelsWidth;

      for(int var15 = -var3; var15 < 0; ++var15) {
         int var16;
         int var17;
         for(var16 = -var2; var16 < 0; ++var16) {
            var17 = graphicsPixels[var14];
            int var18 = var12 + var17;
            int var19 = (var12 & 16711935) + (var17 & 16711935);
            int var20 = (var19 & 16777472) + (var18 - var19 & 65536);
            if(var11 == 0) {
               graphicsPixels[var14++] = var18 - var20 | var20 - (var20 >>> 8);
            } else {
               int var21 = var18 - var20 | var20 - (var20 >>> 8);
               graphicsPixels[var14++] = ((var21 & 16711935) * var10 >> 8 & 16711935) + ((var21 & '\uff00') * var10 >> 8 & '\uff00') + ((var17 & 16711935) * var11 >> 8 & 16711935) + ((var17 & '\uff00') * var11 >> 8 & '\uff00');
            }
         }

         if(var9 > 0) {
            var8 += var9;
            var16 = 65536 - var8 >> 8;
            var17 = var8 >> 8;
            if(var6 != var7) {
               var10 = var6 * (65536 - var8) + var7 * var8 >> 16;
               var11 = 256 - var10;
            }

            if(var4 != var5) {
               var12 = ((var4 & 16711935) * var16 + (var5 & 16711935) * var17 & -16711936) + ((var4 & '\uff00') * var16 + (var5 & '\uff00') * var17 & 16711680) >>> 8;
            }
         }

         var14 += var13;
      }

   }
}
