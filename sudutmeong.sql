-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 11, 2020 at 01:33 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sudutmeong`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employeeID` int(11) NOT NULL,
  `roleID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `DOB` date NOT NULL,
  `salary` int(11) NOT NULL,
  `status` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employeeID`, `roleID`, `name`, `username`, `DOB`, `salary`, `status`, `password`) VALUES
(1, 5, 'sora', 'sora', '1999-12-17', 2000000, 'Active', '8662'),
(2, 2, 'rem', 'rem', '2000-12-01', 12345, 'Active', '1234'),
(3, 2, 'Riku', 'riku', '1970-01-01', 10000000, 'Inactive', '2255'),
(4, 3, 'Kairi', 'kairi', '1998-05-31', 10000000, 'Inactive', '5958'),
(5, 4, 'Ven', 'Ven', '2000-08-12', 10000000, 'Inactive', '2681'),
(6, 6, 'test', 'test', '2000-04-20', 100000, 'Inactive', '1010'),
(7, 5, 'ram', 'ram', '2006-10-10', 20000, 'Active', '1532'),
(8, 5, 'subaru', 'barusu', '2000-12-11', 1000, 'Inactive', '7787'),
(9, 6, 'miku', 'miku', '1999-05-04', 123123, 'Active', '9939'),
(10, 4, 'test2', 'test2', '2020-06-01', 123123, 'Active', '7363'),
(11, 3, 'kami', 'kami', '2010-05-01', 12345, 'Active', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `name`, `description`, `price`, `stock`) VALUES
(1, 'X-blade', 'Standard Damage', 40000, 9407),
(2, 'Keyblade', 'Heavy Damage', 30000, 9960),
(3, 'Potion', 'Heal you', 2000, 2470),
(4, 'Hi-Potion', 'Heal you more', 5000, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `roleID` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleID`, `name`) VALUES
(2, 'Cashier'),
(3, 'StorageManagement'),
(4, 'HumanResource'),
(5, 'Manager'),
(6, 'PromoManagement');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transactionID` int(11) NOT NULL,
  `purchase_datetime` date NOT NULL,
  `voucherID` int(11) DEFAULT NULL,
  `employeeID` int(11) NOT NULL,
  `paymentType` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionID`, `purchase_datetime`, `voucherID`, `employeeID`, `paymentType`) VALUES
(1, '2020-05-24', NULL, 2, 'Credit'),
(2, '2020-05-24', NULL, 2, 'Credit'),
(3, '2020-05-28', 4, 2, 'Credit'),
(4, '2020-05-28', 2, 2, 'Cash'),
(5, '2020-05-28', 3, 2, 'Cash'),
(6, '2020-05-28', NULL, 2, 'Credit'),
(7, '2020-05-28', NULL, 2, 'Cash'),
(8, '2020-05-28', 1, 2, 'Credit'),
(9, '2020-05-28', 5, 2, 'Cash'),
(10, '2020-05-28', NULL, 2, 'Cash'),
(11, '2020-05-28', NULL, 2, 'Cash'),
(12, '2020-05-28', 7, 2, 'Cash\r\n'),
(13, '2020-05-29', 9, 2, 'Cash'),
(14, '2020-05-29', NULL, 2, 'Cash'),
(15, '2020-06-01', NULL, 2, 'Cash'),
(16, '2020-06-08', 10, 2, 'Credit'),
(17, '2020-06-08', 11, 2, 'Cash'),
(18, '2020-06-08', 12, 2, 'Cash'),
(19, '2020-06-08', NULL, 2, 'Credit'),
(20, '2020-06-08', NULL, 2, 'Credit');

-- --------------------------------------------------------

--
-- Table structure for table `transactionitem`
--

CREATE TABLE `transactionitem` (
  `transactionID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionitem`
--

INSERT INTO `transactionitem` (`transactionID`, `productID`, `quantity`) VALUES
(1, 1, 20),
(1, 2, 10),
(2, 1, 20),
(2, 2, 30),
(3, 1, 20),
(4, 1, 40),
(5, 2, 20),
(5, 3, 40),
(5, 4, 20),
(6, 1, 20),
(7, 1, 30),
(8, 1, 100),
(9, 1, 50),
(10, 1, 10),
(11, 1, 30),
(12, 1, 10),
(13, 1, 30),
(14, 1, 20),
(15, 1, 10),
(16, 1, 10),
(16, 2, 10),
(17, 2, 10),
(18, 1, 10),
(19, 1, 1),
(20, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `voucher`
--

CREATE TABLE `voucher` (
  `voucherID` int(11) NOT NULL,
  `discount` float NOT NULL,
  `validDate` date NOT NULL,
  `status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `voucher`
--

INSERT INTO `voucher` (`voucherID`, `discount`, `validDate`, `status`) VALUES
(1, 50, '2020-10-01', 'used'),
(2, 30, '2100-12-12', 'used'),
(3, 32, '2020-12-12', 'used'),
(4, 50.32, '2020-12-12', 'used'),
(5, 60.22, '2300-12-12', 'used'),
(6, 30, '2030-05-05', 'used'),
(7, 20, '2400-12-12', 'used'),
(8, 12, '2025-07-18', 'not used'),
(9, 12, '2200-12-12', 'used'),
(10, 1.8, '2032-06-10', 'used'),
(11, 13.24, '2044-06-09', 'used'),
(12, 20, '2020-06-10', 'used'),
(13, 10, '2020-06-26', 'not used'),
(14, 20, '2020-06-06', 'not used');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employeeID`),
  ADD UNIQUE KEY `EmployeeUsername` (`username`),
  ADD KEY `RoleID` (`roleID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transactionID`),
  ADD KEY `voucherID` (`voucherID`),
  ADD KEY `employeeID` (`employeeID`);

--
-- Indexes for table `transactionitem`
--
ALTER TABLE `transactionitem`
  ADD PRIMARY KEY (`transactionID`,`productID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `voucher`
--
ALTER TABLE `voucher`
  ADD PRIMARY KEY (`voucherID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employeeID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `roleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `voucher`
--
ALTER TABLE `voucher`
  MODIFY `voucherID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`voucherID`) REFERENCES `voucher` (`voucherID`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`EmployeeID`);

--
-- Constraints for table `transactionitem`
--
ALTER TABLE `transactionitem`
  ADD CONSTRAINT `transactionitem_ibfk_1` FOREIGN KEY (`transactionID`) REFERENCES `transaction` (`transactionID`),
  ADD CONSTRAINT `transactionitem_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
