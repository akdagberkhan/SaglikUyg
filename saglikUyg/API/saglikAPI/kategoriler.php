<?php 

include("baglanti.php");

if(isset($_POST) || !empty($_POST))
{
    if($_POST["islem"] == "10")
    {
        $komut2=$conn->prepare("select * from kategori");
        $komut2->execute();
        if($komut2->rowCount() > 0)
        {
            $veri = $komut2->fetchAll(PDO::FETCH_ASSOC);
            echo $json=json_encode($veri);
        }
        else
        {
            $result["basarili"] = "02";
            echo $json=json_encode(array("Deger" => $result));
        }
    }
    
}


?>