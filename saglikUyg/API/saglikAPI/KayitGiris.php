<?php

include("baglanti.php");

if(isset($_POST) and !empty($_POST))
{
    $islem = @$_POST["islem"];

    if($islem == "1")
    {
        $ad = @$_POST["ad"];
        $soyad = @$_POST["soyad"];
        $mail = @$_POST["mail"];
        $sifre = base64_encode(@$_POST["sifre"]);
        $telefon = @$_POST["telefon"];
        $cinsiyet = "boş";
        $ilce = "boş";

        $komut=$conn->prepare("select * from kullanicilar where mail = ?");
        $komut->execute([$mail]);

        if($komut->rowCount() > 0)
        {
            $result["basarili"] = "01";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $xId = uniqid();
            $yas = "0";
            $Kfoto = "saglikAPI/fotoProfil/ppdefault.jpg";
            $kullEkleKomut= $conn->prepare("insert into kullanicilar(kullaniciID,ad,soyad,mail,sifre,telefon,cinsiyet,yas,kullaniciFoto) values(?,?,?,?,?,?,?,?,?)");
            if($kullEkleKomut->execute(array($xId,$ad,$soyad,$mail,$sifre,$telefon,$cinsiyet,$yas,$Kfoto)))
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
    }

    else if($islem == "2")
    {
        $ad = @$_POST["ad"];
        $soyad = @$_POST["soyad"];
        $mail = @$_POST["mail"];
        $sifre =base64_encode(@$_POST["sifre"]);

        $komut=$conn->prepare("select * from onay where mail = ?");
        $komut->execute([$mail]);

        if($komut->rowCount() > 0)
        {
            $result["basarili"] = "02";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $kullEkleKomut= $conn->prepare("insert into onay(ad,soyad,mail,sifre) values(?,?,?,?)");
            if($kullEkleKomut->execute(array($ad,$soyad,$mail,$sifre)))
            {
                $result["basarili"] = "2";
                echo $json=json_encode(array("Deger" => $result));
            }
            else
            {
                $result["basarili"] = "02";
                echo $json=json_encode(array("Deger" => $result));
            }
        }
    }
    else if($islem == "3")
    {
        $mail = @$_POST["mail"];
        $sifre =base64_encode(@$_POST["sifre"]);

        $komut=$conn->prepare("select * from kullanicilar where mail = ? and sifre = ?");
        $komut->execute(array($mail,$sifre));
        if($komut->rowCount() > 0)
        {
            $result["basarili"] = "3";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $result["basarili"] = "03";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    else if($islem == "4")
    {
        $mail = @$_POST["mail"];
        $sifre =base64_encode(@$_POST["sifre"]);

        $komut=$conn->prepare("select * from doktorlar where mail = ? and sifre = ?");
        $komut->execute(array($mail,$sifre));
        if($komut->rowCount() > 0)
        {
            $result["basarili"] = "4";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $komut2=$conn->prepare("select * from onay where mail = ? and sifre = ?");
            $komut2->execute(array($mail,$sifre));
            if($komut2->rowCount() > 0)
            {
                $result["basarili"] = "044";
                echo $json=json_encode(array("Deger" => $result));
            }
            else
            {
                $result["basarili"] = "04";
                echo $json=json_encode(array("Deger" => $result));
            }

        }
    }
    
}

?>