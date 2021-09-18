<?php
include("baglanti.php");

if(isset($_POST) && !empty($_POST))
{
    $islem = $_POST["islem"];
    if($islem == "1")
    {
        $bTansiyon = $_POST["bTansiyon"];
        $kTansiyon = $_POST["kTansiyon"];
        $kAtis = $_POST["kAtis"];
        $kMail = $_POST["kMail"];

        $komut1=$conn->prepare("select * from saglik where kullaniciId in(select kullaniciID from kullanicilar where mail=?)");
        $komut1->execute(array($kMail));
        if($komut1->rowCount() > 0)
        {
            $komut3=$conn->prepare("update saglik set buyukT=? , kucukT=? , kalpA=? where kullaniciId in(select kullaniciID from kullanicilar where mail=?)");
            $komut3->execute(array($bTansiyon,$kTansiyon,$kAtis,$kMail));
            if($komut3)
            {
                $result["basarili"] = "1";
                echo $json=json_encode(array("Deger" => $result));
            }
        }
        else
        {
            $komut = $conn->prepare("select kullaniciID from kullanicilar where mail = ?");
            $komut->execute(array($kMail));
            $veri = $komut->fetch(PDO::FETCH_ASSOC);
            $kId = $veri["kullaniciID"];

            $komut2 = $conn->prepare("insert into saglik(kullaniciId,buyukT,kucukT,kalpA) values(?,?,?,?)");
            $komut2->execute(array($kId,$bTansiyon,$kTansiyon,$kAtis));
            if($komut2)
            {
                $result["basarili"] = "1";
                echo $json=json_encode(array("Deger" => $result));
            }
        }
    }
    if($islem == "2")
    {
        $ilacAd = $_POST["ilacAd"];
        $ilacKullanim = $_POST["ilacKullanim"];
        $ilacKullanimAralik = $_POST["ilacKullanimA"];
        $baslangicTarih = $_POST["baslangicTarih"];
        $kMail = $_POST["kMail"];


        $komut = $conn->prepare("select kullaniciID from kullanicilar where mail = ?");
        $komut->execute(array($kMail));
        $veri = $komut->fetch(PDO::FETCH_ASSOC);
        $kId = $veri["kullaniciID"];

        $komut1=$conn->prepare("insert into ilaclar(kullaniciId,ilacAd,ilacKullanim,ilacKullanimAralik,baslangicTarih) values(?,?,?,?,?)");
        $komut1->execute(array($kId,$ilacAd,$ilacKullanim,$ilacKullanimAralik,$baslangicTarih));
        if($komut1)
        {
            $result["basarili"] = "1";
            echo $json=json_encode(array("Deger" => $result));
        }
        else
        {
            $result["basarili"] = "1";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    if($islem == "3")
    {
       
        $kMail = $_POST["kMail"];

        $komut=$conn->prepare("select * from ilaclar where kullaniciId in(select kullaniciID from kullanicilar where mail = ?)");
        $komut->execute(array($kMail));
        if($komut->rowCount() > 0)
        {
            $veri = $komut->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
    }
    if($islem == "4")
    {
       
        $kMail = $_POST["kMail"];

        $komut=$conn->prepare("select * from saglik where kullaniciId in(select kullaniciID from kullanicilar where mail = ?)");
        $komut->execute(array($kMail));
        if($komut->rowCount() > 0)
        {
            $veri = $komut->fetchAll(PDO::FETCH_ASSOC);
            print_r(json_encode($veri));
        }
    }
}
?>