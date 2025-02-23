package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaccion {
    private LocalDateTime fecha;
    private String categoria;
    final private float COSTO;
    private float monto;
    final private UUID id;
    final private Billetera emisor;
    final private Billetera receptor;

    public Transaccion(LocalDateTime fecha, String categoria, float monto, Billetera emisor, Billetera receptor) {
        this.fecha = fecha;
        this.categoria = categoria;
        this.monto = monto;
        this.emisor = emisor;
        this.receptor = receptor;
        this.id = UUID.randomUUID();
        this.COSTO = 200;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public float getCosto() {
        return COSTO;
    }


    public UUID getId() {
        return id;
    }

    public Billetera getEmisor() {
        return emisor;
    }

    public Billetera getReceptor() {
        return receptor;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "fecha=" + fecha +
                ", categoria='" + categoria + '\'' +
                ", COSTO=" + COSTO +
                ", monto=" + monto +
                ", id=" + id +
                ", emisor=" + emisor +
                ", receptor=" + receptor +
                '}';
    }
}
