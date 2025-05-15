package calendariosLaborales.api.presentacion.controladores;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import calendariosLaborales.api.core.servicios.IFestivoServicio;
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

    @RequestMapping(value = "/jejeje", method=RequestMethod.GET)
    public String requestMethodNam() {
        return "hola que ahce";
    }
    
    
}
