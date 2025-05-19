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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
                .filter(f -> f.getPais().getNombre().equalsIgnoreCase(pais))
                .collect(Collectors.toList());

        List<FestivoDTO> festivoDTOs = new ArrayList<>();
        LocalDate pascua = calcularPascua(anio);

        for (Festivo festivo : festivos) {
            FestivoDTO festivoDTO = new FestivoDTO();
            festivoDTO.setNombre(festivo.getNombre());

            switch (festivo.getTipo().getId()) {
                case 1: // Fijo
                    festivoDTO.setFecha(new Date(anio - 1900, festivo.getMes() - 1, festivo.getDia()));
                    break;

                case 2: // Ley de puente festivo: se traslada al siguiente lunes
                    Date fechaOriginal = new Date(anio - 1900, festivo.getMes() - 1, festivo.getDia());
                    Date fechaPuente = ServicioFechas.siguienteLunes(fechaOriginal);
                    festivoDTO.setFecha(fechaPuente);
                    break;

                case 3: // Basado en domingo de pascua + días
                    LocalDate fechaPascuaCalculada = pascua.plusDays(festivo.getDiasPascua());
                    festivoDTO.setFecha(java.sql.Date.valueOf(fechaPascuaCalculada));
                    break;

                case 4: // Basado en domingo de pascua + días, con ley de puente
                    LocalDate fechaPascuaPuente = pascua.plusDays(festivo.getDiasPascua());
                    Date fechaBase = java.sql.Date.valueOf(fechaPascuaPuente);
                    Date fechaPuenteFinal = ServicioFechas.siguienteLunes(fechaBase);
                    festivoDTO.setFecha(fechaPuenteFinal);
                    break;

                default:
                    festivoDTO.setFecha(null);
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