package pentago.modelo;

/**
 * 
 * Pieza.
 * 
 * Dado que no tiene dependencias de otros módulos salvo de Color, se debería
 * implementar en las segundas etapas tras implementar el paquete juego.util y
 * el tipo enumerado {@link pentago.modelo.Color Color}.
 * 
 * @param color color de la pieza
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * 
 * @version 1.0
 * @see pentago.modelo.Pieza
 */
public record Pieza(Color color) {
}