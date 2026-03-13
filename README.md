# 🏦 Banco Digital (versión consola)

Proyecto académico desarrollado en Java para la **Universidad del Quindío** como parte del curso de Programación Orientada a Objetos (POO). Simula un sistema bancario con billeteras virtuales, transferencias entre usuarios, comisión fija por transacción y reportes de gastos e ingresos desglosados por mes y categoría.

---

## 📁 Estructura del proyecto

```
src/
├── main/java/
│   ├── App/
│   │   └── App.java              # Punto de entrada (placeholder)
│   └── Model/
│       ├── Banco.java            # Clase central con toda la lógica de negocio
│       ├── Usuario.java          # Entidad usuario con credenciales
│       ├── Billetera.java        # Billetera con saldo y operaciones
│       └── Transaccion.java      # Entidad transacción con UUID y costo fijo
└── test/java/
    ├── TestBanco.java            # Pruebas de CRUD, transferencias y reportes
    └── TestBilletera.java        # Pruebas de aumento y disminución de saldo
```

---

## 🧩 Modelo de clases

### `Usuario`
Entidad con `nombre`, `direccion`, `id`, `correo` y `contrasena`. El `id` funciona como clave única de identificación.

### `Billetera`
Asociada a un único usuario. El saldo inicial es **0**. Su `numeroID` de 10 dígitos se genera aleatoriamente garantizando unicidad en el banco.

- `AumentarSaldo(float)` — Lanza excepción si el monto es ≤ 0.
- `DisminuirSaldo(float)` — Lanza excepción si el monto es ≤ 0 o si el saldo resultante sería negativo.

### `Transaccion`
Cada transacción tiene un **costo fijo de 200** unidades que se descuenta al emisor adicionalmente al monto transferido. Genera un `UUID` único automáticamente. Los campos `emisor`, `receptor` e `id` son `final` e inmutables tras la creación.

### `Banco`
Clase central con tres `ArrayList`: usuarios, billeteras y transacciones.

**Gestión de usuarios:**

| Método | Descripción |
|---|---|
| `registrarUsuarios(Usuario)` | Lanza excepción si es nulo o si el ID ya existe |
| `buscarUsuario(String id)` | Lanza excepción si el ID es nulo o vacío; retorna null si no existe |
| `eliminarUsuario(Usuario)` | Lanza excepción si no existe |
| `ActualizarUsuario(Usuario, Usuario)` | Permite cambiar el ID siempre que el nuevo no esté en uso por otro usuario |

**Gestión de billeteras:**

| Método | Descripción |
|---|---|
| `CrearBilletera(Usuario)` | Genera ID único de 10 dígitos, crea la billetera y la asocia al usuario |
| `buscarBilletera(String id)` | Búsqueda por número de billetera |
| `buscarBilletera(Usuario)` | Búsqueda por usuario asociado |
| `RecargarBilletera(Usuario, float)` | Suma directamente al saldo sin comisión |

**Transacciones:**

| Método | Descripción |
|---|---|
| `RealizarTransaccion(emisor, fecha, categoria, cantidad, receptor)` | Valida datos, descuenta `cantidad + 200` al emisor y acredita `cantidad` al receptor |
| `RealizarTransaccion(emisor, categoria, cantidad, receptor)` | Igual pero usa `LocalDateTime.now()` como fecha |
| `buscarTransacciones(Billetera)` | Retorna todas las transacciones donde la billetera es emisora o receptora |
| `validarUsarioContrasena(cedula, contrasena)` | Busca el usuario por cédula, valida la contraseña y retorna su billetera |

**Reportes financieros:**

| Método | Descripción |
|---|---|
| `consultarSaldoyTransacciones(cedula, contrasena)` | Valida credenciales y retorna String con saldo actual y lista de transacciones |
| `consultarTransaccionesFecha(cedula, contrasena, inicio, fin)` | Filtra transacciones entre dos `LocalDateTime` |
| `obtenerPorcentajeGastosIngresos(cedula, contrasena, inicio, fin)` | Itera mes a mes entre las fechas y genera un String con porcentajes de gastos e ingresos por mes y por categoría |
| `calcularTotalGastos` / `calcularTotalIngresos` | Sumatoria de egresos/ingresos para una billetera en un conjunto de transacciones |
| `calcularGastosCategoria` / `calcularIngresosCategoria` | Filtrado adicional por categoría dentro de un conjunto de transacciones |
| `calcularPorcentaje(actual, total)` | Retorna `0` si `total ≤ 0` para evitar división por cero |
| `obtenerCategorias(transacciones)` | Extrae y deduplica las categorías presentes en un conjunto de transacciones |

---

## 🧪 Pruebas unitarias

| Test | Qué prueba |
|---|---|
| `testDeUsuario` | Registro, actualización (incluyendo cambio de ID), fallo por duplicado y eliminación |
| `testDeBilletera` | Creación de billetera, recarga, aumento de saldo y disminución de saldo |
| `testDeUsuarioBilletera` | Validación de credenciales correctas e incorrectas; búsqueda de billetera por usuario |
| `testDeTransaccion` | Transferencia exitosa con descuento correcto en emisor y acreditación en receptor; fallo por saldo insuficiente |
| `consultarSaldoYTransaccionestest` | Verifica que el String de respuesta contenga "Saldo:", "Transacciones:", y las categorías registradas; fallo con credenciales inválidas |
| `testDeObtenerValores` | Reporte mensual detallado con 36 transacciones distribuidas a lo largo de los 12 meses de 2024; verifica el String exacto de porcentajes por mes y categoría |
| `AumentarSaldoTest` | Lanza excepción con montos de 0 y negativos; suma correcta con montos válidos |
| `DisminuirSaldoTest` | Lanza excepción con montos inválidos, negativos y cuando no hay saldo suficiente; resta correcta con saldo disponible |

---

## 🛠️ Tecnologías

- **Java** (SE 11+) — `LocalDateTime`, `UUID`, `ArrayList`
- **Maven** (gestión del proyecto)
- **JUnit 5** (pruebas unitarias)

---

## 🚀 Cómo ejecutar

```bash
git clone <url-del-repositorio>
mvn test
```

---

## 📄 Licencia

GNU/GPL V3.0 — [Ver licencia](https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE)
