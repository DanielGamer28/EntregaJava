package pentago.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pentago.SignaturasUtil;
import pentago.modelo.Jugador;
import pentago.modelo.Region;
import pentago.modelo.Tablero;
import pentago.util.Coordenada;

/**
 * Tests de signatura para la clase Controlador.
 *
 * Se comprueba únicamente la interfaz estructural de la clase mediante
 * reflexión; no se ejecuta la lógica de los métodos.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 * @see pentago.control.Controlador
 */
@DisplayName("Tests sobre signaturas de Controlador")
public class SignaturasControladorTest extends SignaturasUtil {

	/** Constructor. */
	private SignaturasControladorTest() { }

	/**
	 * Comprueba el constructor público.
	 */
	@Test
	@DisplayName("Controlador(Tablero, Configuracion) es público")
	void testConstructor() {
		verificarConstructorPublico(Controlador.class,
				"Controlador(Tablero, Configuracion)",
				Tablero.class, Configuracion.class);
	}

	/**
	 * Comprueba métodos públicos sin parámetros.
	 *
	 * @param nombreMetodo nombre
	 * @param nombreTipoRetorno tipo de retorno
	 * @throws ClassNotFoundException si no puede cargarse el tipo
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos sin parámetros")
	@CsvSource({
		"cambiarTurno, void",
		"consultarConfiguracion, pentago.control.Configuracion",
		"consultarTablero, pentago.modelo.Tablero",
		"estaAcabado, boolean",		
		"obtenerGanador, pentago.modelo.Jugador",
		"obtenerTurno, pentago.modelo.Jugador"		
	})
	void testMetodosPublicosSinParametros(String nombreMetodo, String nombreTipoRetorno)
			throws ClassNotFoundException {
		verificarMetodoPublico(Controlador.class, nombreMetodo,
				obtenerTipo(nombreTipoRetorno));
	}

	/**
	 * Comprueba métodos públicos con un parámetro.
	 *
	 * @param nombreMetodo nombre
	 * @param nombreTipoRetorno tipo de retorno
	 * @param nombreTipoParametro tipo del parámetro
	 * @throws ClassNotFoundException si no puede cargarse algún tipo
	 */
	@ParameterizedTest
	@DisplayName("Métodos públicos con un parámetro")
	@CsvSource({
		"colocarPieza, void, pentago.util.Coordenada",
		"esLegalColocar, boolean, pentago.util.Coordenada",
		"registrarJugador, void, java.lang.String",		
		"rotarRegionSentidoAntihorario, void, pentago.control.Cuadrante",
		"rotarRegionSentidoHorario, void, pentago.control.Cuadrante"		
	})
	void testMetodosPublicosConUnParametro(String nombreMetodo, String nombreTipoRetorno,
			String nombreTipoParametro) throws ClassNotFoundException {
		verificarMetodoPublico(Controlador.class, nombreMetodo,
				obtenerTipo(nombreTipoRetorno), obtenerTipo(nombreTipoParametro));
	}
	
	
	/**
	 * Comprueba obtenerTablero tiene visibilidad de paquete.
	 */
	@Test
	@DisplayName("obtenerTablero() tiene visibilidad de paquete")
	void testObtenerTablero() {
		verificarMetodoPaquete(Controlador.class, "obtenerTablero", pentago.modelo.Tablero.class);
	}

}
