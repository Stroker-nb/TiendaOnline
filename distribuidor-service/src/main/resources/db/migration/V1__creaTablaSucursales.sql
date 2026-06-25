CREATE TABLE sucursales (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          idProducto BIGINT NOT NULL,
                          nombre VARCHAR(255) NOT NULL,
                          direccion VARCHAR(255) UNIQUE NOT NULL

);