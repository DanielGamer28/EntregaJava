package pentago.util;

/**
 * Dirección de movimiento en tablero.
 *
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public enum Direccion {	

	/**
	 * Horizontal.
	 */
	HORIZONTAL(+0, +1), 
	
	/**
	 * Vertical.
	 */
	VERTICAL(+1, +0),
	
	/**
	 * Dirección SO a NE.
	 */
	DIAGONAL_SO_NE(+1, -1),
	
	/**
	 * Dirección NO a SE.
	 */
	DIAGONAL_NO_SE(+1, +1);
	
	/**
	 * Desplazamiento en filas.
	 */
	private int desplazamientoEnFilas;
	
	/**
	 * Desplazamiento en columnas.
	 */
	private int desplazamientoEnColumnas;
	
	/**
	 * Constructor de la enumeración.
	 * 
	 * @param desplazamientoEnFilas desplazamiento en filas
	 * @param desplazamientoEnColumnas desplazamiento en columnas
	 */
	Direccion(int desplazamientoEnFilas, int desplazamientoEnColumnas) {
		this.desplazamientoEnFilas = desplazamientoEnFilas;
		this.desplazamientoEnColumnas = desplazamientoEnColumnas;
	}
	
	/**
	 * Devuelve el desplazamiento en filas.
	 * 
	 * @return desplazamiento en filas
	 */
	public int consultarDesplazamientoEnFilas() {
		return desplazamientoEnFilas;
	}
	
	/**
	 * Devuelve el desplazamiento en columnas.
	 * 
	 * @return desplazamiento en columnas
	 */
	public int consultarDesplazamientoEnColumnas() {
		return desplazamientoEnColumnas;
	}
	
}
