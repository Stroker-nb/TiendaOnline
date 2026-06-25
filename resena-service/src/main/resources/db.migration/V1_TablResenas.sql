CREATE TABLE resenas (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,

                         productos_id BIGINT NOT NULL,

                         clientes_id BIGINT NOT NULL,

                         calificacion INT NOT NULL,

                         comentario VARCHAR(255) NOT NULL
);