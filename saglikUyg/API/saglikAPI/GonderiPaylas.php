<?php 

include("baglanti.php");

if(isset($_POST) || !empty($_POST))
{
    $islem = $_POST["islem"];
    if($islem == "1")
    {
        $foto = $_POST["fotoyol"];
        $baslik = $_POST["baslik"];
        $aciklama = $_POST["aciklama"];
        $kategoriId = $_POST["kategoriId"];
        $kullaniciId = $_POST["kullaniciId"];
        $kullaniciAdSoyad = $_POST["kullaniciAdSoyad"];
        $kullaniciMail = $_POST["kullaniciMail"];
        $kullaniciYas = $_POST["kullaniciYas"];
        $kullaniciCinsiyet = $_POST["kullaniciCinsiyet"];

        $rnd=uniqid();
        $decodeImage=base64_decode("$foto");
        $fotoYol ="fotoGonderi/$rnd.jpg";
        file_put_contents($fotoYol,$decodeImage);

        $sorgu=$conn->prepare("insert into gonderiler(kullaniciId,gonderiFoto,gonderiBaslik,gonderiAciklama,gonderiKategoriId,kullaniciAdSoyad,kullaniciCinsiyet,kullaniciYas,kullaniciMail)
                               values (?,?,?,?,?,?,?,?,?)");
        $sorgu->execute(array($kullaniciId,$fotoYol,$baslik,$aciklama,$kategoriId,$kullaniciAdSoyad,$kullaniciCinsiyet,$kullaniciYas,$kullaniciMail));
        if($sorgu)
        {
            $result["basarili"] = "1";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $result["basarili"] = "01";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    else if($islem == "2")
    {
        $doktorMail = $_POST["drMail"];
        $sorgu2 = $conn->prepare("select * from gonderiler where gonderiKategoriId in(select kategoriId from kategori_doktorlar where doktorId in(select doktorId from doktorlar where mail = ?))");
        $sorgu2->execute(array($doktorMail));
        if($sorgu2->rowCount() > 0)
        {
            $veri = $sorgu2->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
    }
    else if($islem == "3")
    {
        $kullaniciMail = $_POST["kullaniciMail"];
        $sorgu3 = $conn->prepare("select * from gonderiler where kullaniciMail = ?");
        $sorgu3->execute(array($kullaniciMail));
        if($sorgu3->rowCount() > 0)
        {
            $veri = $sorgu3->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
    }
    else if($islem == "4")
    {
        $doktorMail = $_POST["drMail"];
        $sorgu2 = $conn->prepare("select * from gonderiler where gonderiId in(select gonderiId from yorumlar where kullaniciAd = ?)");
        $sorgu2->execute(array($doktorMail));
        if($sorgu2->rowCount() > 0)
        {
            $veri = $sorgu2->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
    }
}
?>