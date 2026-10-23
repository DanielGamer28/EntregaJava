package pentago.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pentago.SignaturasUtil;
import pentago.util.Coordenada;
import pentago.util.Direccion;

/**
 * Tests de signatura para la clase Tablero.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see pentago.modelo.Tablero
 */
@DisplayName("Tests sobre signaturas de Tablero")
public class SignaturasTableroTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasTableroTest() {
	}

	/**
	 * Comprueba Tablero(int, int).
	 */
	@Test
	@DisplayName("Tablero(int, int) es público")
	void testConstructor() {
		verificarConstructorPublico(Tablero.class, "Tablero(int, int)", int.class, int.class);
	}

	/**
	 * Comprueba métodos públicos sin argumentos.
	 *
	 * @param nombreMetodo nombre
	 * @param tipoRetorno  retorno esperado
	 * @throws ClassNotFoundException si la clase no existe
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos sin argumentos")
	@CsvSource({ 
	         "aTexto, String",
	         "clonar, Tablero",
		     "consultarNumeroFilas, int", 
		     "consultarNumeroColumnas, int", 
		     "estaCompleto, boolean",
			 "toString, String",
			 "hashCode, int"
			 })
	void testMetodosPublicosSinParametros(String nombreMetodo, String tipoRetorno) throws ClassNotFoundException {
		Class<?> retorno = obtenerTipo(tipoRetorno);
		verificarMetodoPublico(Tablero.class, nombreMetodo, retorno);
	}

	/**
	 * Comprueba métodos públicos con un argumento.
	 *
	 * @param nombreMetodo  nombre
	 * @param tipoRetorno   retorno
	 * @param tipoParametro parámetro
	 * @throws ClassNotFoundException si la clase no se encuentra
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos con un argumento")
	@CsvSource({ "consultarCelda, Celda, Coordenada", "estaEnTablero, boolean, Coordenada",
			"consultarNumeroPiezas, int, Color" })
	void testMetodosPublicosConUnParametro(String nombreMetodo, String tipoRetorno, String tipoParametro) throws ClassNotFoundException {
		Class<?> retorno = obtenerTipo(tipoRetorno);
		Class<?> parametro = obtenerTipo(tipoParametro);
		verificarMetodoPublico(Tablero.class, nombreMetodo, retorno, parametro);
	}

	/**
	 * Comprueba colocar(Pieza, Coordenada).
	 */
	@Test
	@DisplayName("colocar(Pieza, Coordenada) es público")
	void testColocar() {
		verificarMetodoPublico(Tablero.class, "colocar", void.class, Pieza.class, Coordenada.class);
	}

	/**
	 * Comprueba contarPiezasConsecutivas(Coordenada, Direccion).
	 */
	@Test
	@DisplayName("contarPiezasConsecutivas(Coordenada, Direccion) es público")
	void testContarPiezasConsecutivas() {
		verificarMetodoPublico(Tablero.class, "contarPiezasConsecutivas", int.class, Coordenada.class, Direccion.class);
	}

	/**
	 * Comprueba consultarRegion(Coordenada, int).
	 */
	@Test
	@DisplayName("consultarRegion(Coordenada, int, int) es público")
	void testConsultarRegion() {
		verificarMetodoPublico(Tablero.class, "consultarRegion", Region.class, Coordenada.class, int.class, int.class);
	}

	/**
	 * Comprueba restaurarPiezas(Coordenada, Region).
	 */
	@Test
	@DisplayName("restaurarPiezas(Coordenada, Region) es público")
	void testRestaurarPiezas() {
		verificarMetodoPublico(Tablero.class, "restaurarPiezas", void.class, Coordenada.class, Region.class);
	}

	/**
	 * Comprueba obtenerCelda() con visibilidad de paquete.
	 */
	@Test
	@DisplayName("obtenerCelda() con visibilidad de paquete")
	void testObtenerCelda() {
		verificarMetodoPaquete(Tablero.class, "obtenerCelda", 
				pentago.modelo.Celda.class, pentago.util.Coordenada.class);
	}
	
	/**
	 * Comprueba equals(Object).
	 */
	@Test
	@DisplayName("equals(Object) es público")
	void testObject() {
		verificarMetodoPublico(Tablero.class, "equals", boolean.class, Object.class);
	}
}
