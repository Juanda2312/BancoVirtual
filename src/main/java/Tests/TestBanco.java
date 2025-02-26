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
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.FEBRUARY, 10, 10, 10, 30), "prueba", 200, loim);banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.FEBRUARY, 18, 10, 10, 30), "muestra", 600, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.FEBRUARY, 24, 10, 10, 30), "muestra", 400, moli);banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.MARCH, 4, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2024, Month.MARCH, 7, 10, 10, 30), "muestra", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2024, Month.MARCH, 24, 10, 10, 30), "prueba", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.APRIL, 7, 10, 10, 30), "muestra", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.APRIL, 11, 10, 10, 30), "prueba", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.APRIL, 23, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.MAY, 12, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.MAY, 20, 10, 10, 30), "muestra", 800, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.MAY, 24, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.JUNE, 18, 10, 10, 30), "prueba", 800, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.JUNE, 18, 10, 10, 30), "muestra", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.JUNE, 18, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.JULY, 23, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.JULY, 23, 10, 10, 30), "muestra", 800, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.JULY, 23, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.AUGUST, 24, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.AUGUST, 24, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.AUGUST, 24, 10, 10, 30), "prueba", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.SEPTEMBER, 6, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.SEPTEMBER, 6, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.SEPTEMBER, 6, 10, 10, 30), "muestra", 800, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.OCTOBER, 3, 10, 10, 30), "prueba", 600, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.OCTOBER, 3, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.OCTOBER, 3, 10, 10, 30), "prueba", 400, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.NOVEMBER, 7, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.NOVEMBER, 7, 10, 10, 30), "prueba", 200, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.NOVEMBER, 7, 10, 10, 30), "muestra", 400, loim);
        banco.RealizarTransaccion(loim, LocalDateTime.of(2014, Month.DECEMBER, 9, 10, 10, 30), "muestra", 600, moli);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.DECEMBER, 9, 10, 10, 30), "prueba", 200, loim);
        banco.RealizarTransaccion(moli, LocalDateTime.of(2014, Month.DECEMBER, 9, 10, 10, 30), "muestra", 400, loim);
        String factura = banco.obtenerPorcentajeGastosIngresos("123","1234",LocalDateTime.of(2024, Month.JANUARY, 1, 1, 0, 0),LocalDateTime.of(2025, Month.JANUARY, 1, 23, 59, 59));
        System.out.println(factura);

    }



}
