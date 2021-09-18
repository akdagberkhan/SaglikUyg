<?php

include("baglanti.php");
if(isset($_POST) || !empty($_POST))
{
    $islem=$_POST["islem"];
    if($islem=="1")
    {
        $mail = $_POST["mail"];
        $haraketAdi = $_POST["haraketAdi"];
        $tahminiKalori = $_POST["tahminiKalori"];

        $komut = $conn->prepare("insert into spor(sporAd,sporKalori,sporMail) values(?,?,?)");
        $komut->execute(array($haraketAdi,$tahminiKalori,$mail));
        if($komut)
        {
            $result["basarili"] = "1";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
}


?>