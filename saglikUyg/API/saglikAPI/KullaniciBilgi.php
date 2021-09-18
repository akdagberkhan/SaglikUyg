<?php 

include("baglanti.php");

if(isset($_POST) || !empty($_POST))
{
    if($_POST["tip"] == "1")
    {
        $komut=$conn->prepare("select * from kullanicilar where mail = ?");
        $komut->execute(array($_POST["mail"]));
        if($komut->rowCount() > 0)
        {
            $veri = $komut->fetch(PDO::FETCH_ASSOC);
            $result["kulId"] = $veri["kullaniciID"];
            $result["ad"] = $veri["ad"];
            $result["soyad"] = $veri["soyad"];
            $result["sifre"] = base64_encode($veri["sifre"]);
            $result["telefon"] = $veri["telefon"];
            $result["cinsiyet"] = $veri["cinsiyet"];
            $result["yas"] = $veri["yas"];
            $result["kullaniciFoto"] = $veri["kullaniciFoto"];
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $result["basarili"] = "01";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    else if($_POST["tip"] == "2")
    {
        $komut2=$conn->prepare("select * from doktorlar where mail = ?");
        $komut2->execute(array($_POST["mail"]));
        if($komut2->rowCount() > 0)
        {
            $veri = $komut2->fetch(PDO::FETCH_ASSOC);
            $result["kulId"] = $veri["doktorId"];
            $result["ad"] = $veri["ad"];
            $result["soyad"] = $veri["soyAd"];
            $result["sifre"] = base64_encode($veri["sifre"]);
            $result["telefon"] = $veri["telefon"];
            $result["cinsiyet"] = $veri["cinsiyet"];
            $result["kullaniciFoto"] = $veri["doktorFoto"];
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $result["basarili"] = "02";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    
}


?>