package pentago.control;

import pentago.util.Coordenada;

/**
 * Configuración del juego Pentago.
 * 
 * Contiene información sobre el tamaño del tablero y proporciona métodos para
 * consultar el tamaño de las regiones, los cuadrantes y las coordenadas de
 * referencia.
 *
 * 
 * @param tamañoTablero            tamaño del tablero de juego
 * @param numeroPiezasConsecutivas número de piezas consecutivas necesarias para
 *                                 ganar
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public record Configuracion(int tamañoTablero, int numeroPiezasConsecutivas) {

	/** Número de piezas consecutivas mínimo en una partida. */
	private static final int NUM_CONSECUTIVAS_MINIMO = 3;

	/** Tamaño por defecto. */
	private static final int TAMAÑO_POR_DEFECTO = 6;

	/** Tamaño máximo de tablero. */
	private static final int TAMAÑO_MAXIMO = 12;

	/** Tamaño mínimo de tablero. */
	private static final int TAMAÑO_MINIMO = 4;

	/**
	 * Constructor compacto de la clase Configuracion.
	 * 
	 * Su función es validar los parámetros de entrada y asignar valores por defecto
	 * si son inválidos.
	 * 
	 * Se fija como tamaño del tablero un valor por defecto de 6 si el tamaño del
	 * tablero es menor que 4, mayor que 12 o no es par.
	 * 
	 * 
	 * 
	 * @param tamañoTablero            tamaño del tablero de juego
	 * @param numeroPiezasConsecutivas número de piezas consecutivas necesarias para
	 *                                 ganar
	 */
	public Configuracion { // contructor compacto
		if (tamañoTablero < TAMAÑO_MINIMO || tamañoTablero > TAMAÑO_MAXIMO || tamañoTablero % 2 != 0) {
			tamañoTablero = TAMAÑO_POR_DEFECTO; // Valor por defecto si el tamaño del tablero es inválido
		}
		if (numeroPiezasConsecutivas < NUM_CONSECUTIVAS_MINIMO || numeroPiezasConsecutivas > tamañoTablero - 1) {
			numeroPiezasConsecutivas = tamañoTablero - 1; // Valor por defecto si el número de piezas consecutivas es
															// inválido
		}
	}

	/**
	 * Consulta el tamaño de los cuadrantes del tablero de juego.
	 * 
	 * Por defecto, el tamaño de las regiones es la mitad del tamaño del tablero.
	 * 
	 * @return tamaño de las regiones del tablero de juego
	 */
	public int consultarTamañoCuadrante() {
		return tamañoTablero / 2;
	}

	/**
	 * Consulta el cuadrante al que pertenece una coordenada según el tamaño del
	 * tablero.
	 * 
	 * @param coordenada coordenada a consultar
	 * @return cuadrante al que pertenece la coordenada
	 */
	public Cuadrante consultarCuadrante(Coordenada coordenada) {
		int fila = coordenada.fila();
		int columna = coordenada.columna();
		Cuadrante cuadrante = null;

		if (estaEnPrimerCuadrante(fila, columna)) {
			cuadrante = Cuadrante.SUPERIOR_IZQUIERDO;
		} else if (estaEnSegundoCuadrante(fila, columna)) {
			cuadrante = Cuadrante.SUPERIOR_DERECHO;
		} else if (estaEnTercerCuadrante(fila, columna)) {
			cuadrante = Cuadrante.INFERIOR_IZQUIERDO;
		} else if (estaEnCuartoCuadrante(fila, columna)) {
			cuadrante = Cuadrante.INFERIOR_DERECHO;
		}
		return cuadrante;

	}

	/**
	 * Comprueba si las coordenadas están en el cuarto cuadrante.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 * @return true si están en el cuarto cuadrante, false en caso contrario
	 */
	private boolean estaEnCuartoCuadrante(int fila, int columna) {
		return fila >= tamañoTablero / 2 && fila < tamañoTablero && columna >= tamañoTablero / 2
				&& columna < tamañoTablero;
	}

	/**
	 * Comprueba si las coordenadas están en el tercer cuadrante.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 * @return true si están en el tercer cuadrante, false en caso contrario
	 */
	private boolean estaEnTercerCuadrante(int fila, int columna) {
		return fila >= tamañoTablero / 2 && fila < tamañoTablero && columna >= 0 && columna < tamañoTablero / 2;
	}

	/**
	 * Comprueba si las coordenadas están en el segundo cuadrante.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 * @return true si están en el segundo cuadrante, false en caso contrario
	 */
	private boolean estaEnSegundoCuadrante(int fila, int columna) {
		return fila >= 0 && fila < tamañoTablero / 2 && columna >= tamañoTablero / 2 && columna < tamañoTablero;
	}

	/**
	 * Comprueba si las coordenadas están en el primer cuadrante.
	 * 
	 * @param fila    fila
	 * @param columna columna
	 * @return true si están en el primer cuadrante, false en caso contrario
	 */
	private boolean estaEnPrimerCuadrante(int fila, int columna) {
		return fila >= 0 && fila < tamañoTablero / 2 && columna >= 0 && columna < tamañoTablero / 2;
	}

	/**
	 * Consulta la coordenada de referencia de un cuadrante en el tablero de juego.
	 * 
	 * La coordenada de referencia es la esquina superior izquierda del cuadrante.
	 * 
	 * @param cuadrante cuadrante a consultar
	 * @return coordenada de referencia del cuadrante
	 */
	public Coordenada consultarCoordenadaReferencia(Cuadrante cuadrante) {
		return switch (cuadrante) {
		case SUPERIOR_IZQUIERDO -> new Coordenada(0, 0);
		case SUPERIOR_DERECHO -> new Coordenada(0, tamañoTablero / 2);
		case INFERIOR_IZQUIERDO -> new Coordenada(tamañoTablero / 2, 0);
		default -> new Coordenada(tamañoTablero / 2, tamañoTablero / 2);

		};
	}
}
