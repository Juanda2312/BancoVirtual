package Model;

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

    public ArrayList<Usuario> getUsuarios() {return usuarios;}

    /**Este método recibe un usuario y lo añade a la lista de usuarios, únicamente si es un usuario valido y no
     * se encuentra ya registrado.
     */
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

    /**Este metodo busca un usuario en la lista du usuarios en base a su ID y lo retorna.
     */
    public Usuario buscarUsuario(String id)throws Exception {
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

    /**Este método recibe un usuario y lo, siempre y cuano este sea valido y se encuentre registrado.
     */
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

    /**Este método recibe dos usuarios, si el primero existe, su informacion sera remplasads por la del segundo,
     * si es valido.
     */
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
    /**Este método añade una billetera a la lista de billeteras, únicamente si es una billetera valida y no se encuentra
     *ya registrada.
     */
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
    /**Este método busca una billetera en la lista de billeteras en base a su ID y la retorna.
     */
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

    /** Este método recibe un usuario y, si este es valido, busca una billetera en la lista de billeteras que este
     * relacionada a dicho usuario y la retorna.
     */
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
    /** Este método genera y retorna un numero de diez digitos generados aleatoreamente.
     */
    public String generarId(){
        String id = "";
        for (int i = 0; i < 10; i++) {
            String num = ""+ (int)(Math.random()*10);
            id += num;
        }
        return id;
    }

    /** Este método recibe dos usuarios, que seran el emisor y reseptor de la transacción, la fella en que se realiza la
     * transacción, la categoria de la transacción y la cantidad de dinero que se movera. Si todos los datos son validos
     * y el emisor tiene saldo suficiente, crea la transacción con los datos rrecividos y la almacena.
     */
    public void RealizarTransaccion(Usuario emisor, LocalDateTime fecha, String categoria, float cantidad, Usuario receptor) throws Exception {
        if (emisor == null|| fecha == null|| cantidad <= 0|| receptor == null|| categoria.isEmpty()||emisor.equals(receptor)) {
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

    /** Este método recibe un usuario y una cantidad, si los dtos son validos, le sumara la cantidad al saldo de la
     *billetera.
     */
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

    /** Este método recibe una billetera y, si es valida, crea una lista con todas las transacciones asociadas a esta
     * billetera y la retorna.
     */
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

    /** Este método recibe dos Strings, una para la cedula y otro para la contraseña de un usuario, si se encuentra
     * una billetera asignada a un usuario con dichas características, se buscaran las transacciones relacionadas a dicha
     * billetera y se creara y retornara un String con el saldo actual y las transacción asociadas a la billetera.
     */
    public String consultarSaldoyTransacciones(String cedula, String contrasena)throws Exception{
        Billetera billetera = validarUsarioContrasena(cedula,contrasena);
        String respuesta = "Saldo:"+ billetera.getSaldo() + "\nTransacciones:\n";
        ArrayList<Transaccion> transacciones = buscarTransacciones(billetera);
        for (Transaccion transaccion : transacciones) {
            respuesta += transaccion.toString() + "\n";
        }
        return respuesta;
    }

    /** Este método recibe una billetera y, si es valida, obtiene y retorna su saldo.
     */
    public float GetsaldoBilletera(Billetera billetera) throws Exception {
        if (billetera == null) {
            throw new Exception("Billetera no valida");
        }else {
            return billetera.getSaldo();
        }
    }

    /** Este método recibe una cedula, una contraseña y dos fechas, una de inicio y una de fin, si hay una billetera
     * asignada a un usuario con esa cedula y contraseña, obtiene todas las transacciones realisadas entre esas fechas
     * relacionadas a dicha billetera y las retorna en un arreglo.
     */
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

    /** Este método recibe una cedula y una contraseña, si los datos son validos, busca el usuario correspondiente a
     * dicha cedula y comprueba que la contraseña del usuario sea la misma que se resivio, de ser así, retorna la billera
     * asignada a dicho usuario.
     */
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

    /** Este método recibe una cedula, una contraseña y dos fechas, una de incio y otra de fin, toma todas las
     * transacciones relacionadas a la billetera asignada al usuario con dichas caracteristicas y obtiene para obtener
     * el total de astos e ingresos, posteriormente, divide los astos en meses y subdivide los meses en categorias. Genera
     * y retorna un mensaje String con toda la información obtenida representada en porsentajes.
     */
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

    /** Este método recibe un arreglo de transacciones, extra las cateorias de dichas transacciones, las introduse en un
     * arreglo y lo retorna.
     */
    public ArrayList<String> obtenerCategorias(ArrayList<Transaccion> transaccionestotales)throws Exception{
        ArrayList<String> categorias = new ArrayList<>();
        for (Transaccion transaccion : transaccionestotales) {
            if(!categorias.contains(transaccion.getCategoria())) {
                categorias.add(transaccion.getCategoria());
            }
        }
        return categorias;
    }

    /** Este método recibe un arreglo de transacciones y una billetera, extrae el monto y costo de tosas las transacciones
     * en las que la billetera es el emisor, suma todos los datos obtenidos y los retorna.
     */
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

    /** Este método recibe un arrelo de transacciones, una billetera y un string, extrae el monto y costo de todas las
     * transacciones en las que la billetera es el emisor que ademas corresponden a la catoria indicada en el String.
     * Suma todos los datos obtenidos y los retorna.
     */
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

    /** Este método recibe un arrelo de transacciones, una billetera y un String, obtiene el monto de todas las
     * transaccions en las que la billetera es el receptor que ademas pertenescan a la categoria indicada en el String.
     * Suma todos los montos obtenidos y los retorna.
     */
    public float calcularIngresosCategoria(ArrayList<Transaccion> transacciones,Billetera billetera,String categoria){
        float totalGastos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getReceptor().equals(billetera) && transaccion.getCategoria().equals(categoria)) {
                totalGastos += transaccion.getMonto();
            }
        }
        return totalGastos;
    }

    /** Este método recibe un arreglo de transacciones y una billetera, obtiene el monto de todas las transacciones en
     * las que la billetera es el receptor, los suma y los retorna.
     */
    public float calcularTotalIngresos(ArrayList<Transaccion> transacciones, Billetera billetera) {
        float totalIngresos = 0f;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getReceptor().equals(billetera)) {
                totalIngresos += transaccion.getMonto();
            }
        }
        return totalIngresos;
    }
    public float calcularPorcentaje(float actual, float total){
        return (actual / total)*100;
    }
}
