package pentago.textuicolor;

import com.diogonunes.jcolor.Attribute;

/**
 * Clase de utilidad con la configuración de colores.
 * 
 * Realmente esta clase e lo que se denomina una "Data class" sin complejidad
 * ciclomática solo conteniendo datos de configuración
 * que preferiblemente debería externalizarse a un fichero properties,
 * un .toml o .csv (u otro formato). 
 * 
 * Por motivos de simplificación, se ha resuelto de esta forma. 
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * 
 * @version 1.0
 * @see pentago.textuicolor.EntradaSalidaColor
 */
public final class ConfiguracionColor { // el uso de final aquí se verá en el tema de herencia
	
	/** Constructor privado para impedir instanciaciones. */
	private ConfiguracionColor() {	}
	
	/** Color fondo jugador con blancas. */
	public static final Attribute FONDO_JUGADOR_BLANCAS = Attribute.BACK_COLOR(255, 0, 0); // Fondo rojo

	/** Color fondo jugador con negras. */
	public static final Attribute FONDO_JUGADOR_NEGRAS = Attribute.BACK_COLOR(0, 150, 255); // Fondo azul cielo intenso
	
	/** Color fondo si no hay pieza. */
	public static final Attribute FONDO_VACÍO_CUADRANTE_A = Attribute.BACK_COLOR(200, 200, 200); // Fondo gris claro
	
	/** Color fondo si no hay pieza. */
	public static final Attribute FONDO_VACÍO_CUADRANTE_B = Attribute.BACK_COLOR(100, 100, 100); // Fondo gris claro

	/** Color texto sin texto. */
	public static final Attribute TEXTO_VACÍO = Attribute.TEXT_COLOR(0, 0, 0); // Negro
	
	/** Color texto para letra o carácter asociado al color de la pieza. */
	public static final Attribute TEXTO_PIEZA = Attribute.TEXT_COLOR(255, 255, 255); // Blanco
	
	/** Color texto para índices numéricos del tablero. */
	public static final Attribute TEXTO_ÍNDICE = Attribute.TEXT_COLOR(150, 150, 150); // Gris para índices

	/** Color texto para mostrar turno jugador con blancas. */
	public static final Attribute TEXTO_JUGADOR_BLANCAS = Attribute.TEXT_COLOR(255, 0, 0); // Rojo
	
	/** Color texto para mostrar turno jugador con negras. */
	public static final Attribute TEXTO_JUGADOR_NEGRAS = Attribute.TEXT_COLOR(0, 150, 255); // Azul cielo intenso
}
