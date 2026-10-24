package pentago.control;

import pentago.modelo.Tablero;
import pentago.modelo.Jugador;
import pentago.modelo.Region;
import pentago.modelo.Color;
import pentago.modelo.Pieza;
import pentago.modelo.Celda;

import pentago.util.Coordenada;
import pentago.util.Direccion;

public class Controlador{
	
	private Tablero tablero;
	private int turno;
	private Configuracion configuracion;
	
	private Jugador jugador[];
	
	//Constructor
	public Controlador(Tablero tablero, Configuracion configuracion) {
		this.tablero = tablero;
		this.configuracion = configuracion;	
		this.turno = 0; //Arrancan las fichas blancas con jugador[0]
		
		this.jugador = new Jugador[2]; //De esta forma, se instancian 2 jugadores (creo q tamb se puede con jug1 y jug2, pero soy gilipollas y ns)
	}
	
	/**
	 * Funcion para saber la config actual
	 * 
	 * @return configuracion
	 */
	public Configuracion consultarConfiguracion() {
		return this.configuracion;
	}
	
	/**
	 * Funcion de consulta sobre el tablero actual
	 * 
	 * @return tablero
	 */
	public Tablero consultarTablero() {
		return this.tablero.clonar();
	}
	
	/**
	 * Inicializamos al jugador con su nombre y color correspondiente (blanco para 1, negro para 2)
	 * 
	 * @param nombre
	 */
	public void registrarJugador(String nombre) {
		if(this.jugador[0] == null) {	//como solo hemos guardado el espacio en memoria para 2 jugadores, ahora les instanciamos	
			jugador[0] = new Jugador(nombre, Color.BLANCO );
		}else {
			jugador[1] = new Jugador(nombre, Color.NEGRO );
		}
	}
	
	
	/**
	 * Funcion que devueve al jugador actual con el turno
	 * 
	 * @return jugador con turno
	 */
	public Jugador obtenerTurno() {
		if(turno == 0) {
			return jugador[0];
		}else {
			return jugador[1];
		}
		/*
		 * return jugador[turno]; hace lo mismo, pero soy cortito, si lo hago de la otra forma lo veo mejor
		 */
	}
	
	/**
	 * Cambia el turno del jugador actual al otro jugador
	 */
	public void cambiarTurno() {
		if(turno == 1) {
			turno = 0;
		}else {
			turno = 1;
		}
	}
	
	/**
	 *Comprobacion para saber si se puede colocar una pieza en las coordenadas dadas 
	 * 
	 * @param coordenada
	 * @return true si cumple, false si no
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
	 * Funcion que colocara la pieza si es posible correspondiente al jugador con turno, si no, no hara nada
	 * 
	 * @param coordenada
	 */
	public void colocarPieza(Coordenada coordenada) {
		//De esta forma, colocamos una pieza del color correspondiente al jugador en su turno, en la coordenada dicha
		//Ni puta idea como se me ha ocurrido con mi cabeza de alcornoque
		
		this.tablero.colocar(new Pieza(jugador[turno].consultarColor()), coordenada);
		//tablero.colocar(jugador[turno].generarPieza(), coordenada);
		//Tambien seria otra forma mejor (para q coño habre dicho nd antes, soy imbecil)
	}
	
	
	/**
	 * Retorna la instancia del tablero contenida en el controlador
	 * 
	 * @return tablero actual
	 */
	Tablero obtenerTablero() {
		return this.tablero;
	}
	
	
	/**
	 * Comprueba si el color dado ya tiene una línea ganadora en el tablero.
	 *
	 * @param color color a comprobar
	 * @return true si ese color alcanza el mínimo de piezas consecutivas en algún punto
	 */
	private boolean tienePiezasGanadoras(Color color) {
	    boolean encontrado = false;

	    for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
	        for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
	            Coordenada coordenada = new Coordenada(i, j);
	            Celda celda = tablero.consultarCelda(coordenada);

	            if (!celda.estaVacia() && celda.consultarPieza().color() == color) {
	                for (Direccion direccion : Direccion.values()) {
	                    int consecutivas = tablero.contarPiezasConsecutivas(coordenada, direccion);
	                    encontrado = encontrado || consecutivas >= configuracion.numeroPiezasConsecutivas();
	                }
	            }
	        }
	    }
	    return encontrado;
	}
	
	
	/**
	 * Obtiene el jugador que ha ganado la partida, si lo hay.
	 *
	 * @return jugador ganador, o null si no hay
	 */
	public Jugador obtenerGanador() {
	    boolean blancoGana = tienePiezasGanadoras(Color.BLANCO);
	    boolean negroGana = tienePiezasGanadoras(Color.NEGRO);

	    Jugador ganador = null;
	    if (blancoGana && !negroGana) {
	        ganador = jugador[0];
	    } else if (negroGana && !blancoGana) {
	        ganador = jugador[1];
	    }
	    return ganador;
	}
	
	
	/**
	 * Comprueba si se cumplen las condiciones para que el juego se de por finalizado
	 * 
	 * @return true si cumple, false si no
	 */
	public boolean estaAcabado() {
	boolean acabado = false;
	
	if (this.tablero.estaCompleto()) {
		acabado = true;
	} else if (alcanzaVictoria(Color.BLANCO) || alcanzaVictoria(Color.NEGRO)) {
		acabado = true;
	}
	
	return acabado;
	}
	
	
	public void rotarRegionSentidoHorario(Cuadrante cuadrante) {
	Coordenada esquina = this.configuracion.consultarCoordenadaReferencia(cuadrante);
	int tam = this.configuracion.consultarTamañoCuadrante();
	Region regionOriginal = this.tablero.consultarRegion(esquina, tam, tam);
	Region regionRotada = regionOriginal.rotarSentidoHorario();
	this.tablero.restaurarPiezas(esquina, regionRotada);
	}

	public void rotarRegionSentidoAntihorario(Cuadrante cuadrante) {
	Coordenada esquina = this.configuracion.consultarCoordenadaReferencia(cuadrante);
	int tam = this.configuracion.consultarTamañoCuadrante();
	Region regionOriginal = this.tablero.consultarRegion(esquina, tam, tam);
	Region regionRotada = regionOriginal.rotarSentidoAntihorario();
	this.tablero.restaurarPiezas(esquina, regionRotada);
	}
	
}
