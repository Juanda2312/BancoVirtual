package Model;

public class Billetera {
    private String numeroID;
    private float  saldo;
    private Usuario usuario;


    public Billetera(String numeroID,  Usuario usuario) {
        this.numeroID = numeroID;
        this.saldo = 0;
        this.usuario = usuario;
    }

    public String getNumeroID() {
        return numeroID;
    }

    public void setNumeroID(String numeroID) {
        this.numeroID = numeroID;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** Este método recibe un monto, si el monto es valido, lo suma al saldo de la billetera.
     */
    public void AumentarSaldo(float monto)throws Exception {
        if (monto > 0){
            saldo += monto;
        }else {
            throw new Exception("Monto invalido");
        }
    }

    /** Este método recibe un monto, si es valido, calcula si la billetera tiene saldo suficiente para pagar el monto y,
     * de ser así, resta el monto al saldo.
     */
    public void DisminuirSaldo(float monto)throws Exception {
        if (monto > 0){
            float resultado = saldo - monto;
            if (resultado < 0) {
                throw new Exception("No hay dinero suficiente");
            }else{
                saldo -= monto;
            }
        }else{
            throw new Exception("Monto invalido");
        }
    }

    @Override
    public String toString() {
        return "Billetera{" +
                "numeroID='" + numeroID + '\'' +
                ", saldo=" + saldo +
                ", usuario=" + usuario +
                '}';
    }
}
