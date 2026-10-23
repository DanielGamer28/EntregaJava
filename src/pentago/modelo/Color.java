package pentago.modelo;

/**
 * Color de las piezas del juego.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * 
 * @version 1.0
 * @see pentago.modelo.Pieza
 * @see pentago.modelo.Jugador
 */
public enum Color {

	/**
	 * Blanco.
	 */
	BLANCO('O'),

	/**
	 * Negro.
	 */
	NEGRO('X');

	/**
	 * Carácter o letra correspondiente.
	 */
	private char caracter;

	/**
	 * Constructor.
	 * 
	 * @param caracter letra
	 */
	Color(char caracter) { // por defecto es private en un enum
		this.caracter = caracter;
	}

	/**
	 * Consulta el carácter asociado.
	 * 
	 * @return carácter o letra identificativa
	 */
	public char aCaracter() {
		return caracter;
	}
}
