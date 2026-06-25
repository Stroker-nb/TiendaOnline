CREATE TABLE pedidos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,

                        productos_id BIGINT NOT NULL,

                        clientes_id BIGINT NOT NULL,

                        monto INT NOT NULL
);