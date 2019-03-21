-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-03-11 06:02:34
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
-- 表的结构 `COSmember`
--

CREATE TABLE `COSmember` (
  `ID` int(11) NOT NULL,
  `Username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Bday` date NOT NULL,
  `Sex` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `Mail` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Tel` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Permission` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `COSmember`
--

INSERT INTO `COSmember` (`ID`, `Username`, `Password`, `Bday`, `Sex`, `Mail`, `Tel`, `Permission`, `CreateTime`) VALUES
(1, 'admin', 'admin12345678', '2019-03-11', '男', '0', '0', '0', '2019-03-11 05:59:08'),
(2, 'aabbcc', '12345678', '2019-03-11', '男', 'fdafaf@gmail.com', '0988777111', '1', '2019-03-11 05:59:16'),
(3, 'aabbccdd', '12345678', '2019-03-11', '男', 'safad@gmail.com', '0977111666', '1', '2019-03-11 05:59:22'),
(4, 'OKgood', '12345678', '2019-03-21', '女', 'dfadfad@gmail.com', '0922333888', '1', '2019-03-11 05:59:31'),
(5, 'pjker', '12345678', '2019-03-11', '男', 'T@T.com', '123456789', '1', '2019-03-11 05:59:35'),
(6, 'qqwwqq', '12345678', '2019-03-14', '女', 'afad@gmail.com', '0988774441', '1', '2019-03-11 05:59:38'),
(7, 'qqaazz', '12345678', '2019-03-22', '女', 'dfaadsf@gmail.com', '0955222111', '1', '2019-03-11 05:59:41'),
(8, 'bbggbb', '12345678', '2019-03-30', '女', 'bsbfg@gmail.com', '0988777444', '1', '2019-03-11 05:59:44'),
(9, 'aaaaa', '12345678', '2019-03-22', '男', 'aaa@gmail.com', '1234567890', '1', '2019-03-11 05:59:48'),
(10, 'nnbbnn', '12345678', '2019-03-13', '女', 'nnn@gmail.com', '0966333222', '1', '2019-03-11 05:59:51'),
(11, 'bbvvbb', '12345678', '2019-03-23', '女', 'bbbb@gmail.com', '0988444111', '1', '2019-03-11 05:59:54'),
(12, 'pppppp', '12345678', '2019-03-14', '女', 'ppp@gmail.com', '0966332211', '1', '2019-03-11 05:59:57'),
(13, 'nnnppp', '12345678', '2019-03-31', '女', 'pop@gmail.com', '0977111444', '1', '2019-03-11 06:00:00'),
(14, 'cccccc', '12345678', '2019-03-29', '女', 'ccc@gmail.com', '0944555666', '1', '2019-03-11 06:00:04'),
(15, 'aaaddd', '12345678', '2019-03-29', '男', 'aaa@gmail.com', '0955222111', '1', '2019-03-11 06:00:08'),
(16, 'seaqqq', '12345678', '2019-03-19', '女', 'aa@gmail.com', '0933111222', '1', '2019-03-11 06:00:10'),
(17, 'xxxxxx', '12345678', '2019-03-15', '男', 'xxx@gmail.com', '0911222333', '1', '2019-03-11 06:00:14');

--
-- 转储表的索引
--

--
-- 表的索引 `COSmember`
--
ALTER TABLE `COSmember`
  ADD PRIMARY KEY (`ID`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `COSmember`
--
ALTER TABLE `COSmember`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
