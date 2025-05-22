package calendariosLaborales.api.presentacion.controladores;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import calendariosLaborales.api.core.servicios.IFestivoServicio;
import calendariosLaborales.api.dominio.dtos.FestivoDTO;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/festivos")
public class FestivoControlador {
    private IFestivoServicio servicio;

    public FestivoControlador(IFestivoServicio servicio) {
        this.servicio = servicio;
    }

    @RequestMapping(value = "/verificar/{id}", method=RequestMethod.POST)
    public String consultEsFestivo(@RequestBody LocalDate date, @PathVariable int id) {
        System.out.println("hola desde el controller");
        return servicio.consultarSiEsFestivo(id, date) ? "Es festivo" : "No es festivo";
    }

    @RequestMapping(value = "/festivos/{idpais}/{id}", method=RequestMethod.GET)
    public List<FestivoDTO> requestMethodName(@PathVariable String idpais, @PathVariable int id) {
        return servicio.obtenerFestivosDelAnio(Integer.parseInt(idpais), id);
    }
    
    
}
