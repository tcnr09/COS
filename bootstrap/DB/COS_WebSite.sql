-- phpMyAdmin SQL Dump
-- version 2.11.10
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2019 年 03 月 11 日 14:00
-- 服务器版本: 5.1.73
-- PHP 版本: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `COS_WebSite`
--
CREATE DATABASE `COS_WebSite` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `COS_WebSite`;

-- --------------------------------------------------------

--
-- 表的结构 `COS_opinion`
--

CREATE TABLE IF NOT EXISTS `COS_opinion` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `clean` int(20) NOT NULL,
  `attitude` int(20) NOT NULL,
  `food` int(20) NOT NULL,
  `advise` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- 导出表中的数据 `COS_opinion`
--

INSERT INTO `COS_opinion` (`ID`, `clean`, `attitude`, `food`, `advise`) VALUES
(1, 3, 3, 3, 't'),
(2, 5, 5, 5, 't'),
(3, 2, 2, 2, 'h'),
(4, 1, 1, 1, 'g'),
(5, 5, 5, 5, 'b'),
(6, 5, 5, 5, 'a'),
(7, 5, 5, 5, 'a'),
(8, 5, 5, 5, 'j'),
(9, 5, 5, 5, 'n'),
(10, 5, 5, 5, 'l'),
(11, 1, 1, 1, 'V');
