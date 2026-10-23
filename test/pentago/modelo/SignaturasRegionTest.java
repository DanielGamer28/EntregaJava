package pentago.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pentago.SignaturasUtil;
import pentago.util.Coordenada;

/**
 * Tests de signatura para la clase Region.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see pentago.modelo.Region
 */
@DisplayName("Tests sobre signaturas de Region")
public class SignaturasRegionTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasRegionTest() { }

	/**
	 * Comprueba Region(int, int).
	 */
	@Test
	@DisplayName("Region(int, int) es público")
	void testConstructorFilasColumnas() {
		verificarConstructorPublico(Region.class,
				"Region(int, int)", int.class, int.class);
	}

	/**
	 * Comprueba Region(Celda[][]).
	 */
	@Test
	@DisplayName("Region(Celda[][]) es público")
	void testConstructorMatrizCeldas() {
		verificarConstructorPublico(Region.class,
				"Region(Celda[][])", Celda[][].class);
	}

	/**
	 * Comprueba métodos públicos sin parámetros.
	 *
	 * @param nombreMetodo nombre
	 * @param nombreTipoRetorno retorno
	 * @throws ClassNotFoundException si no puede cargarse el tipo
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos sin parámetros")
	@CsvSource({
		"aTexto, java.lang.String",
		"consultarNumeroColumnas, int",
		"consultarNumeroFilas, int",
		"rotarSentidoHorario, pentago.modelo.Region",
		"rotarSentidoAntihorario, pentago.modelo.Region",
		"toString, java.lang.String",
		"hashCode, int"
	})
	void testMetodosPublicosSinParametros(String nombreMetodo, String nombreTipoRetorno)
			throws ClassNotFoundException {
		verificarMetodoPublico(Region.class, nombreMetodo,
				obtenerTipo(nombreTipoRetorno));
	}

	/**
	 * Comprueba métodos públicos con Coordenada.
	 *
	 * @param nombreMetodo nombre
	 * @param nombreTipoRetorno retorno
	 * @throws ClassNotFoundException si no puede cargarse el tipo
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos con Coordenada")
	@CsvSource({
		"consultarCelda, pentago.modelo.Celda",
		"estaEnRegion, boolean"
	})
	void testMetodosPublicosConCoordenada(String nombreMetodo, String nombreTipoRetorno)
			throws ClassNotFoundException {
		Class<?> retorno = obtenerTipo(nombreTipoRetorno);
		verificarMetodoPublico(Region.class, nombreMetodo,
				retorno, Coordenada.class);
	}
	
	/**
	 * Comprueba equals(Object).
	 */
	@Test
	@DisplayName("equals(bject) es público")
	void testEquals() {
		verificarMetodoPublico(Region.class, "equals",
				boolean.class, java.lang.Object.class);
	}

	/**
	 * Comprueba colocar(Pieza, Coordenada).
	 */
	@Test
	@DisplayName("colocar(Pieza, Coordenada) es público")
	void testColocar() {
		verificarMetodoPublico(Region.class, "colocar",
				void.class, Pieza.class, Coordenada.class);
	}
}