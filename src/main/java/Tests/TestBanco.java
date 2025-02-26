package Tests;

import Model.Banco;
import Model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(moli);
        assertArrayEquals(usuarios.toArray(),banco.getUsuarios().toArray());
        banco.eliminarUsuario(moli);
        assertEquals(0,banco.getUsuarios().size());
    }



}
