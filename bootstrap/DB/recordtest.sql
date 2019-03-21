-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-03-11 06:03:09
-- 服务器版本： 10.3.13-MariaDB
-- PHP 版本： 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `id7769595_friends`
--

-- --------------------------------------------------------

--
-- 表的结构 `recordtest`
--

CREATE TABLE `recordtest` (
  `recordID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Item` varchar(999) COLLATE utf8_unicode_ci NOT NULL,
  `Price` int(11) NOT NULL,
  `Tel` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `recordtest`
--

INSERT INTO `recordtest` (`recordID`, `Date`, `Item`, `Price`, `Tel`) VALUES
(1, '2018-12-02', '水果蛋糕', 1500, '0911111111'),
(2, '2018-12-05', '咖啡，義大利麵', 1000, '0911111111'),
(3, '2018-12-08', '冰咖啡，早餐A', 550, '0911111111'),
(4, '2018-12-09', '套餐A，拿鐵', 700, '0911111111'),
(5, '2018-12-18', '熱咖啡，套餐C', 800, '0911111111'),
(6, '2018-12-20', '美式冰咖啡，套餐E', 700, '0922222222'),
(7, '2018-12-25', '拿鐵，早餐E', 650, '0911111111'),
(8, '2018-12-30', '冰咖啡，早餐D', 700, '0922222222'),
(9, '2019-01-05', '套餐C，早餐E，冰咖啡，拿鐵', 800, '0922222222'),
(10, '2019-01-10', '套餐B，早餐A，冰咖啡，拿鐵', 900, '0911111111');

--
-- 转储表的索引
--

--
-- 表的索引 `recordtest`
--
ALTER TABLE `recordtest`
  ADD PRIMARY KEY (`recordID`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `recordtest`
--
ALTER TABLE `recordtest`
  MODIFY `recordID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
