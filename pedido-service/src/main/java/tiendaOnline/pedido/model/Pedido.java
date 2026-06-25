
package tiendaOnline.pedido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productos_id", nullable = false)
    private long productoId;

    @Column(name = "clientes_id", nullable = false)
    private long clienteId;

    @Column(nullable = false)
    private Integer monto;

}
