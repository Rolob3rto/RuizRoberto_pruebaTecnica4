-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-02-2024 a las 04:36:51
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agency`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `booking`
--

CREATE TABLE `booking` (
  `booking_type` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `booking_date` date DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `total_amount` double NOT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `flight_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `booking`
--

INSERT INTO `booking` (`booking_type`, `id`, `booking_date`, `active`, `total_amount`, `date_from`, `date_to`, `flight_id`, `room_id`) VALUES
('flightBooking', 1, '2024-02-22', b'1', 601, NULL, NULL, 1, NULL),
('roomBooking', 2, '2024-02-22', b'1', 150, '2024-03-20', '2024-03-21', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `destination` varchar(255) NOT NULL,
  `flight_date` date NOT NULL,
  `flight_number` varchar(255) NOT NULL,
  `num_seats` int(11) NOT NULL,
  `origin` varchar(255) NOT NULL,
  `price_person` double NOT NULL,
  `type_seat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `active`, `destination`, `flight_date`, `flight_number`, `num_seats`, `origin`, `price_person`, `type_seat`) VALUES
(1, b'1', 'Ciudad de Destino', '2024-02-20', 'ABCD-1234', 148, 'Ciudad de Origen', 300.5, 'Clase Económica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `place` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `active`, `hotel_code`, `place`) VALUES
(1, b'1', 'AB-1234', 'Ciudad de Destino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person`
--

CREATE TABLE `person` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `person`
--

INSERT INTO `person` (`id`, `active`, `dni`, `email`, `last_name`, `name`) VALUES
(1, b'1', '23541524L', 'ashjgd@jshd.com', 'apellido1', 'nombre1'),
(2, b'1', '23541524L', 'nombre2@gmail.com', 'apellido2', 'nombre2'),
(3, b'1', '23541524L', 'nombre@gmail.com', 'apellidoo1', 'nombre1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person_booking`
--

CREATE TABLE `person_booking` (
  `person_id` bigint(20) NOT NULL,
  `booking_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `person_booking`
--

INSERT INTO `person_booking` (`person_id`, `booking_id`) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `room_price` double NOT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `active`, `name`, `room_price`, `room_type`, `hotel_id`) VALUES
(1, b'1', 'Habitación Deluxe 1', 150, 'Deluxe', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK546eybei9q7dsna94vryofrbr` (`flight_id`),
  ADD KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`);

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_aucisqx7arn3fi6eyjmsvqdb3` (`flight_number`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sy3ax7w4f8ay5pb0p1csavjra` (`hotel_code`);

--
-- Indices de la tabla `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_fwmwi44u55bo4rvwsv0cln012` (`email`);

--
-- Indices de la tabla `person_booking`
--
ALTER TABLE `person_booking`
  ADD KEY `FKqt3cnf56oj0nx199rurr9fgw5` (`booking_id`),
  ADD KEY `FKj4lgu7e8w5f5hnmajld0ru2by` (`person_id`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4l8mm4fqoos6fcbx76rvqxer` (`name`),
  ADD KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `flight`
--
ALTER TABLE `flight`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `person`
--
ALTER TABLE `person`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK546eybei9q7dsna94vryofrbr` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`),
  ADD CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Filtros para la tabla `person_booking`
--
ALTER TABLE `person_booking`
  ADD CONSTRAINT `FKj4lgu7e8w5f5hnmajld0ru2by` FOREIGN KEY (`person_id`) REFERENCES `booking` (`id`),
  ADD CONSTRAINT `FKqt3cnf56oj0nx199rurr9fgw5` FOREIGN KEY (`booking_id`) REFERENCES `person` (`id`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
