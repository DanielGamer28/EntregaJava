package pentago.modelo;

import java.util.Objects;

/**
 * Identificación de un participante en el juego.
 * 
 * @author <a href="mailto:cbb1013@alu.ubu.es">Claudia Berezo Berzosa</a>
 * @author <a href="mailto:dam1012@alu.ubu.es">Daniel Acitores Martinez</a>
 * @version 1.0
 * @since 1.0
 */
public class Jugador {
	/**
	 * Nombre.
	 */ 
	private String nombre;
	
	/**
	 * Color asignado.
	 */
	private Color color;

	/**
 	* Construye un jugador.
 	*
	* @param nombre nombre
 	* @param color color
 	*/
	public Jugador( String nombre, Color color ) {
		this.nombre = nombre;
		this.color = color;
	}

	/**
	 * Consulta el color asignado al jugador.
	 *
	 * @return devuelve el color
	 */
	public Color consultarColor() {
		return color;
	}

	/**
	 * Consulta el nombre del jugador.
	 *
	 * @return Devuelve el nombre
	 */
	public String consultarNombre() {
		return nombre;
	}
	
	/**
	 * Genera una pieza del color actual del jugador.
	 * 
	 * @return nueva pieza
	 */
	public Pieza generarPieza() {
		return new Pieza(color);
	}	
	
	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", color=" + color + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return color == other.color && Objects.equals(nombre, other.nombre);
	}
}
