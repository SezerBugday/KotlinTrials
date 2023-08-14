package com.sezer.tipcalculator

class GenericDeneme<T>(s: T, val zorluk: EnumDeneme) {
    var sezer :T? = null

      //EnumDeneme sınıfından bir
    // değisken olustur

    fun Yazdir(sez : T): T
    {
        return sez
    }
  // Generic classlarda bu sınıftan bir nesne oluşturduğumuzda her
// bir çağrırmada farklı türle değişken verebiliriz.
    // Örneğin var sinif = GenericDeneme("asa")
    // var sinif2 = GenericDeneme(true) bize aynı classa farklı türde parametreler atama şansı veriyor.
}