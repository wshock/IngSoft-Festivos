package calendariosLaborales.api.aplicacion.servicios;

import calendariosLaborales.api.core.servicios.IFestivoServicio;
import calendariosLaborales.api.dominio.dtos.FestivoDTO;
import calendariosLaborales.api.dominio.entidades.Festivo;
import calendariosLaborales.api.infraestructura.repositorios.IFestivoRepositorio;
import calendariosLaborales.api.infraestructura.repositorios.IPaisRepositorio;
import calendariosLaborales.api.infraestructura.repositorios.ITipoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class FestivoServicioImpl implements IFestivoServicio {

    @Autowired
    private IFestivoRepositorio festivoRepositorio;
    @Autowired
    private IPaisRepositorio paisRepositorio;
    @Autowired
    private ITipoRepositorio tipoRepositorio;

    @Override
    public Boolean consultarSiEsFestivo(int idpais, LocalDate fecha) {
        // List<Festivo> fijos =
        // festivoRepositorio.findFestivosFijosByDiaAndMesAndPaisId(fecha.getDayOfMonth(),
        // fecha.getMonthValue(), idpais);
        // if (!fijos.isEmpty()) return true;
        // LocalDate pascua = calcularPascua(fecha.getYear());
        // List<Festivo> variables =
        // festivoRepositorio.findFestivosVariablesByPaisId(idpais);
        // return variables.stream().anyMatch(f ->
        // pascua.plusDays(f.getDiasPascua()).equals(fecha));
        System.out.println("Hola desde el servicio");
        if (idpais == 1) {
            return true;
        } else {
            return false;
        }
    }

    private LocalDate calcularPascua(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
    }

    @Override
    public List<FestivoDTO> obtenerFestivosDelAnio(String pais, int anio) {
        var festivos = festivoRepositorio.findAll().stream()
                .filter(f -> f.getPais().getNombre().equals(pais))
                .collect(Collectors.toList());
        List<FestivoDTO> festivoDTOs = new ArrayList<>();
        for (Festivo festivo : festivos) {
            FestivoDTO festivoDTO = new FestivoDTO();
            festivoDTO.setNombre(festivo.getNombre());
            switch (festivoDTOs.getTipo().getId()) {
                case 2:
                    Date fecha = ServicioFechas
                            .siguienteLunes(new Date(anio - 1900, festivo.getMes() - 1, festivo.getDia()));
                    festivoDTO.setFecha(fecha);
                    break;

            }
            festivoDTOs.add(festivoDTO);

        }
        return festivoDTOs;
    }

    @Override
    public List<Festivo> listar() {
        return festivoRepositorio.findAll();
    }

    @Override
    public Festivo obtener(int id) {
        return festivoRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Festivo> buscar(String nombre) {
        return festivoRepositorio.buscar(nombre);
    }

    @Override
    public Festivo agregar(Festivo festivo) {
        return festivoRepositorio.save(festivo);
    }

    @Override
    public Festivo modificar(Festivo festivo) {
        return festivoRepositorio.save(festivo);
    }

    @Override
    public boolean eliminar(int id) {
        festivoRepositorio.deleteById(id);
        return true;
    }
}
