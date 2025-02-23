package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Banco {
    private String nombre;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Billetera> billeteras;
    private ArrayList<Transaccion> transacciones;

    public Banco(String nombre) {
        this.nombre = nombre;
        usuarios = new ArrayList<>();
        billeteras = new ArrayList<>();
        transacciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void registrarUsuarios(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario no valido");
        } else {
            Usuario usuarioaux = buscarUsuario(usuario.getId());
            if (usuarioaux == null) {
                usuarios.add(usuario);
            } else {
                throw new Exception("Usuario ya existe");
            }
        }
    }

    private Usuario buscarUsuario(String id)throws Exception {
    if (id == null || id.isEmpty()) {
        throw new Exception("Id no valido");
    }else {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    }

    public void eliminarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario no valido");
        } else {
            Usuario usuarioaux = buscarUsuario(usuario.getId());
            if (usuarioaux != null) {
                usuarios.remove(usuarioaux);
            } else {
                throw new Exception("Usuario no encontrado");
            }
        }
    }

    public void ActualizarUsuario(Usuario usuario, Usuario usuarionuevo) throws Exception {
        if (usuario == null || usuarionuevo == null) {
            throw new Exception("Usuario no valido");
        } else {
            Usuario usuarioaux = buscarUsuario(usuario.getId());
            if (usuarioaux != null) {
                if (!usuarionuevo.getId().equals(usuarioaux.getId())) {
                    Usuario usuarioaux2 = buscarUsuario(usuarionuevo.getId());
                    if (usuarioaux2 == null) {
                        usuarioaux.setNombre(usuarionuevo.getNombre());
                        usuarioaux.setContrasena(usuarionuevo.getContrasena());
                        usuarioaux.setCorreo(usuarionuevo.getCorreo());
                        usuarioaux.setDireccion(usuarionuevo.getDireccion());
                        usuarioaux.setId(usuarionuevo.getId());
                    } else {
                        throw new Exception("Ese id ya existe");
                    }
                } else {
                    usuarioaux.setNombre(usuarionuevo.getNombre());
                    usuarioaux.setContrasena(usuarionuevo.getContrasena());
                    usuarioaux.setCorreo(usuarionuevo.getCorreo());
                    usuarioaux.setDireccion(usuarionuevo.getDireccion());
                    usuarioaux.setId(usuarionuevo.getId());
                }
            } else {
                throw new Exception("Usuario no encontrado");
            }
        }
    }

    public void CrearBilletera(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario no valido");
        } else {
            String id = "";
            Billetera existe = new Billetera("",null);
            while (existe != null) {
                id = generarId();
                existe = buscarBilletera(id);
            }
            Billetera billetera = new Billetera(id, usuario);
            billeteras.add(billetera);
        }
    }

    public Billetera buscarBilletera(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("Id no valido");
        }else {
            for (Billetera billetera : billeteras) {
                if (billetera.getNumeroID().equals(id)) {
                    return billetera;
                }
            }
            return null;
        }
    }

    public Billetera buscarBilletera(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario no valido");
        }else {
            for (Billetera billetera : billeteras) {
                if (billetera.getUsuario().equals(usuario)) {
                    return billetera;
                }
            }
            return null;
        }
    }

    public String generarId(){
        String id = "";
        for (int i = 0; i < 10; i++) {
            String num = ""+ (int)(Math.random()*10);
            id += num;
        }
        return id;
    }

    public void RealizarTransaccion(Usuario emisor, LocalDateTime fecha, String categoria, float cantidad, Usuario receptor) throws Exception {
        if (emisor == null|| fecha == null|| cantidad == 0||receptor == null|| categoria.isEmpty()||emisor.equals(receptor)) {
            throw new Exception("Parametros invalidos");
        }else {
            Billetera billeteraemisor = buscarBilletera(emisor);
            Billetera billeterareceptor = buscarBilletera(receptor);
            if (billeterareceptor == null|| billeteraemisor == null) {
                throw new Exception("Usuario no encontrado");
            }else {
                try {
                    Transaccion transaccion = new Transaccion(fecha,categoria,cantidad,billeteraemisor,billeterareceptor);
                    billeteraemisor.DisminuirSaldo(cantidad + transaccion.getCosto());
                    billeterareceptor.AumentarSaldo(cantidad);
                    transacciones.add(transaccion);
                } catch (Exception e) {
                    throw new Exception("Error al realizar la transaccion");
                }
            }
        }
    }

    public void RecargarBilletera(Usuario usuario, float cantidad) throws Exception {
        if (usuario == null) {
            throw new Exception("Usuario no valido");
        }else {
            Billetera billetera = buscarBilletera(usuario);
            if (billetera == null) {
                throw new Exception("Usuario no encontrado");
            }else{
                billetera.AumentarSaldo(cantidad);
            }
        }
    }

    public ArrayList<Transaccion> buscarTransacciones(Billetera billetera) throws Exception {
        if (billetera == null) {
            throw new Exception("Billetera no valida");
        }else{
            ArrayList<Transaccion> transaccioness = new ArrayList<>();
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getEmisor().equals(billetera)|| transaccion.getReceptor().equals(billetera)) {
                    transaccioness.add(transaccion);

                }
            }
            return transaccioness;
        }
    }
    public String consultarSaldoyTransacciones(String cedula, String contrasena)throws Exception{
        Billetera billetera = validarUsarioContrasena(cedula,contrasena);
        String respuesta = "Saldo:"+ billetera.getSaldo() + "\nTransacciones:\n";
        ArrayList<Transaccion> transacciones = buscarTransacciones(billetera);
        for (Transaccion transaccion : transacciones) {
            respuesta += transaccion.toString() + "\n";
        }
        return respuesta;
    }

    public float GetsaldoBilletera(Billetera billetera) throws Exception {
        if (billetera == null) {
            throw new Exception("Billetera no valida");
        }else {
            return billetera.getSaldo();
        }
    }

    public ArrayList<Transaccion> consultarTransaccionesFecha(String cedula, String contrasena, LocalDateTime fechainicio,LocalDateTime fechafin)throws Exception{
        if (fechainicio == null || fechafin == null) {
            throw new Exception("Fecha invalida");
        }else {
            Billetera billetera = validarUsarioContrasena(cedula,contrasena);
            ArrayList<Transaccion> transaccionesfecha = new ArrayList<>();
            ArrayList<Transaccion> transacciones = buscarTransacciones(billetera);
            for (Transaccion transaccion : transacciones) {
                if (transaccion.getFecha().isAfter(fechainicio) && transaccion.getFecha().isBefore(fechafin)) {
                    transaccionesfecha.add(transaccion);

                }
            }
            return transaccionesfecha;
        }
    }

    public Billetera validarUsarioContrasena(String cedula, String contrasena) throws Exception {
        if (cedula.isEmpty() || contrasena.isEmpty()) {
            throw new Exception("Parametros invalidos");
        }else {
            Usuario usuario = buscarUsuario(cedula);
            if (usuario == null) {
                throw new Exception("Usuario no encontrado");
            }else {
                if (usuario.getContrasena().equals(contrasena)) {
                    Billetera billetera = buscarBilletera(usuario);
                    if (billetera == null) {
                        throw new Exception("Usuario no encontrado");
                    }else{
                        return billetera;
                    }
                }else{
                    throw new Exception("Contraseña invalida");
                }
            }
        }
    }

    public String obtenerPorcentajeGastosIngresos(String cedula, String contrasena, LocalDateTime fechainicio,LocalDateTime fechafin)throws Exception{
        Billetera billetera = validarUsarioContrasena(cedula, contrasena);
        ArrayList<Transaccion> transaccionestotales = consultarTransaccionesFecha(cedula,  contrasena,  fechainicio, fechafin);
        ArrayList<String> categorias = obtenerCategorias(transaccionestotales);
        String respuesta = "";
        float totalGastos = calcularTotalGastos(transaccionestotales,billetera);
        float totalIngresos = calcularTotalIngresos(transaccionestotales,billetera);
        ArrayList<Transaccion> aux = new ArrayList<>();
        while(fechainicio.isBefore(fechafin)) {
            LocalDateTime inicio = fechainicio;
            fechainicio = fechainicio.plusMonths(1);
            LocalDateTime fin = fechainicio;
            aux = consultarTransaccionesFecha(cedula, contrasena, inicio, fin);
            respuesta += "Fecha inicio: "+ inicio + " - Fecha fin: "+ fin + "\n";
            float gastosmes = calcularTotalGastos(aux,billetera);
            float porcentajegastos = calcularPorcentaje(gastosmes,totalGastos);
            respuesta += "% Gastos del mes: " + porcentajegastos + "%\n";
            for (String categoria : categorias) {
                float gastoscategoria = calcularGastosCategoria(aux,billetera,categoria);
                porcentajegastos = calcularPorcentaje(gastoscategoria,gastosmes);
                respuesta += porcentajegastos + "% Gastos de la categoria: " + categoria + "\n";
            }

            float ingresosmes = calcularTotalIngresos(aux,billetera);
            float porcentajeingresos = calcularPorcentaje(ingresosmes,totalIngresos);
            respuesta += "% Ingresos del mes: " + porcentajeingresos+ "%\n";
            for (String categoria : categorias) {
                float ingresoscategoria = calcularIngresosCategoria(aux,billetera,categoria);
                porcentajeingresos = calcularPorcentaje(ingresoscategoria,ingresosmes);
                respuesta += porcentajeingresos + "% Ingresos de la categoria: " + categoria + "\n";
            }

        }
        return respuesta;
    }

    public ArrayList<String> obtenerCategorias(ArrayList<Transaccion> transaccionestotales)throws Exception{
        ArrayList<String> categorias = new ArrayList<>();
        for (Transaccion transaccion : transaccionestotales) {
            categorias.add(transaccion.getCategoria());
        }
        return categorias;
    }

    public float calcularTotalGastos(ArrayList<Transaccion> transacciones,Billetera billetera){
        float totalGastos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEmisor().equals(billetera)){
                totalGastos += transaccion.getMonto();
                totalGastos += transaccion.getCosto();
            }
        }
        return totalGastos;
    }
    public float calcularGastosCategoria(ArrayList<Transaccion> transacciones,Billetera billetera,String categoria){
        float totalGastos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEmisor().equals(billetera) && transaccion.getCategoria().equals(categoria)) {
                totalGastos += transaccion.getMonto();
                totalGastos += transaccion.getCosto();
            }
        }
        return totalGastos;
    }
    public float calcularIngresosCategoria(ArrayList<Transaccion> transacciones,Billetera billetera,String categoria){
        float totalGastos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getReceptor().equals(billetera) && transaccion.getCategoria().equals(categoria)) {
                totalGastos += transaccion.getMonto();
            }
        }
        return totalGastos;
    }

    public float calcularTotalIngresos(ArrayList<Transaccion> transacciones, Billetera billetera){
        float totalIngresos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getReceptor().equals(billetera)){
                totalIngresos += transaccion.getMonto();
            }
        }
        return totalIngresos;
    }
    public float calcularPorcentaje(float actual, float total){
        return (actual / total)*100;
    }
}
