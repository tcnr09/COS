-- phpMyAdmin SQL Dump
-- version 2.11.10
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 建立日期: Feb 18, 2019, 10:14 AM
-- 伺服器版本: 5.1.73
-- PHP 版本: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫: `my_db`
--
CREATE DATABASE `my_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `my_db`;

-- --------------------------------------------------------

--
-- 資料表格式： `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(50) NOT NULL,
  `price` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `img` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- 列出以下資料庫的數據： `exam`
--

INSERT INTO `exam` (`ID`, `Title`, `price`, `description`, `img`) VALUES
(1, '國際牌1400W負離子速乾型吹風機 EH-NE60', 1590, '●風量選擇：二段式  ●溫度：冷,暖,熱風三種  ◆50度柔風溫控  ◆冷,熱雙溫出風口', '01.jpg'),
(2, 'Panasonic 國際牌負離子吹風機 EH-NE57-A(藍色)', 1490, '■ 負離子高效速乾  ■ 可折疊收納  ■ 三段溫度  ■ 二段風量', '02.jpg'),
(3, 'Panasonic 國際牌 奈米水離子吹風機 EH-NA27', 2090, '■ 奈米水離子  ■ 速乾美學  ■ 冷、暖、熱三段溫度  ■ 輕巧保濕 攜帶方便', '03.jpg'),
(4, 'Panasonic 國際牌負離子吹風機 EH-NE73-P(粉)', 1690, '■ 溫冷雙風出風口設計  ■ 雙負離子  ■ 三段溫度選擇  ■ 二段速冷定型開關  ■ 附速乾吹嘴', '04.jpg'),
(5, 'Panasonic 國際牌 大風量負離子吹風機 EH-NE57', 1390, '■ 1400W大風量快速乾髮  ■ 外置負離子裝置，有效提供保濕因子  ■ 速乾美學  ■ 二段式風', '05.jpg'),
(6, 'Panasonic 國際牌 負離子速乾型冷熱吹風機 EH-NE11', 799, '■速乾護髮吹嘴  ■外置負離子設計  ■二段式風量  ■可折疊式設計  ■冷、暖、熱三段溫度', '06.jpg'),
(7, 'Panasonic國際牌奈米水離子吹風機 EH-NA98/RP', 6290, '■ 智慧溫控  ■ 奈米水離子及雙倍礦物負離子  ■ 三段風速  ■ 四種美髮美肌模式  ■ 內建速', '07.jpg'),
(8, '國際牌雙負離子三段溫度吹風機EH-NE41', 850, '■ 雙負離子  ■ 冷、暖、熱三段溫度  ■ 全新花色及花漾外觀設計  ■ 外置雙負離子外置負離子出', '08.jpg'),
(9, 'Panasonic 國際牌奈米水離子吹風機 EH-NA45/RP(紅)', 3690, '■最新白金負離子抗UV防曬科技  ■奈米水離子潤澤滲透護髮科技  ■抑制紫外線對秀髮的影響  ■14', '09.jpg'),
(10, 'Panasonic 國際牌負離子吹風機 EH-NE14', 599, '■保濕負離子  ■冷、暖、熱三段溫度  ■全新花色及花漾外觀設計  ■可折疊式設計收納超方便', '10.jpg'),
(11, 'Panasonic 國際牌奈米水離子吹風機 EH-NA45/RP', 3680, '■ 首創吹髮同時護髮  ■ 白金負離子生成  ■ 兩段式風量  ■ 三種溫度調節  ■ 50度柔風溫', '11.jpg'),
(12, 'Panasonic 國際牌奈米水離子吹風機 EH-NA45/RP', 3680, '■ 日本狂銷，奈米水離子技術  ■ 首創吹髮同時護髮  ■ 白金負離子生成  ■ 兩段式風量  ■ ', '12.jpg');

-- --------------------------------------------------------

--
-- 資料表格式： `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Bday` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `Create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- 列出以下資料庫的數據： `members`
--

INSERT INTO `members` (`ID`, `Username`, `Password`, `Bday`, `Sex`, `Create_time`) VALUES
(1, 'Tom', '123456', '2019/02/18', 'male', '2019-02-18 09:57:52'),
(2, 'pjker', '12456789', '2019-02-18', '男生', '2019-02-18 10:00:57');

-- --------------------------------------------------------

--
-- 資料表格式： `persons`
--

CREATE TABLE IF NOT EXISTS `persons` (
  `personID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(15) DEFAULT NULL,
  `LastName` varchar(15) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  PRIMARY KEY (`personID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- 列出以下資料庫的數據： `persons`
--

INSERT INTO `persons` (`personID`, `FirstName`, `LastName`, `Age`) VALUES
(8, 'P', 'Q', 22),
(2, 'Gleen', 'Wuagmire', 33),
(12, 'sdf', 'sdf', 12);

-- --------------------------------------------------------

--
-- 資料表格式： `register`
--

CREATE TABLE IF NOT EXISTS `register` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Alias` varchar(20) NOT NULL,
  `Email` varchar(20) NOT NULL,
  `Tel` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 列出以下資料庫的數據： `register`
--

