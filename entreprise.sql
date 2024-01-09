-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 09 jan. 2024 à 10:55
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `entreprise`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `employe_name` varchar(255) NOT NULL,
  `employe_prenom` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `attendance`
--

INSERT INTO `attendance` (`id`, `employe_name`, `employe_prenom`, `date`) VALUES
(0, 'LABRAHMI', 'Hicham', '2024-01-06'),
(0, 'MBARKI', 'Ayyoub', '2024-01-06'),
(0, 'BOURUIZE', 'Mohammed', '2024-01-06'),
(0, 'BAHOUSSI', 'Hanane', '2024-01-06'),
(0, 'BABA', 'Rime', '2024-01-06'),
(0, 'BOUFERRA', 'Chihab', '2024-01-06'),
(0, 'LAZAAR', 'Oumaima', '2024-01-06'),
(0, 'MOUSTADRAF', 'Mohammed Hamza', '2024-01-06'),
(0, 'ZENIBLA', 'Mohamed', '2024-01-06'),
(0, 'LAHMAMY', 'Taha', '2024-01-06'),
(155, 'MBARKI', 'Ayyoub', '2024-01-07'),
(156, 'BOURUIZE', 'Mohammed', '2024-01-07'),
(158, 'BABA', 'Rime', '2024-01-07'),
(159, 'BOUFERRA', 'Chihab', '2024-01-07'),
(160, 'LAZAAR', 'Oumaima', '2024-01-07'),
(161, 'MOUSTADRAF', 'Mohammed Hamza', '2024-01-07'),
(155, 'MBARKI', 'Ayyoub', '2024-01-08'),
(159, 'BOUFERRA', 'Chihab', '2024-01-08'),
(163, 'LAHMAMY', 'Taha', '2024-01-08'),
(160, 'LAZAAR', 'Oumaima', '2024-01-08'),
(158, 'BABA', 'Rime', '2024-01-08');

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE `employe` (
  `id` int(11) NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  `job` varchar(60) NOT NULL,
  `salary` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`id`, `fname`, `lname`, `age`, `job`, `salary`) VALUES
(154, 'LABRAHMI', 'Hicham', 20, 'Developer', 54373),
(155, 'MBARKI', 'Ayyoub', 20, 'Developer', 26524),
(156, 'BOURUIZE', 'Mohammed', 22, 'Designer', 2275),
(158, 'BABA', 'Rime', 19, 'Developer', 200),
(159, 'BOUFERRA', 'Chihab', 20, 'Designer', 600),
(160, 'LAZAAR', 'Oumaima', 22, 'Developer', 600),
(161, 'MOUSTADRAF', 'Mohammed Hamza', 20, 'Designer', 500),
(162, 'ZENIBLA', 'Mohamed', 21, 'Manager', 500),
(163, 'LAHMAMY', 'Taha', 20, 'Developer', 700);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `employe`
--
ALTER TABLE `employe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=164;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
