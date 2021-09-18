<?php
$mysqlsunucu = "localhost";
$mysqlkullanici = "root";
$mysqlsifre = "";
$baglankontrol = 0;
try {
    $conn = new PDO("mysql:host=$mysqlsunucu;dbname=db_saglik", $mysqlkullanici, $mysqlsifre);
	  $conn->exec("SET NAMES 'utf8'; SET CHARSET 'utf8'");
    
		$baglankontrol=1;
    }
catch(PDOException $e)
    {
		$baglankontrol=0;
    }
?>