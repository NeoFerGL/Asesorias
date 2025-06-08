-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-06-2025 a las 18:37:14
-- Versión del servidor: 11.5.2-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `asesoria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `matricula` int(11) NOT NULL,
  `idCarrera` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`matricula`, `idCarrera`) VALUES
(1002, 1),
(1004, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asesoria`
--

CREATE TABLE `asesoria` (
  `id` int(11) NOT NULL,
  `matricula` int(11) DEFAULT NULL,
  `asunto` varchar(255) DEFAULT NULL,
  `fechasolicitud` date DEFAULT NULL,
  `horasolicitud` time DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `nrc` int(11) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT 'Asesoria sin respuesta'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asesoria`
--

INSERT INTO `asesoria` (`id`, `matricula`, `asunto`, `fechasolicitud`, `horasolicitud`, `estado`, `nrc`, `comentario`) VALUES
(1, 1002, 'Consulta sobre entrega de proyecto', '2024-05-01', '10:00:00', 0, 201, 'Pendiente de revisión'),
(2, 1004, 'Dudas sobre la materia de bases de datos', '2024-05-02', '15:30:00', 1, 203, 'Asesoría aceptada'),
(3, 1002, 'Solicitud de apoyo en modelo MVC', '2024-05-10', '09:09:00', 2, 202, 'Asesoría denegada'),
(4, 1004, 'Revision de ejercicios de programacion', '2024-05-07', '11:00:00', 2, 204, 'Asesoría denegada'),
(7, 1004, 'Aplicante', '2025-06-19', '09:00:00', 1, 203, 'Asesoría aceptada'),
(8, 1004, 'Aplicante para asesorÃ­a ', '2025-06-19', '07:00:00', 1, 204, 'Asesoría aceptada'),
(9, 1002, 'Desarrollo de pagina ', '2025-06-25', '10:30:00', 1, 201, 'Horario aceptado ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrera`
--

CREATE TABLE `carrera` (
  `id_carrera` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrera`
--

INSERT INTO `carrera` (`id_carrera`, `nombre`) VALUES
(1, 'Ingenieria en Tecnologias de la Informacion'),
(2, 'Licenciatura en Ciencias de la Computacion'),
(3, 'Ingenieria en Ciencias de la Computacion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `maestros_materias`
--

CREATE TABLE `maestros_materias` (
  `NRC` int(11) NOT NULL,
  `materia` varchar(255) DEFAULT NULL,
  `matricula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `maestros_materias`
--

INSERT INTO `maestros_materias` (`NRC`, `materia`, `matricula`) VALUES
(201, 'Aplicaciones Web', 1001),
(202, 'Modelos de Desarrollo Web', 1003),
(203, 'Bases de Datos', 1005),
(204, 'Programacion Orientada a Objetos', 1006);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia_alumnos`
--

CREATE TABLE `materia_alumnos` (
  `nrc` int(11) DEFAULT NULL,
  `matricula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `materia_alumnos`
--

INSERT INTO `materia_alumnos` (`nrc`, `matricula`) VALUES
(201, 1002),
(203, 1004),
(202, 1002),
(204, 1004);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `matricula` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellidoP` varchar(255) DEFAULT NULL,
  `apellidoM` varchar(255) DEFAULT NULL,
  `Maestro` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`matricula`, `nombre`, `apellidoP`, `apellidoM`, `Maestro`) VALUES
(1001, 'Luis', 'Perez', 'Garcia', 1),
(1002, 'Sofia', 'Lopez', 'Martinez', 0),
(1003, 'Miguel', 'Ramirez', 'Hernandez', 1),
(1004, 'Carla', 'Martinez', 'Gomez', 0),
(1005, 'Andres', 'Garcia', 'Lopez', 1),
(1006, 'Elena', 'Sanchez', 'Ruiz', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`matricula`),
  ADD KEY `idCarrera` (`idCarrera`);

--
-- Indices de la tabla `asesoria`
--
ALTER TABLE `asesoria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `matricula` (`matricula`),
  ADD KEY `fk_nrc` (`nrc`);

--
-- Indices de la tabla `carrera`
--
ALTER TABLE `carrera`
  ADD PRIMARY KEY (`id_carrera`);

--
-- Indices de la tabla `maestros_materias`
--
ALTER TABLE `maestros_materias`
  ADD PRIMARY KEY (`NRC`),
  ADD KEY `matricula` (`matricula`);

--
-- Indices de la tabla `materia_alumnos`
--
ALTER TABLE `materia_alumnos`
  ADD KEY `nrc` (`nrc`),
  ADD KEY `matricula` (`matricula`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`matricula`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asesoria`
--
ALTER TABLE `asesoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`matricula`) REFERENCES `usuario` (`matricula`),
  ADD CONSTRAINT `alumno_ibfk_2` FOREIGN KEY (`idCarrera`) REFERENCES `carrera` (`id_carrera`);

--
-- Filtros para la tabla `asesoria`
--
ALTER TABLE `asesoria`
  ADD CONSTRAINT `asesoria_ibfk_1` FOREIGN KEY (`matricula`) REFERENCES `usuario` (`matricula`),
  ADD CONSTRAINT `fk_nrc` FOREIGN KEY (`nrc`) REFERENCES `maestros_materias` (`NRC`);

--
-- Filtros para la tabla `maestros_materias`
--
ALTER TABLE `maestros_materias`
  ADD CONSTRAINT `maestros_materias_ibfk_1` FOREIGN KEY (`matricula`) REFERENCES `usuario` (`matricula`);

--
-- Filtros para la tabla `materia_alumnos`
--
ALTER TABLE `materia_alumnos`
  ADD CONSTRAINT `materia_alumnos_ibfk_1` FOREIGN KEY (`nrc`) REFERENCES `maestros_materias` (`NRC`),
  ADD CONSTRAINT `materia_alumnos_ibfk_2` FOREIGN KEY (`matricula`) REFERENCES `usuario` (`matricula`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
