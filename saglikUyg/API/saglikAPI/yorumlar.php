<?php
include("baglanti.php");

if(isset($_POST) || !empty($_POST))
{
    $islem=$_POST["islem"];

    if($islem=="1")
    {
        $gonderiId = $_POST["gonderiId"];
        $tip = $_POST["tip"];
        $kullaniciAd = $_POST["kullaniciAd"];
        $yorum = $_POST["yorum"];
        $kullaniciFoto;
        $kullanciId;

        if($tip == "1")
        {
            $komut=$conn->prepare("select * from kullanicilar where mail = ?");
            $komut->execute(array($kullaniciAd));
            $veri = $komut->fetch(PDO::FETCH_ASSOC);
            $kullaniciFoto = $veri["kullaniciFoto"];
            $kullanciId = $veri["kullaniciID"];
        }
        else if($tip =="2")
        {
            $komut2=$conn->prepare("select * from doktorlar where mail = ?");
            $komut2->execute(array($kullaniciAd));
            $veri = $komut2->fetch(PDO::FETCH_ASSOC);
            $kullaniciFoto = $veri["doktorFoto"];
            $kullanciId = $veri["doktorId"];
        }

        $komut3=$conn->prepare("insert into yorumlar(kullaniciId,kullaniciAd,yorum,kullaniciFoto,gonderiId) values(?,?,?,?,?)");
        $komut3->execute(array($kullanciId,$kullaniciAd,$yorum,$kullaniciFoto,$gonderiId));

        if($komut3)
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
    else if($islem=="2")
    {
        $gonderiId = $_POST["gonderiId"];

        $komut=$conn->prepare("select * from yorumlar where gonderiId = ?");
        $komut->execute(array($gonderiId));
        if($komut->rowCount() > 0)
        {
            $veri = $komut->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
        
    }
}
?>