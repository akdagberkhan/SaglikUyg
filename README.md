# Proje Amacı

Kullanıcı eğer bir hastalığı ya da sorunu var ise kategori(diyetisyen, dermotoloji) paylaşımını yaparak bu alanda ki doktorların anasayfasına düşmesini
sağlıyor ve doktor yorumu ve tavsiyesi alabiliyor. Aynı zaman da ilaç, spor düzeni oluşturmak için kendine liste hazırlayabiliyor.

## Kullanılan Kütüphaneler

 `implementation 'com.android.volley:volley:1.2.0'`  
 `implementation 'com.squareup.picasso:picasso:2.71828'`

Picasso kütüphanesini sunucumdan fotoğrafları almak ve belirlediğim nesnelerimde göstermek için kullandım.
Volley kütüphanesini ise API bağlantısını kurmak veri Post edip almak üzere projeme dahil ettim.

## Kullandığım Teknolojiler

Projemde Api yazmak için PHP kullandım. Veritabanım için MySQL ve bunlara mobil uygulamamdan bağlanmak için Volley kütüphanesini
dahil etim.

Api aracılığı ile mobil uygulamamdan aldığım verileri veritabanına kaydettim ve gerekli kontrolleri sağladım. Fotoğrafları uzak klosörden almak için
Picasso kütüphanesini ve sınıfını kullandım.

Anasayfa tasarımım için drawer menu kullandım bunun yanında bazı sayfalar için Fragment kullandım. Paylaşımlar ve Yorumalrı görüntülemek için 
RecyclerView kullandım.

## Bazı Görüntüler

![Anasayfa](https://i.www.hizliresim.com/5g8nkk8.jpg)

![Gönderi Paylaş](https://i.www.hizliresim.com/8nkmfxf.jpg)

![Gönderi Detay ve Yorumlar](https://i.hizliresim.com/c8sdzol.JPG)
