package calendariosLaborales.api.dominio.dtos;

public class FestivoDTO {
    private int id;
    private String pais;
    private String nombre;
    private Integer dia;
    private Integer mes;
    private Integer diasPascua;
    private String tipo;

    public FestivoDTO() {}

    public FestivoDTO(int id, String pais, String nombre, Integer dia, Integer mes, Integer diasPascua, String tipo) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.diasPascua = diasPascua;
        this.tipo = tipo;
    }

    // Getters and setters omitted for brevity
}
