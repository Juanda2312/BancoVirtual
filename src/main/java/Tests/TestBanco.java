package Tests;

import Model.Banco;
import Model.Billetera;
import Model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBanco {

    Banco banco = new Banco("Banco Probable");

    @Test
    public void testDeUsuario() throws Exception {
        Usuario loim = new Usuario("Loim","Tangamandapio","123","Loim@gmail.com","1234");
        banco.registrarUsuarios(loim);
        assertEquals(1,banco.getUsuarios().size());
        Usuario moli = new Usuario("Moli", "Porra", "321", "Moli@gmail.com","4321");
        banco.ActualizarUsuario(loim, moli);
        assertThrows(Exception.class, () -> banco.registrarUsuarios(moli));
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(moli);
        assertEquals(moli.getId(),banco.getUsuarios().getFirst().getId());
        banco.eliminarUsuario(moli);
        assertEquals(0,banco.getUsuarios().size());
    }

    @Test
    public void testDeBilletera() throws Exception {
        Usuario loim = new Usuario("Loim","Tangamandapio","123","Loim@gmail.com","1234");
        banco.registrarUsuarios(loim);
        assertDoesNotThrow(() -> banco.CrearBilletera(loim));
        Usuario moli = null;
        assertThrows(Exception.class, () -> banco.CrearBilletera(moli));
        Billetera billeteraLoim = banco.buscarBilletera(loim);
        banco.RecargarBilletera(loim,200);
        assertEquals(200,billeteraLoim.getSaldo());
        billeteraLoim.AumentarSaldo(200);
        assertEquals(400,billeteraLoim.getSaldo());
        billeteraLoim.DisminuirSaldo(250);
        assertEquals(150,billeteraLoim.getSaldo());
    }

    @Test
    public void testDeUsuarioBilletera() throws Exception {
        Usuario loim = new Usuario("Loim","Tangamandapio","123","Loim@gmail.com","1234");
        banco.registrarUsuarios(loim);
        banco.CrearBilletera(loim);
        Billetera billeteraObtenida = banco.validarUsarioContrasena("123","1234");
        Billetera billeteraLoim = banco.buscarBilletera(loim);
        assertEquals(billeteraLoim,billeteraObtenida);
        assertThrows(Exception.class, () -> banco.validarUsarioContrasena("123","1111"));
        assertThrows(Exception.class, () -> banco.validarUsarioContrasena("","1111"));
    }

    @Test
    public void testDeTransaccion() throws Exception {
        Usuario loim = new Usuario("Loim","Tangamandapio","123","Loim@gmail.com","1234");
        Usuario moli = new Usuario("Moli", "Porra", "321", "Moli@gmail.com","4321");
        banco.CrearBilletera(loim);
        banco.CrearBilletera(moli);
        Billetera billeteraLoim = banco.buscarBilletera(loim);
        Billetera billeteraMoli = banco.buscarBilletera(moli);
        banco.RecargarBilletera(loim, 500);
        banco.RealizarTransaccion(loim, LocalDateTime.now(), "prueba", 200, moli);
        assertEquals(100,billeteraLoim.getSaldo());
        assertEquals(200,billeteraMoli.getSaldo());
        assertThrows(Exception.class, () -> banco.RealizarTransaccion(loim, LocalDateTime.now(), "prueba", 200, moli));
    }

     @Test
        public void testDeObtenerValores() throws Exception {
        Usuario loim = new Usuario("Loim","Tangamandapio","123","Loim@gmail.com","1234");
        Usuario moli = new Usuario("Moli", "Porra", "321", "Moli@gmail.com","4321");
        banco.registrarUsuarios(loim);
        banco.registrarUsuarios(moli);
        banco.CrearBilletera(loim);
        banco.CrearBilletera(moli);
        Billetera billetera = banco.buscarBilletera(loim);
        banco.RecargarBilletera(loim, 1200000);
        banco.RecargarBilletera(moli, 1200000);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.JANUARY, 1, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.JANUARY, 3, 10, 10, 30), "muestra", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.JANUARY, 8, 10, 10, 30), "prueba", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.FEBRUARY, 10, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.FEBRUARY, 18, 10, 10, 30), "muestra", 600, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.FEBRUARY, 24, 10, 10, 30), "muestra", 400, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.MARCH, 4, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.MARCH, 7, 10, 10, 30), "muestra", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.MARCH, 24, 10, 10, 30), "prueba", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.APRIL, 7, 10, 10, 30), "muestra", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.APRIL, 11, 10, 10, 30), "prueba", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.APRIL, 23, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.MAY, 12, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.MAY, 20, 10, 10, 30), "muestra", 800, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.MAY, 24, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.JUNE, 2, 10, 10, 30), "prueba", 800, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.JUNE, 18, 10, 10, 30), "muestra", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.JUNE, 28, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.JULY, 3, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.JULY, 13, 10, 10, 30), "muestra", 800, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.JULY, 23, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.AUGUST, 4, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.AUGUST, 24, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.AUGUST, 25, 10, 10, 30), "prueba", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.SEPTEMBER, 6, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.SEPTEMBER, 16, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.SEPTEMBER, 22, 10, 10, 30), "muestra", 800, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.OCTOBER, 3, 10, 10, 30), "prueba", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.OCTOBER, 12, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.OCTOBER, 30, 10, 10, 30), "prueba", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.NOVEMBER, 7, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.NOVEMBER, 14, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.NOVEMBER, 27, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.DECEMBER, 9, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.DECEMBER, 11, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.DECEMBER, 22, 10, 10, 30), "muestra", 400, loim);
        String factura = banco.obtenerPorcentajeGastosIngresos("123","1234",LocalDateTime.of(2024, Month.JANUARY, 1, 1, 0, 0),LocalDateTime.of(2025, Month.JANUARY, 1, 23, 59, 59));
        String mensaje = "Fecha inicio: 2024-01-01T01:00 - Fecha fin: 2024-02-01T01:00\n" +
                "% Gastos del mes: 9.803922%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 2.5%\n" +
                "0.0% Ingresos de la categoria: prueba\n" +
                "100.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-02-01T01:00 - Fecha fin: 2024-03-01T01:00\n" +
                "% Gastos del mes: 5.882353%\n" +
                "0.0% Gastos de la categoria: prueba\n" +
                "100.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 10.0%\n" +
                "25.0% Ingresos de la categoria: prueba\n" +
                "75.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-03-01T01:00 - Fecha fin: 2024-04-01T01:00\n" +
                "% Gastos del mes: 9.803922%\n" +
                "40.0% Gastos de la categoria: prueba\n" +
                "60.000004% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 7.5000005%\n" +
                "100.0% Ingresos de la categoria: prueba\n" +
                "0.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-04-01T01:00 - Fecha fin: 2024-05-01T01:00\n" +
                "% Gastos del mes: 7.8431377%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 5.0%\n" +
                "50.0% Ingresos de la categoria: prueba\n" +
                "50.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-05-01T01:00 - Fecha fin: 2024-06-01T01:00\n" +
                "% Gastos del mes: 7.8431377%\n" +
                "0.0% Gastos de la categoria: prueba\n" +
                "100.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 15.000001%\n" +
                "0.0% Ingresos de la categoria: prueba\n" +
                "100.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-06-01T01:00 - Fecha fin: 2024-07-01T01:00\n" +
                "% Gastos del mes: 9.803922%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 10.0%\n" +
                "25.0% Ingresos de la categoria: prueba\n" +
                "75.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-07-01T01:00 - Fecha fin: 2024-08-01T01:00\n" +
                "% Gastos del mes: 13.725491%\n" +
                "28.57143% Gastos de la categoria: prueba\n" +
                "71.42857% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 2.5%\n" +
                "100.0% Ingresos de la categoria: prueba\n" +
                "0.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-08-01T01:00 - Fecha fin: 2024-09-01T01:00\n" +
                "% Gastos del mes: 13.725491%\n" +
                "42.857143% Gastos de la categoria: prueba\n" +
                "57.14286% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 5.0%\n" +
                "0.0% Ingresos de la categoria: prueba\n" +
                "100.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-09-01T01:00 - Fecha fin: 2024-10-01T01:00\n" +
                "% Gastos del mes: 3.9215689%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 15.000001%\n" +
                "0.0% Ingresos de la categoria: prueba\n" +
                "100.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-10-01T01:00 - Fecha fin: 2024-11-01T01:00\n" +
                "% Gastos del mes: 5.882353%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 10.0%\n" +
                "100.0% Ingresos de la categoria: prueba\n" +
                "0.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-11-01T01:00 - Fecha fin: 2024-12-01T01:00\n" +
                "% Gastos del mes: 3.9215689%\n" +
                "100.0% Gastos de la categoria: prueba\n" +
                "0.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 10.0%\n" +
                "0.0% Ingresos de la categoria: prueba\n" +
                "100.0% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2024-12-01T01:00 - Fecha fin: 2025-01-01T01:00\n" +
                "% Gastos del mes: 7.8431377%\n" +
                "0.0% Gastos de la categoria: prueba\n" +
                "100.0% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 7.5000005%\n" +
                "33.333336% Ingresos de la categoria: prueba\n" +
                "66.66667% Ingresos de la categoria: muestra\n" +
                "Fecha inicio: 2025-01-01T01:00 - Fecha fin: 2025-02-01T01:00\n" +
                "% Gastos del mes: 0.0%\n" +
                "NaN% Gastos de la categoria: prueba\n" +
                "NaN% Gastos de la categoria: muestra\n" +
                "% Ingresos del mes: 0.0%\n" +
                "NaN% Ingresos de la categoria: prueba\n" +
                "NaN% Ingresos de la categoria: muestra\n";
        assertEquals(mensaje, factura);

    }



}
