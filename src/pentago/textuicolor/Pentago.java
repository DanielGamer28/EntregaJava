package pentago.textuicolor;

import pentago.control.Configuracion;
import pentago.control.Controlador;
import pentago.control.Cuadrante;
import pentago.modelo.Tablero;
import pentago.util.Coordenada;

/**
 * Juego del Pentago con interfaz de texto en color.
 * 
 * Contiene la lógica principal de ejecución de una partida.
 * Las operaciones de entrada y salida se delegan en
 * {@link EntradaSalidaColor}.
 * 
 * Ejercicio práctico - Metodología de la Programación.
 * <p>
 * 2º GII - Curso 2026-2027
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @version 1.0
 */
public class Pentago {

	/** Tamaño del tablero de juego. */
	private static final int TAMAÑO_TABLERO = 6;

	/** Número de piezas consecutivas necesarias para ganar. */
	private static final int NUM_PIEZAS_CONSECUTIVAS_GANADOR = 5;

	/** Controlador del juego. */
	private static Controlador controlador;

	/** Constructor privado para evitar instanciación. */
	private Pentago() {
		// Constructor privado para evitar instanciación
	}

	/**
	 * Flujo de ejecución principal del juego.
	 * 
	 * @param args argumentos en línea de comando (se ignoran)
	 */
	public static void main(String[] args) {
		EntradaSalidaColor entradaSalida = new EntradaSalidaColor();

		// Pedir nombres...
		String nombreBlancas = entradaSalida.leerNombrePrimerJugador();
		String nombreNegras = entradaSalida.leerNombreSegundoJugador();

		// Comenzar a jugar...
		jugarPentago(entradaSalida, nombreBlancas, nombreNegras);

		entradaSalida.cerrar();
	}

	/**
	 * Algoritmo básico del juego del Pentago.
	 * 
	 * @param entradaSalida interfaz de entrada y salida
	 * @param nombreBlancas nombre de jugador con blancas
	 * @param nombreNegras  nombre de jugador con negras
	 */
	private static void jugarPentago(EntradaSalidaColor entradaSalida,
			String nombreBlancas, String nombreNegras) {

		// Inicializar controlador y recoger turno...
		controlador = inicializarJuego(nombreBlancas, nombreNegras);
		entradaSalida.mostrarTablero(controlador);

		// Bucle principal
		while (!controlador.estaAcabado()) {
			entradaSalida.mostrarTurnoActual(controlador);
			
			realizarJugada(entradaSalida);
			entradaSalida.mostrarTablero(controlador);

			if (!controlador.estaAcabado()) {
				realizarRotacion(entradaSalida);
				entradaSalida.mostrarTablero(controlador);
			}

			controlador.cambiarTurno();
		}

		entradaSalida.mostrarResultadoFinal(controlador);
	}

	/**
	 * Realiza una jugada solicitando una coordenada hasta encontrar
	 * una colocación legal.
	 * 
	 * @param entradaSalida interfaz de entrada y salida
	 */
	private static void realizarJugada(EntradaSalidaColor entradaSalida) {
		boolean legal = false;

		while (!legal) {
			int fila = entradaSalida.leerFila();
			int columna = entradaSalida.leerColumna();

			Coordenada coordenada = new Coordenada(fila, columna);

			if (controlador.esLegalColocar(coordenada)) {
				controlador.colocarPieza(coordenada);
				legal = true;
			} else {				
				entradaSalida.mostrarMovimientoInvalido();
			}
		}
	}

	/**
	 * Realiza una rotación solicitando el cuadrante y el sentido
	 * hasta encontrar una rotación legal.
	 * 
	 * @param entradaSalida interfaz de entrada y salida
	 */
	private static void realizarRotacion(EntradaSalidaColor entradaSalida) {
		boolean legal = false;

		while (!legal) {
			int region = entradaSalida.leerCuadrante();
			int direccion = entradaSalida.leerSentidoRotacion();

			if (esRotacionLegal(region, direccion)) {
				Cuadrante cuadrante = convertirACuadrante(region);

				if (direccion == 0) {
					controlador.rotarRegionSentidoAntihorario(cuadrante);
				} else {
					controlador.rotarRegionSentidoHorario(cuadrante);
				}

				legal = true;
			} else {				
				entradaSalida.mostrarRotacionInvalida();
			}
		}
	}

	/**
	 * Convierte el valor numérico introducido por el usuario
	 * en el cuadrante correspondiente.
	 * 
	 * Previamente debe haberse comprobado que el valor está
	 * comprendido entre 0 y 3.
	 * 
	 * @param region número de región
	 * @return cuadrante correspondiente
	 */
	private static Cuadrante convertirACuadrante(int region) {
		return switch (region) {
			case 0 -> Cuadrante.SUPERIOR_IZQUIERDO;
			case 1 -> Cuadrante.SUPERIOR_DERECHO;
			case 2 -> Cuadrante.INFERIOR_IZQUIERDO;
			case 3 -> Cuadrante.INFERIOR_DERECHO;
			default -> null;
		};
	}

	/**
	 * Comprueba si la rotación solicitada es legal.
	 * 
	 * @param region    región a rotar
	 * @param direccion dirección de rotación
	 *                  (0: antihorario, 1: horario)
	 * @return true si es legal, false en caso contrario
	 */
	private static boolean esRotacionLegal(int region, int direccion) {
		return region >= 0 && region <= 3
				&& (direccion == 0 || direccion == 1);
	}

	/**
	 * Inicializa el controlador con la configuración para jugar.
	 * 
	 * Registra los dos jugadores.
	 * 
	 * @param nombreBlancas jugador con blancas
	 * @param nombreNegras  jugador con negras
	 * @return controlador configurado
	 */
	private static Controlador inicializarJuego(String nombreBlancas,
			String nombreNegras) {

		Tablero tablero = new Tablero(TAMAÑO_TABLERO, TAMAÑO_TABLERO);

		Configuracion configuracion = new Configuracion(
				TAMAÑO_TABLERO,
				NUM_PIEZAS_CONSECUTIVAS_GANADOR);

		Controlador controlador = new Controlador(tablero, configuracion);

		controlador.registrarJugador(nombreBlancas);
		controlador.registrarJugador(nombreNegras);

		return controlador;
	}
}
