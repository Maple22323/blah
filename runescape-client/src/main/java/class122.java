import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("do")
public class class122 {
   @ObfuscatedName("i")
   int[] field1860;

   public class122(int[] var1) {
      int var2;
      for(var2 = 1; var2 <= var1.length + (var1.length >> 1); var2 <<= 1) {
         ;
      }

      this.field1860 = new int[var2 + var2];

      int var3;
      for(var3 = 0; var3 < var2 + var2; ++var3) {
         this.field1860[var3] = -1;
      }

      int var4;
      for(var3 = 0; var3 < var1.length; this.field1860[var4 + var4 + 1] = var3++) {
         for(var4 = var1[var3] & var2 - 1; this.field1860[var4 + var4 + 1] != -1; var4 = var4 + 1 & var2 - 1) {
            ;
         }

         this.field1860[var4 + var4] = var1[var3];
      }

   }

   @ObfuscatedName("i")
   public int method2287(int var1) {
      int var2 = (this.field1860.length >> 1) - 1;
      int var3 = var1 & var2;

      while(true) {
         int var4 = this.field1860[var3 + var3 + 1];
         if(var4 == -1) {
            return -1;
         }

         if(this.field1860[var3 + var3] == var1) {
            return var4;
         }

         var3 = var3 + 1 & var2;
      }
   }
}
