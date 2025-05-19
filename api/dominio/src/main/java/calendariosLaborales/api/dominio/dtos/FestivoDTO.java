package calendariosLaborales.api.dominio.dtos;

import java.util.Date;

public class FestivoDTO {

    private String nombre;
    private Date fecha;

    public FestivoDTO() {
    }

    public FestivoDTO(String nombre, Date fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
