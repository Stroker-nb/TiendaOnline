
package tiendaOnline.resena.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resenas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productos_id", nullable = false)
    private long productoId;

    @Column(name = "clientes_id", nullable = false)
    private long clienteId;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(nullable = false)
    private String comentario;

}
