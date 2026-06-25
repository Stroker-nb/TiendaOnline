CREATE TABLE envios (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          pedidoId BIGINT UNIQUE NOT NULL,
                          clienteId BIGINT NOT NULL,
                          direccion VARCHAR(100) NOT NULL

);