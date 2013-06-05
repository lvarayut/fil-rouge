-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 05, 2013 at 10:06 PM
-- Server version: 5.5.29
-- PHP Version: 5.4.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `betting`
--

-- --------------------------------------------------------

--
-- Table structure for table `bet_podium`
--

CREATE TABLE `bet_podium` (
  `pseudo_subscriber` varchar(6) NOT NULL,
  `id_competitor1` int(11) NOT NULL,
  `id_competitor2` int(11) NOT NULL,
  `id_competitor3` int(11) NOT NULL,
  `name_competition` varchar(11) NOT NULL,
  `betToken` int(11) NOT NULL,
  PRIMARY KEY (`pseudo_subscriber`,`id_competitor1`,`id_competitor2`,`id_competitor3`),
  KEY `pseudo_competitor1` (`id_competitor1`),
  KEY `pseudo_competitor2` (`id_competitor2`),
  KEY `pseudo_competitor3` (`id_competitor3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `bet_winner`
--

CREATE TABLE `bet_winner` (
  `pseudo_subscriber` varchar(6) NOT NULL,
  `id_competitor` int(11) NOT NULL,
  `name_competition` varchar(11) NOT NULL,
  `betToken` int(11) NOT NULL,
  PRIMARY KEY (`pseudo_subscriber`,`id_competitor`),
  KEY `pseudo_competitor` (`id_competitor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `competition`
--

CREATE TABLE `competition` (
  `name` varchar(10) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `competitor`
--

CREATE TABLE `competitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(10) NOT NULL,
  `lastname` varchar(10) NOT NULL,
  `birthdate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=531 ;

-- --------------------------------------------------------

--
-- Table structure for table `comp_subs_rob`
--

CREATE TABLE `comp_subs_rob` (
  `pseudo_subscriber` varchar(6) NOT NULL,
  `name_robot` varchar(10) NOT NULL,
  `name_competition` varchar(10) NOT NULL,
  `betToken` int(11) NOT NULL,
  PRIMARY KEY (`pseudo_subscriber`,`name_robot`,`name_competition`),
  KEY `name_robot` (`name_robot`),
  KEY `name_competition` (`name_competition`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

CREATE TABLE `manager` (
  `lastname` varchar(10) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL,
  `birthdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `participate`
--

CREATE TABLE `participate` (
  `id_competitor` int(11) NOT NULL,
  `name_competition` varchar(11) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_competitor`,`name_competition`),
  KEY `name_competition` (`name_competition`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `podium`
--

CREATE TABLE `podium` (
  `name_competition` varchar(10) NOT NULL,
  `id_competitor1` int(11) NOT NULL,
  `id_competitor2` int(11) NOT NULL,
  `id_competitor3` int(11) NOT NULL,
  PRIMARY KEY (`name_competition`,`id_competitor1`,`id_competitor2`,`id_competitor3`),
  KEY `pseudo_competitor1` (`id_competitor1`),
  KEY `pseudo_competitor2` (`id_competitor2`),
  KEY `pseudo_competitor3` (`id_competitor3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `robot`
--

CREATE TABLE `robot` (
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `subscriber`
--

CREATE TABLE `subscriber` (
  `pseudo` varchar(6) NOT NULL,
  `lastname` varchar(10) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL,
  `birthdate` date NOT NULL,
  `number_token` int(11) NOT NULL,
  PRIMARY KEY (`pseudo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `winner`
--

CREATE TABLE `winner` (
  `name_competition` varchar(10) NOT NULL,
  `id_competitor` int(11) NOT NULL,
  PRIMARY KEY (`name_competition`,`id_competitor`),
  KEY `pseudo_competitor` (`id_competitor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bet_podium`
--
ALTER TABLE `bet_podium`
  ADD CONSTRAINT `bet_podium_ibfk_1` FOREIGN KEY (`pseudo_subscriber`) REFERENCES `subscriber` (`pseudo`),
  ADD CONSTRAINT `bet_podium_ibfk_2` FOREIGN KEY (`id_competitor1`) REFERENCES `competitor` (`id`),
  ADD CONSTRAINT `bet_podium_ibfk_3` FOREIGN KEY (`id_competitor2`) REFERENCES `competitor` (`id`),
  ADD CONSTRAINT `bet_podium_ibfk_4` FOREIGN KEY (`id_competitor3`) REFERENCES `competitor` (`id`);

--
-- Constraints for table `bet_winner`
--
ALTER TABLE `bet_winner`
  ADD CONSTRAINT `bet_winner_ibfk_1` FOREIGN KEY (`pseudo_subscriber`) REFERENCES `subscriber` (`pseudo`),
  ADD CONSTRAINT `bet_winner_ibfk_2` FOREIGN KEY (`id_competitor`) REFERENCES `competitor` (`id`);

--
-- Constraints for table `comp_subs_rob`
--
ALTER TABLE `comp_subs_rob`
  ADD CONSTRAINT `comp_subs_rob_ibfk_1` FOREIGN KEY (`pseudo_subscriber`) REFERENCES `subscriber` (`pseudo`),
  ADD CONSTRAINT `comp_subs_rob_ibfk_2` FOREIGN KEY (`name_robot`) REFERENCES `robot` (`name`),
  ADD CONSTRAINT `comp_subs_rob_ibfk_3` FOREIGN KEY (`name_competition`) REFERENCES `competition` (`name`);

--
-- Constraints for table `participate`
--
ALTER TABLE `participate`
  ADD CONSTRAINT `participate_ibfk_1` FOREIGN KEY (`id_competitor`) REFERENCES `competitor` (`id`),
  ADD CONSTRAINT `participate_ibfk_2` FOREIGN KEY (`name_competition`) REFERENCES `competition` (`name`);

--
-- Constraints for table `podium`
--
ALTER TABLE `podium`
  ADD CONSTRAINT `podium_ibfk_1` FOREIGN KEY (`name_competition`) REFERENCES `competition` (`name`),
  ADD CONSTRAINT `podium_ibfk_2` FOREIGN KEY (`id_competitor1`) REFERENCES `competitor` (`id`),
  ADD CONSTRAINT `podium_ibfk_3` FOREIGN KEY (`id_competitor2`) REFERENCES `competitor` (`id`),
  ADD CONSTRAINT `podium_ibfk_4` FOREIGN KEY (`id_competitor3`) REFERENCES `competitor` (`id`);

--
-- Constraints for table `winner`
--
ALTER TABLE `winner`
  ADD CONSTRAINT `winner_ibfk_1` FOREIGN KEY (`name_competition`) REFERENCES `competition` (`name`),
  ADD CONSTRAINT `winner_ibfk_2` FOREIGN KEY (`id_competitor`) REFERENCES `competitor` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
