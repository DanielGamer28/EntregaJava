package pentago.modelo;

import java.util.Objects;

import pentago.util.Coordenada;

/**
 * Celda del tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * 
 * @version 1.0
 * @see pentago.modelo.Tablero
 */
public class Celda {
	
	/**
	 * Pieza contenida en la celda.
	 */
	private Pieza pieza;
	
	/** Coordenada. */
	private Coordenada coordenada;
	
	/**
	 * Construye una celda. 
	 * 
	 * @param coordenada coordenada
	 */
	public Celda(Coordenada coordenada){		
		this.establecerCoordenada(coordenada);
	}
	
	/**
	 * Establece la coordenda.
	 * 
	 * @param coordenada coordenada
	 */
	private void establecerCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	
	/**
	 * Consulta la pieza colocada en la celda.
	 * 
	 * @return pieza
	 */
	public Pieza consultarPieza(){
		return pieza;
	}
	
	/** 
	 * Establece la pieza.
	 * 
	 * @param pieza pieza
	 */
	void establecerPieza(Pieza pieza){
		this.pieza = pieza;
	}	
	
	/**
	 * Comprueba si la celda contiene una pieza o no.
	 * 
	 * @return true si contiene pieza, false en caso contrario
	 */
	public boolean estaVacia(){
		return pieza == null;
	}
	
	/**
	 * Obtiene la coordenada.
	 * 
	 * @return coordenada
	 */
	public Coordenada consultarCoordenada() {
		return coordenada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordenada, pieza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza);
	}

	@Override
	public String toString() {
		return "Celda [pieza=" + pieza + ", coordenada=" + coordenada + "]";
	}

} // Celda