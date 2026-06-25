CREATE TABLE clientes (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(255) NOT NULL,
                          correo VARCHAR(255) UNIQUE NOT NULL,
                          telefono VARCHAR(9) NOT NULL

);