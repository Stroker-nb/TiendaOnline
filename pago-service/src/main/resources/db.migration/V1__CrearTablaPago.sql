CREATE TABLE pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    Cliente_id BIGINT NOT NULL,

    Pedidos_id BIGINT NOT NULL,

    monto DOUBLE NOT NULL,

    metodos_pago VARCHAR(255) NOT NULL,

    fecha_pago DATE NOT NULL
);