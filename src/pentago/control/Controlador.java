package pentago.control;

import pentago.modelo.Celda;
import pentago.modelo.Color;
import pentago.modelo.Jugador;
import pentago.modelo.Region;
import pentago.modelo.Tablero;
import pentago.util.Coordenada;
import pentago.util.Direccion;

/**
 * Gestiona el desarrollo de la partida de Pentago, coordinando el tablero,
 * la configuración, los jugadores y los turnos.
 * 
 * @author Daniel Acitores Martinez 
 * @author Claudia Berezo Berzosa
 * 
 */
public class Controlador {

	/** Tablero de la partida. */
	private Tablero tablero;

	/** Índice del jugador que tiene el turno actual (0 o 1). */
	private int turno;

	/** Configuración de la partida. */
	private Configuracion configuracion;

	/** Array con los dos jugadores de la partida. */
	private Jugador[] jugador;

	/**
	 * Inicializa el controlador con un tablero y su configuración correspondiente.
	 * 
	 * @param tablero tablero de juego
	 * @param configuracion configuración de la partida
	 */
	public Controlador(Tablero tablero, Configuracion configuracion) {
		this.tablero = tablero;
		this.configuracion = configuracion;
		this.turno = 0;
		this.jugador = new Jugador[2];
	}

	/**
	 * Consulta la configuración actual de la partida.
	 * 
	 * @return configuración actual
	 */
	public Configuracion consultarConfiguracion() {
		return this.configuracion;
	}

	/**
	 * Consulta una copia en profundidad del tablero actual.
	 * 
	 * @return clon del tablero actual
	 */
	public Tablero consultarTablero() {
		return this.tablero.clonar();
	}

	/**
	 * Registra a un jugador con su nombre y su color correspondiente
	 * (blanco para el primer jugador, negro para el segundo).
	 * 
	 * @param nombre nombre del jugador a registrar
	 */
	public void registrarJugador(String nombre) {
		if (this.jugador[0] == null) {
			this.jugador[0] = new Jugador(nombre, Color.BLANCO);
		} else if (this.jugador[1] == null) {
			this.jugador[1] = new Jugador(nombre, Color.NEGRO);
		}
	}

	/**
	 * Obtiene el jugador que tiene el turno actual.
	 * 
	 * @return jugador con el turno
	 */
	public Jugador obtenerTurno() {
		Jugador jugadorActual = null;
		if (this.turno == 0) {
			jugadorActual = this.jugador[0];
		} else {
			jugadorActual = this.jugador[1];
		}
		return jugadorActual;
	}

	/**
	 * Cambia el turno del jugador actual al otro jugador.
	 */
	public void cambiarTurno() {
		if (this.turno == 1) {
			this.turno = 0;
		} else {
			this.turno = 1;
		}
	}

	/**
	 * Comprueba si es legal colocar una pieza en la coordenada dada.
	 * 
	 * @param coordenada coordenada donde se pretende colocar la pieza
	 * @return true si es legal colocar, false en caso contrario
	 */
	public boolean esLegalColocar(Coordenada coordenada) {
		boolean esLegal = false;
		if (!this.estaAcabado() && this.tablero.estaEnTablero(coordenada)
				&& this.tablero.consultarCelda(coordenada).estaVacia()) {
			esLegal = true;
		}
		return esLegal;
	}

	/**
	 * Coloca una pieza del jugador con el turno actual en la coordenada indicada.
	 * 
	 * @param coordenada coordenada donde colocar la pieza
	 */
	public void colocarPieza(Coordenada coordenada) {
		this.tablero.colocar(this.jugador[this.turno].generarPieza(), coordenada);
	}

	/**
	 * Obtiene la instancia del tablero contenida en el controlador.
	 * 
	 * @return referencia al tablero actual
	 */
	Tablero obtenerTablero() {
		return this.tablero;
	}

	/**
	 * Comprueba si el color dado ya tiene una línea ganadora en el tablero.
	 *
	 * @param color color a comprobar
	 * @return true si ese color alcanza el mínimo de piezas consecutivas
	 */
	private boolean tienePiezasGanadoras(Color color) {
		boolean encontrado = false;

		for (int i = 0; i < this.tablero.consultarNumeroFilas(); i++) {
			for (int j = 0; j < this.tablero.consultarNumeroColumnas(); j++) {
				Coordenada coordenada = new Coordenada(i, j);
				Celda celda = this.tablero.consultarCelda(coordenada);

				if (!celda.estaVacia() && celda.consultarPieza().color() == color) {
					for (Direccion direccion : Direccion.values()) {
						int consecutivas = this.tablero.contarPiezasConsecutivas(coordenada, direccion);
						if (consecutivas >= this.configuracion.numeroPiezasConsecutivas()) {
							encontrado = true;
						}
					}
				}
			}
		}
		return encontrado;
	}

	/**
	 * Obtiene el jugador que ha ganado la partida, si lo hay.
	 *
	 * @return jugador ganador, o null si no hay ganador o hay empate
	 */
	public Jugador obtenerGanador() {
		boolean blancoGana = this.tienePiezasGanadoras(Color.BLANCO);
		boolean negroGana = this.tienePiezasGanadoras(Color.NEGRO);

		Jugador ganador = null;
		if (blancoGana && !negroGana) {
			ganador = this.jugador[0];
		} else if (negroGana && !blancoGana) {
			ganador = this.jugador[1];
		}
		return ganador;
	}

	/**
	 * Comprueba si se cumplen las condiciones de finalización de la partida.
	 * 
	 * @return true si la partida ha finalizado, false en caso contrario
	 */
	public boolean estaAcabado() {
		boolean acabado = false;

		if (this.tablero.estaCompleto()) {
			acabado = true;
		} else if (this.tienePiezasGanadoras(Color.BLANCO) || this.tienePiezasGanadoras(Color.NEGRO)) {
			acabado = true;
		}

		return acabado;
	}

	/**
	 * Rota el cuadrante indicado 90 grados en sentido horario.
	 * 
	 * @param cuadrante cuadrante del tablero a rotar
	 */
	public void rotarRegionSentidoHorario(Cuadrante cuadrante) {
		Coordenada esquina = this.configuracion.consultarCoordenadaReferencia(cuadrante);
		int tam = this.configuracion.consultarTamañoCuadrante();
		Region regionOriginal = this.tablero.consultarRegion(esquina, tam, tam);
		Region regionRotada = regionOriginal.rotarSentidoHorario();
		this.tablero.restaurarPiezas(esquina, regionRotada);
	}

	/**
	 * Rota el cuadrante indicado 90 grados en sentido antihorario.
	 * 
	 * @param cuadrante cuadrante del tablero a rotar
	 */
	public void rotarRegionSentidoAntihorario(Cuadrante cuadrante) {
		Coordenada esquina = this.configuracion.consultarCoordenadaReferencia(cuadrante);
		int tam = this.configuracion.consultarTamañoCuadrante();
		Region regionOriginal = this.tablero.consultarRegion(esquina, tam, tam);
		Region regionRotada = regionOriginal.rotarSentidoAntihorario();
		this.tablero.restaurarPiezas(esquina, regionRotada);
	}

}
