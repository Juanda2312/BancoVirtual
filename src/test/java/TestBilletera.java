import Model.Billetera;
import Model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBilletera {
    Billetera billetera = new Billetera("1", new Usuario("a","tangamandapio","123","Loim@gmail.com","1234"));

    @Test
    public void AumentarSaldoTest() throws Exception{
        assertEquals(0,billetera.getSaldo());
        assertThrows(Exception.class,()-> billetera.AumentarSaldo(0));
        assertThrows(Exception.class,()-> billetera.AumentarSaldo(-500));
        billetera.AumentarSaldo(200);
        assertEquals(200,billetera.getSaldo());
        billetera.AumentarSaldo(70000);
        assertEquals(70200,billetera.getSaldo());
    }

    @Test
    public void DisminuirSaldoTest() throws Exception{
        assertEquals(0,billetera.getSaldo());
        assertThrows(Exception.class,()-> billetera.DisminuirSaldo(0));
        assertThrows(Exception.class,()-> billetera.DisminuirSaldo(-500));
        assertThrows(Exception.class,()-> billetera.DisminuirSaldo(200));
        billetera.AumentarSaldo(50000);
        billetera.DisminuirSaldo(20000);
        assertEquals(30000,billetera.getSaldo());
        assertThrows(Exception.class,()-> billetera.DisminuirSaldo(70000));
    }
}
