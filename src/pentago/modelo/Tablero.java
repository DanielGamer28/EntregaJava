package pentago.modelo;

import java.util.Arrays;
import java.util.Objects;

import pentago.util.Coordenada;
import pentago.util.Direccion;

/*
 * Autores: Daniel Acitores Martinez y Claudia Berezo Berzosa
 */

public class Tablero{
	
	private int filas;
	private int columnas;
	private Celda celda[][];//Dimensiones del tablero?
	
	//Constructor
	public Tablero(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		
		if(filas < 1 || columnas < 1) {
			this.filas = filas;
			this.columnas = columnas;
		}	
		
		this.celda = new Celda[this.filas][this.columnas];
		for(int i = 0; i < this.filas; i++) {
			for(int j = 0; j < this.columnas; j++) {
				celda[i][j] = new Celda(new Coordenada(i,j));
			}
		}
					//Guardamos memoria para que se permitan X-Y celdas
					//Ahora vamos a cada hueco, y creamos la celda VACIA
		
	}


	/**
	 * Devuelve una represantacion actual del tablero
	 * 
	 * @return texto
	 */
	public String aTexto() {
		
		String texto = "";
			
		for(int i = 0; i < consultarNumeroFilas(); i++) {
			texto = texto + "\n";
			for(int j = 0; j < consultarNumeroColumnas(); j++) {
				if(this.celda[i][j].estaVacia()) {
					texto = texto + "-";
				}else {
					char c = this.celda[i][j].consultarPieza().color().aCaracter();
					texto = texto + c;
				}
			}
		}
		return texto;
	}


	/**
	 * Coloca la pieza en la posicion indicada si es posible
	 * 
	 * @param pieza
	 * @param coordenada
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if(estaEnTablero(coordenada)) {
			this.celda[coordenada.fila()][coordenada.columna()].establecerPieza(pieza);
		}
	}


	/**
	 * Comprueba los contenidos de la celda en esas coords
	 * 
	 * @param coordenada
	 * @return celda
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		
		Celda celdaAux = new Celda(coordenada);
		if(!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
			celdaAux.establecerPieza(this.celda[coordenada.fila()][coordenada.columna()].consultarPieza());
		}
		
		return celdaAux;
	}


	/**
	 * Devuelve el numero de columnas del tablero
	 * 
	 * @return columnas
	 */
	public int consultarNumeroColumnas() {
		return this.columnas;
	}


	/**
	 * Devuelve el numero de filas del tablero
	 * 
	 * @return filas
	 */
	public int consultarNumeroFilas() {
		return this.filas;
	}
	
	
	/**
	 * Cuenta la cantidad de piezas del color indicado
	 * 
	 * @param color
	 * @return nº de piezas de ese color
	 */
	public int consultarNumeroPiezas(Color color) {
		
		int total = 0;
		
		for(int i = 0; i < consultarNumeroFilas(); i++) {
			for(int j = 0; j < consultarNumeroColumnas(); j++) {
				if(!this.celda[i][j].estaVacia()) { //primero miramos a ver si hay alguna alguna pieza en la celda
					if(this.celda[i][j].consultarPieza().color().equals(color)) { //se supone que si son iguales, sumaremos 1 al contador de las piezas de ese color
						total++;
					}
				}
			}
		}
		
		return total;
	}


	/**
	 * Devuelve la region indicada con los parametros, y en ella todos los contenidos de las celdas
	 * 
	 * @param esquinaSuperiorIzquierda
	 * @param tamañoFilas
	 * @param tamañoColumnas
	 * @return region acotada y sus contenidos
	 */
	public Region consultarRegion(Coordenada esquinaSuperiorIzquierda, int tamañoFilas, int tamañoColumnas) {
		
		Celda[][] celdaAux  = new Celda[tamañoFilas][tamañoColumnas];
		
		for(int i = 0; i < tamañoFilas; i++) {
			for(int j = 0; j < tamañoColumnas; j++) {
				int fila = i+esquinaSuperiorIzquierda.fila();
				int columna = j+esquinaSuperiorIzquierda.columna();
				celdaAux[i][j] = new Celda(new Coordenada(fila, columna));
				if(!this.celda[fila][columna].estaVacia()) {
					celdaAux[i][j].establecerPieza(this.celda[fila][columna].consultarPieza());
				}
			}
		}
		
		Region regionAux = new Region(celdaAux); //De esta forma creamos la region con la celda ya creada
		
		return regionAux;
	}


	/**
	 * Comprueba la cantidad de piezas iguales a la indicada en dicha direccion
	 * 
	 * @param coordenada
	 * @param dir
	 * @return total de piezas iguales en la direccion
	 */
	public int contarPiezasConsecutivas(Coordenada coordenada, Direccion dir) {
		
		int total = 0;
		if(!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
			total++; //Ahora si, sabemos si en la celda en la que entramos tiene ficha o no
		}
		
		int n1 = 1; //Va a servir para poder desplazarnos por el tablero tras cada bucle
		int n2 = 1;
		char color = this.celda[coordenada.fila()][coordenada.columna()].consultarPieza().color().aCaracter();
		
		int x1 = (coordenada.fila()+(dir.consultarDesplazamientoEnFilas()*n1));
		int y1 = (coordenada.columna()+(dir.consultarDesplazamientoEnColumnas()*n1));
		
		int x2 = (coordenada.fila()-(dir.consultarDesplazamientoEnFilas()*n2));
		int y2 = (coordenada.columna()-(dir.consultarDesplazamientoEnColumnas()*n2));
		
		while (estaEnTablero(new Coordenada(x1,y1))) {
			if(!this.celda[x1][y1].estaVacia() && (this.celda[x1][y1].consultarPieza().color().aCaracter() == color)) {
				total++;	
			}
			n1++;
			x1 = (coordenada.fila()+(dir.consultarDesplazamientoEnFilas()*n1));
			y1 = (coordenada.columna()+(dir.consultarDesplazamientoEnColumnas()*n1));
		}
	
		while (estaEnTablero(new Coordenada(x2,y2))) {
			if(!this.celda[x2][y2].estaVacia() && (this.celda[x2][y2].consultarPieza().color().aCaracter() == color)) {
				total++;	
			}
			n2++;
			x2 = (coordenada.fila()-(dir.consultarDesplazamientoEnFilas()*n2));
			y2 = (coordenada.columna()-(dir.consultarDesplazamientoEnColumnas()*n2));
			
		}		
		//Y solo saldra cuando vea que las 2 posiciones siguientes NO ESTAN EN TABLERO	
		return total;
	}


	/**
	 * Devuelve la "legalidad" de la posicion indicada
	 * 
	 * @param coordenada
	 * @return true si cumple, false si no
	 */
	public boolean estaEnTablero(Coordenada coordenada) {
		if(coordenada.fila() >= 0 && coordenada.columna() >= 0) {
			if(coordenada.fila() < consultarNumeroFilas() && coordenada.columna() < consultarNumeroColumnas() ){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Devuelve si esta el tablero lleno
	 * 
	 * @return true si cumple, false si no
	 */
	public boolean estaCompleto() {
		
		int total = consultarNumeroColumnas() * consultarNumeroFilas();
		if(total == (consultarNumeroPiezas(Color.NEGRO)) + (consultarNumeroPiezas(Color.BLANCO))){
			return true;
		}
		
		return false;
	}


	/**
	 * Funcion que nos da la celda en las coordenadas dadas, si no tiene nada devuelve "null"
	 * 
	 * @param coordenada
	 * @return celda con sus contenidos
	 */
	//Necesita ser amigable (para eso necesitamos que no tenga el "public" o "private" antes)
	Celda obtenerCelda(Coordenada coordenada) {
		if(!estaEnTablero(coordenada)) {
			return null;
		}else {
			return this.celda[coordenada.fila()][coordenada.columna()];
		}
	}
	
	/**
	 * Sobreescribe en el tablero actual las celdas de la region dada en las coordenadas
	 * 
	 * @param esquinaSuperiorIzquierda
	 * @param region
	 */
	public void restaurarPiezas(Coordenada esquinaSuperiorIzquierda, Region region){
		if(estaEnTablero(esquinaSuperiorIzquierda)) {
			for(int i = 0; i < region.consultarNumeroFilas(); i++) {
				for(int j = 0; j < region.consultarNumeroColumnas(); j++) {
					//Tenemos que calcular la posicion en la que nos vamos a posicionar ahora
					
					int fila = i+esquinaSuperiorIzquierda.fila();
					int columna = j+esquinaSuperiorIzquierda.columna();
					Coordenada coordenadaReal = new Coordenada(fila, columna);
					
					//Y simplemente sacamos la pieza de la celda en la coordenada de los bucles, y la ponemos en las coordenadas de la region 
					
					colocar(region.consultarCelda(new Coordenada(i,j)).consultarPieza(), coordenadaReal);
				}
			}
		}
		
	}


	/**
	 * Funcion que devuelve una copia del tablero actual con sus celdas
	 * 
	 * @return tablero clonado
	 */
	public Tablero clonar() {
		Tablero tableroAux = new Tablero(this.consultarNumeroFilas(), this.consultarNumeroColumnas());
		for(int i = 0; i < consultarNumeroFilas(); i++) {
			for(int j = 0; j < consultarNumeroColumnas(); j++) {
				if(!this.celda[i][j].estaVacia()) {
					tableroAux.celda[i][j].establecerPieza(this.celda[i][j].consultarPieza());
				}
			}
		}
		
		return tableroAux;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(celda);
		result = prime * result + Objects.hash(columnas, filas);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return Arrays.deepEquals(celda, other.celda) && columnas == other.columnas && filas == other.filas;
	}


	@Override
	public String toString() {
		return "Tablero [filas=" + filas + ", columnas=" + columnas + ", celda=" + Arrays.toString(celda) + "]";
	}
}