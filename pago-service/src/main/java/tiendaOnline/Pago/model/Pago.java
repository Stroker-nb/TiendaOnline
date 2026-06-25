
package tiendaOnline.Pago.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Builder
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Cliente_id" , nullable = false)
    private Long idCliente;

    @Column(name = "Pedidos_id", nullable = false)
    private Long idPedido;

    @Column(nullable = false)
    private Double monto;

    @Column(name = "metodos_pago", nullable = false)
    private String metodoPago;


    @Column(nullable = false)
    private LocalDate fechaPago;

    public Pago() {}

    public Pago(Long id, Long idCliente , Long idPedido, Double monto, String metodoPago, LocalDate fechaPago) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPedido = idPedido;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}
