-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 18 Ağu 2021, 23:12:27
-- Sunucu sürümü: 5.6.17
-- PHP Sürümü: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Veritabanı: `db_saglik`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `doktorlar`
--

CREATE TABLE IF NOT EXISTS `doktorlar` (
  `doktorId` int(11) NOT NULL,
  `ad` varchar(30) COLLATE utf8_turkish_ci NOT NULL,
  `soyAd` varchar(30) COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(25) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(25) COLLATE utf8_turkish_ci NOT NULL,
  `telefon` varchar(11) COLLATE utf8_turkish_ci NOT NULL,
  `cinsiyet` varchar(5) COLLATE utf8_turkish_ci NOT NULL,
  `doktorFoto` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`doktorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `doktorlar`
--

INSERT INTO `doktorlar` (`doktorId`, `ad`, `soyAd`, `mail`, `sifre`, `telefon`, `cinsiyet`, `doktorFoto`) VALUES
(1, 'deneme', 'doktor', 'deneme@mail.com', 'MTIzMTIz', '5469736815', 'Erkek', 'saglikAPI/fotoProfil/61182d64a9c47.jpg');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `gonderiler`
--

CREATE TABLE IF NOT EXISTS `gonderiler` (
  `gonderiId` int(11) NOT NULL AUTO_INCREMENT,
  `kullaniciId` int(11) NOT NULL,
  `gonderiFoto` varchar(150) COLLATE utf8_turkish_ci NOT NULL,
  `gonderiBaslik` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `gonderiAciklama` varchar(150) COLLATE utf8_turkish_ci NOT NULL,
  `gonderiKategoriId` int(11) NOT NULL,
  `kullaniciAdSoyad` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `kullaniciCinsiyet` varchar(10) COLLATE utf8_turkish_ci NOT NULL,
  `kullaniciYas` varchar(10) COLLATE utf8_turkish_ci NOT NULL,
  `kullaniciMail` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`gonderiId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=4 ;

--
-- Tablo döküm verisi `gonderiler`
--

INSERT INTO `gonderiler` (`gonderiId`, `kullaniciId`, `gonderiFoto`, `gonderiBaslik`, `gonderiAciklama`, `gonderiKategoriId`, `kullaniciAdSoyad`, `kullaniciCinsiyet`, `kullaniciYas`, `kullaniciMail`) VALUES
(3, 61182, 'fotoGonderi/61182d2e56377.jpg', 'akne sorunum', 'denem sorun akne 123', 8, 'Berkhan Akdag', 'boş', '0', 'berkhan@gmail.com');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ilaclar`
--

CREATE TABLE IF NOT EXISTS `ilaclar` (
  `ilacId` int(11) NOT NULL AUTO_INCREMENT,
  `kullaniciId` int(11) NOT NULL,
  `ilacAd` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `ilacKullanim` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `ilacKullanimAralik` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `baslangicTarih` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`ilacId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kategori`
--

CREATE TABLE IF NOT EXISTS `kategori` (
  `kategoriId` int(11) NOT NULL AUTO_INCREMENT,
  `kategoriAd` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`kategoriId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=21 ;

--
-- Tablo döküm verisi `kategori`
--

INSERT INTO `kategori` (`kategoriId`, `kategoriAd`) VALUES
(1, 'Belirsiz'),
(2, 'Ağız ve Diş Sağlığı'),
(3, 'Beslenme ve Diyet'),
(4, 'Cildiye'),
(5, 'Çocuk Sağlığı ve Hastalıkları'),
(6, 'Dahiliye'),
(7, 'Deri Hastaliklari'),
(8, 'Dermatoloji ve Kozmetoloji'),
(9, 'Estetik, Plastik ve Rekonstrüktif Cerrahi'),
(10, 'Fizik Tedavi ve Rehabilitasyon'),
(11, 'Genel Cerrahi'),
(12, 'Göğüs Hastalıkları'),
(13, 'İç Hastalıkları'),
(14, 'Kadın Hastalıkları ve Doğum'),
(15, 'Lazer Epilasyon'),
(16, 'Obezite, Diyabet ve Metabolik Cerrahi'),
(17, 'Omurga Sorunları'),
(18, 'Psikiyatri'),
(19, 'Uyku Bozuklukları'),
(20, 'Yenidoğan');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kategori_doktorlar`
--

CREATE TABLE IF NOT EXISTS `kategori_doktorlar` (
  `kategoriId` int(11) NOT NULL,
  `doktorId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `kategori_doktorlar`
--

INSERT INTO `kategori_doktorlar` (`kategoriId`, `doktorId`) VALUES
(1, 8),
(8, 1);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kullanicilar`
--

CREATE TABLE IF NOT EXISTS `kullanicilar` (
  `kullaniciID` int(11) NOT NULL,
  `ad` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `soyad` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `telefon` varchar(11) COLLATE utf8_turkish_ci NOT NULL,
  `cinsiyet` varchar(5) COLLATE utf8_turkish_ci NOT NULL,
  `yas` varchar(10) COLLATE utf8_turkish_ci NOT NULL,
  `ilce` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  `kullaniciFoto` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`kullaniciID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `kullanicilar`
--

INSERT INTO `kullanicilar` (`kullaniciID`, `ad`, `soyad`, `mail`, `sifre`, `telefon`, `cinsiyet`, `yas`, `ilce`, `kullaniciFoto`) VALUES
(61182, 'Berkhan', 'Akdag', 'berkhan@gmail.com', 'MTIzMTIz', '05469736815', 'Erkek', '21', '', 'saglikAPI/fotoProfil/61182cc796473.jpg');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `onay`
--

CREATE TABLE IF NOT EXISTS `onay` (
  `onayId` int(11) NOT NULL AUTO_INCREMENT,
  `ad` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `soyad` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(40) COLLATE utf8_turkish_ci NOT NULL,
  `sifre` varchar(20) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`onayId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `saglik`
--

CREATE TABLE IF NOT EXISTS `saglik` (
  `saglikId` int(11) NOT NULL AUTO_INCREMENT,
  `kullaniciId` int(11) NOT NULL,
  `buyukT` varchar(5) COLLATE utf8_turkish_ci NOT NULL,
  `kucukT` varchar(5) COLLATE utf8_turkish_ci NOT NULL,
  `kalpA` varchar(5) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`saglikId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `spor`
--

CREATE TABLE IF NOT EXISTS `spor` (
  `sporId` int(11) NOT NULL AUTO_INCREMENT,
  `sporAd` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `sporKalori` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `sporMail` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  PRIMARY KEY (`sporId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `yorumlar`
--

CREATE TABLE IF NOT EXISTS `yorumlar` (
  `yorumId` int(11) NOT NULL AUTO_INCREMENT,
  `kullaniciId` int(11) NOT NULL,
  `kullaniciAd` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `yorum` varchar(200) COLLATE utf8_turkish_ci NOT NULL,
  `kullaniciFoto` varchar(100) COLLATE utf8_turkish_ci NOT NULL,
  `gonderiId` int(11) NOT NULL,
  PRIMARY KEY (`yorumId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci AUTO_INCREMENT=23 ;

--
-- Tablo döküm verisi `yorumlar`
--

INSERT INTO `yorumlar` (`yorumId`, `kullaniciId`, `kullaniciAd`, `yorum`, `kullaniciFoto`, `gonderiId`) VALUES
(21, 61182, 'berkhan@gmail.com', 'acil yardm', 'saglikAPI/fotoProfil/61182cc796473.jpg', 3),
(22, 1, 'deneme@mail.com', 'xxx kremi deneyin', 'saglikAPI/fotoProfil/61182d64a9c47.jpg', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
