CREATE TABLE Trabajadores (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          sucursalId BIGINT NOT NULL,
                          nombre VARCHAR(255) NOT NULL,
                          correo VARCHAR(255) UNIQUE NOT NULL,
                          telefono VARCHAR(9) NOT NULL

);