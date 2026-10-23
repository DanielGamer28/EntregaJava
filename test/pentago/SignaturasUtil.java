package pentago;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Clase auxiliar con métodos para verificar signaturas mediante reflexión.
 *
 * No ejecuta los métodos comprobados: únicamente verifica su existencia,
 * parámetros, tipo de retorno y modificador de acceso.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class SignaturasUtil {

	/** Constructor para permitir la herencia desde las clases de test. */
	protected SignaturasUtil() { }

	/**
	 * Comprueba la signatura de un constructor público.
	 *
	 * @param clase clase a verificar
	 * @param nombreConstructor nombre usado en los mensajes de error
	 * @param tiposParametros tipos de los parámetros formales
	 */
	protected void verificarConstructorPublico(Class<?> clase, String nombreConstructor,
			Class<?>... tiposParametros) {
		try {
			Constructor<?> constructor = clase.getDeclaredConstructor(tiposParametros);
			assertNotNull(constructor, "El constructor " + nombreConstructor + " no existe.");
			assertTrue(Modifier.isPublic(constructor.getModifiers()),
					"El constructor " + nombreConstructor + " debe ser público.");
		} catch (NoSuchMethodException _) {
			fail("El constructor " + nombreConstructor
					+ " no existe con la signatura indicada; es obligatoria su implementación.");
		}
	}

	/**
	 * Comprueba la signatura de un método público.
	 *
	 * @param clase clase a verificar
	 * @param nombreMetodo nombre del método
	 * @param tipoRetorno tipo de retorno esperado
	 * @param tiposParametros tipos de los parámetros formales
	 */
	protected void verificarMetodoPublico(Class<?> clase, String nombreMetodo, Class<?> tipoRetorno,
			Class<?>... tiposParametros) {
		Method metodo = obtenerMetodo(clase, nombreMetodo, tiposParametros);
		assertTrue(Modifier.isPublic(metodo.getModifiers()),
				"El método " + signatura(nombreMetodo, tiposParametros) + " debe ser público.");
		verificarTipoRetorno(metodo, nombreMetodo, tipoRetorno);
	}

	/**
	 * Comprueba la signatura de un método privado.
	 *
	 * @param clase clase a verificar
	 * @param nombreMetodo nombre del método
	 * @param tipoRetorno tipo de retorno esperado
	 * @param tiposParametros tipos de los parámetros formales
	 */
	protected void verificarMetodoPrivado(Class<?> clase, String nombreMetodo, Class<?> tipoRetorno,
			Class<?>... tiposParametros) {
		Method metodo = obtenerMetodo(clase, nombreMetodo, tiposParametros);
		assertTrue(Modifier.isPrivate(metodo.getModifiers()),
				"El método " + signatura(nombreMetodo, tiposParametros) + " debe ser privado.");
		verificarTipoRetorno(metodo, nombreMetodo, tipoRetorno);
	}

	/**
	 * Comprueba la signatura de un método con visibilidad de paquete
	 * (sin modificador public, protected ni private).
	 *
	 * @param clase clase a verificar
	 * @param nombreMetodo nombre del método
	 * @param tipoRetorno tipo de retorno esperado
	 * @param tiposParametros tipos de los parámetros formales
	 */
	protected void verificarMetodoPaquete(Class<?> clase, String nombreMetodo, Class<?> tipoRetorno,
			Class<?>... tiposParametros) {
		Method metodo = obtenerMetodo(clase, nombreMetodo, tiposParametros);
		int modificadores = metodo.getModifiers();
		assertFalse(Modifier.isPublic(modificadores),
				"El método " + signatura(nombreMetodo, tiposParametros)
						+ " debe tener visibilidad de paquete, no public.");
		assertFalse(Modifier.isProtected(modificadores),
				"El método " + signatura(nombreMetodo, tiposParametros)
						+ " debe tener visibilidad de paquete, no protected.");
		assertFalse(Modifier.isPrivate(modificadores),
				"El método " + signatura(nombreMetodo, tiposParametros)
						+ " debe tener visibilidad de paquete, no private.");
		verificarTipoRetorno(metodo, nombreMetodo, tipoRetorno);
	}

	/**
	 * Obtiene un método declarado con una signatura concreta.
	 *
	 * @param clase clase
	 * @param nombreMetodo nombre
	 * @param tiposParametros tipos de parámetros
	 * @return método encontrado
	 */
	private Method obtenerMetodo(Class<?> clase, String nombreMetodo, Class<?>... tiposParametros) {
		try {
			Method metodo = clase.getDeclaredMethod(nombreMetodo, tiposParametros);
			assertNotNull(metodo, "El método " + signatura(nombreMetodo, tiposParametros) + " no existe.");
			return metodo;
		} catch (NoSuchMethodException _) {
			fail("El método " + signatura(nombreMetodo, tiposParametros)
					+ " no existe con la signatura indicada; es obligatoria su implementación.");
			return null; // Inalcanzable tras fail(), necesario para el compilador.
		}
	}

	/**
	 * Comprueba el tipo de retorno.
	 *
	 * @param metodo método
	 * @param nombreMetodo nombre del método
	 * @param tipoRetorno tipo esperado
	 */
	private void verificarTipoRetorno(Method metodo, String nombreMetodo, Class<?> tipoRetorno) {
		assertThat("El método " + nombreMetodo + " debe devolver " + tipoRetorno.getTypeName(),
				metodo.getReturnType(), equalTo(tipoRetorno));
	}

	/**
	 * Construye un texto legible con una signatura.
	 *
	 * @param nombreMetodo nombre
	 * @param tiposParametros parámetros
	 * @return texto de la signatura
	 */
	private String signatura(String nombreMetodo, Class<?>... tiposParametros) {
		String parametros = Arrays.stream(tiposParametros)
				.map(Class::getSimpleName)
				.reduce((a, b) -> a + ", " + b)
				.orElse("");
		return nombreMetodo + "(" + parametros + ")";
	}

	/**
	 * Convierte nombres sencillos y nombres de clase en objetos Class.
	 *
	 * @param nombre nombre del tipo
	 * @return clase correspondiente
	 * @throws ClassNotFoundException si no existe
	 */
	protected Class<?> obtenerTipo(String nombre) throws ClassNotFoundException {
		return switch (nombre) {
		case "void" -> void.class;
		case "boolean" -> boolean.class;
		case "int" -> int.class;
		case "Coordenada" -> pentago.util.Coordenada.class;
		case "Color" -> pentago.modelo.Color.class;
		case "Celda" -> pentago.modelo.Celda.class;
		case "Tablero" -> pentago.modelo.Tablero.class;
		case "String" -> java.lang.String.class;
		default -> Class.forName(nombre);
		};
	}
}
