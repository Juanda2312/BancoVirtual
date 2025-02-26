package Tests;

import Model.Banco;
import Model.Billetera;
import Model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        banco.CrearBilletera(loim);
        Billetera billeteraObtenida = banco.validarUsarioContrasena("123","1234");
        Billetera billeteraLoim = banco.buscarBilletera(loim);
        assertEquals(billeteraLoim,billeteraObtenida);
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



}
