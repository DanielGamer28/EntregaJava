package pentago.textuicolor;

import java.util.Optional;
import java.util.Scanner;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import pentago.control.Controlador;
import pentago.control.Cuadrante;
import pentago.modelo.Celda;
import pentago.modelo.Color;
import pentago.modelo.Jugador;
import pentago.modelo.Tablero;
import pentago.util.Coordenada;

/**
 * Gestiona las operaciones de entrada y salida de la interfaz de texto con
 * colores del juego Pentago.
 * 
 * Encapsula la lectura desde teclado, la escritura en consola, el uso de
 * colores ANSI y la representación gráfica del tablero.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0
 */
public class EntradaSalidaColor {

	/** Linux. */
	private static final String LINUX = "linux";

	/** Mac. */
	private static final String MAC = "mac";

	/** Windows 11. */
	private static final String WINDOWS_11 = "windows 11";

	/** Texto Eclipse. */
	private static final String ECLIPSE = "eclipse";

	/** Texto celda vacía. */
	private static final String TEXTO_CELDA_VACIA = "   ";
	
	/** Entrada de teclado. */
	private Scanner teclado;

	/**
	 * Constructor.
	 */
	public EntradaSalidaColor() {
		teclado = new Scanner(System.in);
	}

	/**
	 * Lee el nombre del primer jugador.
	 * 
	 * @return nombre introducido
	 */
	public String leerNombrePrimerJugador() {
		System.out.print("Introduce el nombre del primer jugador: ");
		return teclado.nextLine();
	}

	/**
	 * Lee el nombre del segundo jugador.
	 * 
	 * @return nombre introducido
	 */
	public String leerNombreSegundoJugador() {
		System.out.print("Introduce el nombre del segundo jugador: ");
		return teclado.nextLine();
	}

	/**
	 * Lee la fila en la que se desea colocar la pieza.
	 * 
	 * @return fila
	 */
	public int leerFila() {
		return leerEntero("Introduce fila:");
	}

	/**
	 * Lee la columna en la que se desea colocar la pieza.
	 * 
	 * @return columna
	 */
	public int leerColumna() {
		return leerEntero("Introduce columna:");
	}

	/**
	 * Lee el cuadrante que se desea rotar.
	 * 
	 * @return número de cuadrante
	 */
	public int leerCuadrante() {
		return leerEntero("Introduce cuadrante (0-3):");
	}

	/**
	 * Lee el sentido en el que se desea realizar la rotación.
	 * 
	 * @return 0 para sentido antihorario y 1 para sentido horario
	 */
	public int leerSentidoRotacion() {
		return leerEntero("Introduce sentido del giro (0: antihorario, 1: horario):");
	}

	/**
	 * Lee un valor numérico por teclado.
	 * 
	 * @param mensaje texto a mostrar
	 * @return valor numérico
	 */
	private int leerEntero(String mensaje) {
		System.out.print(mensaje);
		return teclado.nextInt();
	}

	/**
	 * Muestra el mensaje correspondiente a una colocación inválida.
	 */
	public void mostrarMovimientoInvalido() {
		System.err.print("¡Movimiento inválido! " + "Celda ya ocupada o movimiento fuera de límites. "
				+ "Inténtalo de nuevo.\n");
	}

	/**
	 * Muestra el mensaje correspondiente a una rotación inválida.
	 */
	public void mostrarRotacionInvalida() {
		System.err.print("¡Rotación inválida! " + "Cuadrante inválido o sentido de rotación incorrecto. "
				+ "Inténtalo de nuevo.\n");
	}

	/**
	 * Muestra el turno actual con el color asociado.
	 * 
	 * @param controlador controlador del juego
	 */
	public void mostrarTurnoActual(Controlador controlador) {
		Jugador turno = controlador.obtenerTurno();
		if (supportsAnsi()) {
			System.out.println(Ansi.colorize("Turno de " + turno.consultarNombre(),
					seleccionarColorTexto(turno.consultarColor())));
		} else {
			System.out.println("Turno de " + turno.consultarNombre());
		}
	}

	/**
	 * Muestra el resultado final de la partida.
	 * 
	 * Puede ser victoria de un jugador o empate.
	 * 
	 * @param controlador controlador del juego
	 */
	public void mostrarResultadoFinal(Controlador controlador) {
		if (controlador.estaAcabado() && controlador.obtenerGanador() != null) {
			if (supportsAnsi()) {
				System.out.println(Ansi.colorize("¡" + controlador.obtenerGanador().consultarNombre() + " ha ganado!",
						seleccionarColorTexto(controlador.obtenerGanador().consultarColor())));
			} else {
				System.out.println("¡" + controlador.obtenerGanador().consultarNombre() + " ha ganado!");
			}
		} else if (controlador.estaAcabado() && controlador.obtenerGanador() == null) {
			System.out.println("¡Empate!");
		} else {
			System.out.println("Partida en curso (no ha finalizado todavía)...");
		}
	}

	/**
	 * Selecciona el color de texto asociado al color de un jugador.
	 * 
	 * @param color color del jugador
	 * @return atributo de color correspondiente
	 */
	private Attribute seleccionarColorTexto(Color color) {
		return color == Color.BLANCO ? ConfiguracionColor.TEXTO_JUGADOR_BLANCAS
				: ConfiguracionColor.TEXTO_JUGADOR_NEGRAS;
	}

	/**
	 * Muestra el tablero en pantalla con colores.
	 * 
	 * @param controlador controlador del juego
	 */
	public void mostrarTablero(Controlador controlador) {
		Tablero tablero = controlador.consultarTablero();
		imprimirCabecera(tablero.consultarNumeroColumnas());
		for (int fila = 0; fila < tablero.consultarNumeroFilas(); fila++) {
			imprimirFila(controlador, tablero, fila);
			imprimirSeparadoresFila(fila, tablero.consultarNumeroFilas(), tablero.consultarNumeroColumnas());
		}
	}

	/**
	 * Imprime una fila del tablero.
	 * 
	 * @param controlador controlador del juego
	 * @param tablero     tablero
	 * @param fila        fila
	 */
	private void imprimirFila(Controlador controlador, Tablero tablero, int fila) {
		// Índice lateral
		if (supportsAnsi()) {
			System.out.print(Ansi.colorize(fila + " ", ConfiguracionColor.TEXTO_ÍNDICE));
		} else {
			System.out.print(fila + " ");
		}

		for (int columna = 0; columna < tablero.consultarNumeroColumnas(); columna++) {
			Celda celda = tablero.consultarCelda(new Coordenada(fila, columna));
			System.out.print("│");
			imprimirContenidoCelda(controlador, celda);
		}

		System.out.println("│");
	}

	/**
	 * Imprime el contenido de una celda.
	 * 
	 * Puede estar vacía o contener una pieza.
	 * 
	 * @param controlador controlador del juego
	 * @param celda       celda
	 */
	private void imprimirContenidoCelda(Controlador controlador, Celda celda) {
		if (celda.estaVacia()) {
			imprimirCeldaVacia(controlador, celda);
		} else if (celda.consultarPieza().color() == Color.BLANCO) {
			imprimirPiezaBlanca(celda);
		} else {
			imprimirPiezaNegra(celda);
		}
	}

	/**
	 * Imprime una celda vacía.
	 * 
	 * @param controlador controlador
	 * @param celda       celda
	 */
	private void imprimirCeldaVacia(Controlador controlador, Celda celda) {
		if (supportsAnsi()) {
			Coordenada coordenada = celda.consultarCoordenada();
			Cuadrante cuadrante = controlador.consultarConfiguracion().consultarCuadrante(coordenada);
			switch (cuadrante) {
			case SUPERIOR_IZQUIERDO, INFERIOR_DERECHO -> System.out.print(
					Ansi.colorize(TEXTO_CELDA_VACIA, ConfiguracionColor.TEXTO_VACÍO, ConfiguracionColor.FONDO_VACÍO_CUADRANTE_A));
			default -> System.out.print(
					Ansi.colorize(TEXTO_CELDA_VACIA, ConfiguracionColor.TEXTO_VACÍO, ConfiguracionColor.FONDO_VACÍO_CUADRANTE_B));
			}
		} else {
			System.out.print(TEXTO_CELDA_VACIA);
		}
	}

	/**
	 * Imprime una pieza blanca.
	 * 
	 * @param celda celda
	 */
	private void imprimirPiezaBlanca(Celda celda) {
		if (supportsAnsi()) {
			System.out.print(Ansi.colorize(" " + celda.consultarPieza().color().aCaracter() + " ",
					ConfiguracionColor.TEXTO_PIEZA, ConfiguracionColor.FONDO_JUGADOR_BLANCAS));
		} else {
			System.out.print(" " + celda.consultarPieza().color().aCaracter() + " ");
		}
	}

	/**
	 * Imprime una pieza negra.
	 * 
	 * @param celda celda
	 */
	private void imprimirPiezaNegra(Celda celda) {
		if (supportsAnsi()) {
			System.out.print(Ansi.colorize(" " + celda.consultarPieza().color().aCaracter() + " ",
					ConfiguracionColor.TEXTO_PIEZA, ConfiguracionColor.FONDO_JUGADOR_NEGRAS));
		} else {
			System.out.print(" " + celda.consultarPieza().color().aCaracter() + " ");
		}
	}

	/**
	 * Imprime líneas de separación entre filas.
	 * 
	 * @param fila        número de fila actual
	 * @param numFilas    número total de filas
	 * @param numColumnas número total de columnas
	 */
	private void imprimirSeparadoresFila(int fila, int numFilas, int numColumnas) {
		String linea = "  ";
		if (fila < numFilas - 1) {
			linea += construirLinea("├", "┼", "┤", numColumnas);
		} else {
			linea += construirLinea("└", "┴", "┘", numColumnas);
		}
		System.out.println(linea);
	}

	/**
	 * Construye una línea horizontal del tablero.
	 * 
	 * @param inicio      inicio
	 * @param separador   separador entre celdas
	 * @param fin         final
	 * @param numColumnas número de columnas
	 * @return línea construida
	 */
	private String construirLinea(String inicio, String separador, String fin, int numColumnas) {
		String linea = inicio;
		for (int columna = 0; columna < numColumnas; columna++) {
			linea += "───";
			if (columna < numColumnas - 1) {
				linea += separador;
			}
		}
		linea += fin;
		return linea;
	}

	/**
	 * Imprime la cabecera del tablero.
	 * 
	 * @param numColumnas número de columnas
	 */
	private void imprimirCabecera(int numColumnas) {
		System.out.println();
		System.out.print(TEXTO_CELDA_VACIA);

		for (int columna = 0; columna < numColumnas; columna++) {
			if (supportsAnsi()) {
				System.out.print(Ansi.colorize(" " + columna + "  ", ConfiguracionColor.TEXTO_ÍNDICE));
			} else {
				System.out.print(" " + columna + "  ");
			}
		}

		System.out.println();
		String linea = "  ┌";

		for (int columna = 0; columna < numColumnas; columna++) {
			linea += "───";
			if (columna < numColumnas - 1) {
				linea += "┬";
			}
		}

		linea += "┐";
		System.out.println(linea);
	}

	/**
	 * Detecta si se está ejecutando desde Eclipse.
	 * 
	 * @return true si se está ejecutando desde Eclipse
	 */
	private boolean estaEjecutandoseDesdeEclipse() {
		ProcessHandle handle = ProcessHandle.current();

		boolean continuar = true;
		while (continuar) {
			Optional<ProcessHandle> parent = handle.parent();
			if (parent.isEmpty()) {
				continuar = false;
			} else {
				handle = parent.get();
				Optional<String> comando = handle.info().command();
				if (comando.isPresent()) {
					String minusculas = comando.get().toLowerCase();
					if (minusculas.contains(ECLIPSE)) {
						return true;
					}
				}
			}
		}
		return continuar;
	}

	/**
	 * Detecta si el entorno de ejecución soporta ANSI.
	 * 
	 * @return true si soporta ANSI, false en caso contrario
	 */
	private boolean supportsAnsi() {
		String sistemaOperativo = System.getProperty("os.name").toLowerCase();
		return System.getenv("WT_SESSION") != null || System.getenv("ANSICON") != null
				|| System.getenv("ConEmuANSI") != null || System.getenv("TERM_PROGRAM") != null
				|| (System.getenv("TERM") != null && !"dumb".equals(System.getenv("TERM")))
				|| estaEjecutandoseDesdeEclipse() || sistemaOperativo.contains(WINDOWS_11)
				|| sistemaOperativo.contains(LINUX) || sistemaOperativo.contains(MAC);
	}

	/**
	 * Cierra el recurso utilizado para la entrada desde teclado.
	 */
	public void cerrar() {
		teclado.close();
	}
}