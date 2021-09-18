<?php

include("baglanti.php");
if(isset($_POST) || !empty($_POST))
{
    $islem = $_POST["islem"];
    $tip = $_POST["tip"];
    $mail = $_POST["mail"];
    if($islem == "1")
    {
        if($tip == "1")
        {
            $komut=$conn->prepare("select * from yorumlar where kullaniciAd = ?");
            $komut->execute(array($mail));
            $yorumlar = $komut->fetch(PDO::FETCH_ASSOC);

            $komut2=$conn->prepare("select * from gonderiler where kullaniciMail = ?");
            $komut2->execute(array($mail));
            $gonderiler = $komut2->fetch(PDO::FETCH_ASSOC);

            $komut3=$conn->prepare("select * from kullanicilar where mail = ?");
            $komut3->execute(array($mail));
            $bilgiler = $komut3->fetch(PDO::FETCH_ASSOC);
            
            $etkilesim =$komut->rowCount() + $komut2->rowCount();
            $result["basarili"] = "1";
            $result["ad"] = $bilgiler["ad"];
            $result["soyad"] = $bilgiler["soyad"];
            $result["sifre"] = base64_decode($bilgiler["sifre"]);
            $result["telefon"] = $bilgiler["telefon"];
            $result["cinsiyet"] = $bilgiler["cinsiyet"];
            $result["yas"] = $bilgiler["yas"];
            $result["foto"] = $bilgiler["kullaniciFoto"];
            $result["kId"] = $bilgiler["kullaniciID"];
            $result["etkilesim"] = $etkilesim;
            $result["paylasimS"] =$komut2->rowCount();

            echo $json=json_encode(array("Deger" => $result));
            
        }
        else if($tip == "2")
        {
            $komut=$conn->prepare("select * from yorumlar where kullaniciAd = ?");
            $komut->execute(array($mail));
            if($komut->rowCount()>0)
            {
                $etkilesim =$komut->rowCount();
            }
            else
            {
                $etkilesim =0;
            }
            
            $komut2=$conn->prepare("select kategoriAd from kategori where kategoriId in(select kategoriId from kategori_doktorlar where doktorId in(select doktorId from doktorlar where mail = ?))");
            $komut2->execute(array($mail));
            if($komut2->rowCount()>0)
            {
                $kategori = $komut2->fetch(PDO::FETCH_ASSOC);
                $result["uzmanlik"] = $kategori["kategoriAd"];
            }
            else
            {
                $result["uzmanlik"] ="bos";
               
            }
            
            $komut3=$conn->prepare("select * from doktorlar where mail = ?");
            $komut3->execute(array($mail));
            $bilgiler = $komut3->fetch(PDO::FETCH_ASSOC);

            
            $result["basarili"] = "1";
            $result["ad"] = $bilgiler["ad"];
            $result["soyad"] = $bilgiler["soyAd"];
            $result["sifre"] = base64_decode($bilgiler["sifre"]);
            $result["telefon"] = $bilgiler["telefon"];
            $result["cinsiyet"] = $bilgiler["cinsiyet"];
            $result["foto"] = $bilgiler["doktorFoto"];
            $result["kId"] = $bilgiler["doktorId"];
            $result["etkilesim"] = $etkilesim;
            
            
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    else if($islem == "2")
    {
        if($tip == "1")
        {
            $ad = $_POST["ad"];
            $soyad = $_POST["soyad"];
            $sifre =base64_encode($_POST["sifre"]);
            $telefon = $_POST["telefon"];
            $cinsiyet = $_POST["cinsiyet"];
            $yas = $_POST["yas"];
            $komut=$conn->prepare("update kullanicilar set ad=?,soyad=?,sifre=?,telefon=?,cinsiyet=?,yas=? where mail = ?");
            $komut->execute(array($ad,$soyad,$sifre,$telefon,$cinsiyet,$yas,$mail));
            if($komut)
            {
                $result["basarili"] = "1";
                echo $json=json_encode(array("Deger" => $result));
            }
        }
        else if($tip == "2")
        {
        
            $sifre =base64_encode($_POST["sifre"]);
            $telefon = $_POST["telefon"];
            $cinsiyet = $_POST["cinsiyet"];
            $kategori = $_POST["kategori"];
            $kullaniciId = $_POST["kulId"];
            $komut=$conn->prepare("update doktorlar set sifre=?,telefon=?,cinsiyet=? where mail = ?");
            $komut->execute(array($sifre,$telefon,$cinsiyet,$mail));
            if($komut)
            {
                $komut2=$conn->prepare("select * from kategori_doktorlar where doktorId = ?");
                $komut2->execute(array($kullaniciId));
                if($komut2->rowCount() > 0)
                {
                    $komu3=$conn->prepare("update kategori_doktorlar set kategoriId=? where doktorId = ?");
                    $komu3->execute(array($kategori,$kullaniciId));
                    $result["basarili"] = "1";
                    echo $json=json_encode(array("Deger" => $result));
                }
                else
                {
                    $komu4=$conn->prepare("insert into kategori_doktorlar(kategoriId,doktorId) values(?,?)");
                    $komu4->execute(array($kategori,$kullaniciId));
                    $result["basarili"] = "1";
                    echo $json=json_encode(array("Deger" => $result));
                } 
            }
        }
    }
    else if($islem == "3")
    {
        if($tip == "1")
        {
            
            if(!empty($_POST["foto"]))
            {
                $foto = $_POST["foto"];
                $rnd=uniqid();
                $decodeImage=base64_decode("$foto");
                $fotoYol ="fotoProfil/$rnd.jpg";
                $fotoYol2 ="saglikAPI/fotoProfil/$rnd.jpg";
                file_put_contents($fotoYol,$decodeImage);
                $komut=$conn->prepare("update kullanicilar set kullaniciFoto=? where mail = ?");
                $komut->execute(array($fotoYol2,$mail));
                if($komut)
                {
                    $result["basarili"] = "1";
                    echo $json=json_encode(array("Deger" => $result));
                }
            }
            
            
        }
        else if($tip == "2")
        {
            if(!empty($_POST["foto"]))
            {
                $foto = $_POST["foto"];
                $rnd=uniqid();
                $decodeImage=base64_decode("$foto");
                $fotoYol ="fotoProfil/$rnd.jpg";
                $fotoYol2 ="saglikAPI/fotoProfil/$rnd.jpg";
                file_put_contents($fotoYol,$decodeImage);
                $komut=$conn->prepare("update doktorlar set doktorFoto=? where mail = ?");
                $komut->execute(array($fotoYol2,$mail));
                if($komut)
                {
                    $result["basarili"] = "1";
                    echo $json=json_encode(array("Deger" => $result));
                }
                
            }
        }
    }
}

?>