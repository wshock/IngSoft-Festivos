package servicios;

import java.util.Calendar;
import java.util.Date;

public class ServicioFechas {

    public static Date getInicioSemanaSanta(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;

        int dias = d + e;
        int dia = 15 + dias;
        int mes = 3; // Marzo

        if (dia > 31) {
            dia = dia - 31;
            mes = 4; // Abril
        }

        return new Date(año - 1900, mes - 1, dia);
    }

    public static Date agregarDias(Date fecha, int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DATE, dias);
        return calendario.getTime();
    }

    public static Date siguienteLunes(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        if (diaSemana != Calendar.MONDAY) {
            if (diaSemana > Calendar.MONDAY) {
                fecha = agregarDias(fecha, 9 - diaSemana);
            } else {
                fecha = agregarDias(fecha, 1);
            }
        }
        return fecha;
    }
}
