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

    public ArrayList<String> consultarSaldoYtransacciones(String cedula, String contrasena) throws Exception {
        validarUsarioContrasena(cedula, contrasena);
        ArrayList<String> resultado = new ArrayList<>();
        Usuario usuario = buscarUsuario(cedula);
        Billetera billetera = buscarBilletera(usuario);
        resultado.add("Saldo: " + billetera.getSaldo() + "\nTransacciones:\n");
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEmisor().equals(billetera)||transaccion.getReceptor().equals(billetera)) {
                resultado.add(transaccion.toString());
            }
        }
        return resultado;
    }

    public ArrayList<Transaccion> consultarTransacciones(String cedula, String contrasena, LocalDateTime fechainicio, LocalDateTime fechaFin) throws Exception {
        if (cedula == null || cedula.isEmpty()||fechainicio == null || fechaFin == null) {
            throw new Exception("Parametros invalidos");
        }
        validarUsarioContrasena(cedula, contrasena);
        ArrayList<Transaccion> resultado = new ArrayList<>();
        Usuario usuario = buscarUsuario(cedula);
        Billetera billetera = buscarBilletera(usuario);
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getEmisor().equals(billetera)||transaccion.getReceptor().equals(billetera)) {
                if (transaccion.getFecha().isAfter(fechainicio) && transaccion.getFecha().isBefore(fechaFin)) {
                    resultado.add(transaccion);
                }
            }
        }
        return resultado;
    }
    public void validarUsarioContrasena(String cedula, String contrasena) throws Exception {
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
                    }
                }else{
                    throw new Exception("Contraseña invalida");
                }
            }
        }
    }
}
