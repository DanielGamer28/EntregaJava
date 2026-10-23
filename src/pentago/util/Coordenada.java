package pentago.util;

/**
 * Coordenadas de una celda con sus valores fila y columna.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena</a>
 * @param fila fila
 * @param columna columna
 * @version 1.0
 * @since 1.0
 */
public record Coordenada(int fila, int columna) {
	
	/**
	 * Devuelve la representación en texto de la coordenada.
	 * 
	 * @return representación en texto de la coordenada
	 */
	public String aTexto() {
		return "(" + fila + "," + columna + ")";
	}
}
