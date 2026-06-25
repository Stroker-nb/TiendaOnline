package tiendaOnline.sucursal.dto;

import jakarta.validation.constraints.NotBlank;

public class SucursalRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;



    public SucursalRequest() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
