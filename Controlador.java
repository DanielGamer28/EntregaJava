package pentago.control;

import pentago.modelo.Tablero;
import pentago.modelo.Jugador;

import pentago.modelo.Color;
import pentago.modelo.Pieza;

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
		//Para que sea legal, tiene que estar en tablero y tiene que estar vacia la celda donde se va a colocar
		//Nos aprovechamos de las funciones que ya tenemos creadas en otras clases (como se pide, obviamente solo q yo soy imbecil)
		
		if(this.tablero.estaEnTablero(coordenada) && this.tablero.consultarCelda(coordenada).estaVacia()) {
			return true;
		}else {
			return false;
		}
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
	
	
	
	public Jugador obtenerGanador() {
		
		int n = 0;
		boolean existeGanador = false;
		
		for(int i = 0; i < tablero.consultarNumeroFilas(); i++) {
			for(int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
				if(!tablero.consultarCelda(new Coordenada(i,j)).estaVacia()) {	//Si no esta vacia, miramos ahora si tiene el color q queremos
					if(tablero.consultarCelda(new Coordenada(i,j)).consultarPieza().color() == jugador[0].consultarColor()) {
						int seguidas1 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.DIAGONAL_NO_SE);
						if(seguidas1 == configuracion.numeroPiezasConsecutivas()) {
							n = 0;
						}
						int seguidas2 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.DIAGONAL_SO_NE);
						if(seguidas2 == configuracion.numeroPiezasConsecutivas()) {
							n = 0;
						}
						int seguidas3 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.HORIZONTAL);
						if(seguidas3 == configuracion.numeroPiezasConsecutivas()) {
							n = 0;
						}
						int seguidas4 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.VERTICAL);
						if(seguidas4 == configuracion.numeroPiezasConsecutivas()) {
							n = 0;
						}	
					}
				}else {
					/*Se puede poner lo siguiente, pero es "redundante" porque pasa al otro jugador por huevos
					 * if(tablero.consultarCelda(new Coordenada(i,j)).consultarPieza().color() == jugador[1].consultarColor()) {
					 */
					int seguidas1 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.DIAGONAL_NO_SE);
					if(seguidas1 == configuracion.numeroPiezasConsecutivas()) {
						n++;
					}
					int seguidas2 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.DIAGONAL_SO_NE);
					if(seguidas2 == configuracion.numeroPiezasConsecutivas()) {
						n++;
					}
					int seguidas3 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.HORIZONTAL);
					if(seguidas3 == configuracion.numeroPiezasConsecutivas()) {
						n++;
					}
					int seguidas4 = tablero.contarPiezasConsecutivas(new Coordenada(i,j), Direccion.VERTICAL);
					if(seguidas4 == configuracion.numeroPiezasConsecutivas()) {
						n++;						
					}
				}
			}
		}
		
		if(existeGanador) {
			return jugador[n];
		}else {
			return null;
		}
	}
	
	
	/**
	 * Comprueba si se cumplen las condiciones para que el juego se de por finalizado
	 * 
	 * @return true si cumple, false si no
	 */
	public boolean estaAcabado() {
		if(this.tablero.estaCompleto()) { 
			//La intencion es comprobar si jug1 y jug2 tienen minimo n fichas de cada color seguidas
			
			//this.tablero.consultarNumeroPiezas(jugador[0].consultarColor()) == tablero.) {
			
			
			
			return false;
		}
		return true;
	}
	
	
}