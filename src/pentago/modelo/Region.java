package pentago.modelo;
import java.util.Arrays;
import java.util.Objects;

import pentago.util.Coordenada;

public class Region{
	
	//Variables que tendra region
	private int filas;
	
	private int columnas;
	
	private Celda[][] celda;
	
	//constructor
	
	//Se hace esa comprobacion para meter por predefinido un 
	public Region(int filas, int columnas) {		
		this.filas = filas;
		this.columnas = columnas;
		
		if(filas < 1) {
			this.filas = 1;
		}
		if(columnas < 1) {
			this.columnas = 1;
		}
		
		this.celda = new Celda[this.filas][this.columnas];
		for(int i = 0; i < this.filas; i++) {
			for(int j = 0; j < this.columnas; j++) {
				celda[i][j] = new Celda(new Coordenada(i,j));
			}
		}
	}
	
	//Constructor de las celdas en la region
	public Region(Celda[][] celda) {
		//Creamos una region que sea una COPIA de la celda pasada
		//Una vez creada, ahora le vamos a hacer una copia de todo lo q tiene a lo que hay
		this.celda = new Celda[celda.length][celda[0].length];
		
		for(int i = 0; i < celda.length; i++) {
			for(int j = 0; j < celda[0].length; j++) {
				Celda celdaAux = new Celda(new Coordenada(i,j));
				if(celda[i][j].estaVacia()) {
					//la que tiene la pieza es celda[i][j], Tenemos que sacarla, guardarla en una variable pieza y luego meterla en la aux?
					Pieza piezaAux = celda[i][j].consultarPieza();
					celdaAux.establecerPieza(piezaAux);
				}
				this.celda[i][j] = celdaAux;
			}
		}
	}
	/**
	 * Obtiene el numero de filas
	 * 
	 * @return filas
	 */
	public int consultarNumeroFilas() {
		return filas;
	}

	/**
	 * Obtiene el numero de columnas
	 * 
	 * @return columnas
	 */
	public int consultarNumeroColumnas() {
		return columnas;
	}

	/**
	 * Colocamos la pieza en las coordenadas dadas si es posible
	 * 
	 * @param pieza
	 * @param coordenada
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if(!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) { //esta funcion devuelve "true" si existe ya una pieza en ella
			this.celda[coordenada.fila()][coordenada.columna()].establecerPieza(pieza);
		}
	}
	
	/**
	 * Mira si en la coordenada se encuentra una celda 
	 * 
	 * @param coordenada
	 * @return
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		//Vamos a crear una celda aux para ver si que tiene la que se situa en esas coords
		Celda celdaAux = new Celda(coordenada);
		if(!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
			celdaAux.establecerPieza(this.celda[coordenada.fila()][coordenada.columna()].consultarPieza());
		}
		
		return celdaAux;
	}
	
	
	/**
	 * Calcula el giro y mira si hay piezas para cambiarlas de posicion
	 * 
	 * @return la region indicada girada ANTIHORARIAMENTE
	 */
	public Region rotarSentidoAntihorario() {
		int F = consultarNumeroFilas();
		int C = consultarNumeroColumnas();
		Region regionGirada = new Region(C, F);
		
		for(int i = 0;i < F; i++) {
			for(int j = 0;j < C; j++) {
				if(this.celda[i][j].estaVacia()) {
					regionGirada.colocar(this.celda[i][j].consultarPieza(), new Coordenada(j,F-1-i));				}
			}
		}
		return regionGirada;
	}
	
	
	/**
	 * Calcula el giro y mira si hay piezas para cambiarlas de posicion
	 * 
	 * @return la region indicada girada HORARIAMENTE
	 */
	public Region rotarSentidoHorario() {
		int F = consultarNumeroFilas();
		int C = consultarNumeroColumnas();
		Region regionGirada = new Region(C, F);
		
		for(int i = 0;i < F; i++) {
			for(int j = 0;j < C; j++) {
				if(!this.celda[i][j].estaVacia()) {
					regionGirada.colocar(this.celda[i][j].consultarPieza(), new Coordenada(C-1-j,i));
				}
			}
		}
		return regionGirada;
	}
	
	
	/**
	 * Determina si la coordenada se encuentra en la region
	 * 
	 * @param coordenada
	 * @return true si esta, false si no
	 */
	public boolean estaEnRegion(Coordenada coordenada) {
		if(coordenada.fila() >= 0 && coordenada.columna() >= 0)	
			if((coordenada.fila() < this.consultarNumeroFilas()) && coordenada.columna() < this.consultarNumeroColumnas()) {
				return true;
		}
		return false;
	}
	
	/**
	 * Obtiene la region entera, y la transcribe a caracteres
	 * 
	 * @return Region en caracteres
	 */
	public String aTexto() {
		
		String texto = "";
		int F = this.consultarNumeroFilas();
		int C = this.consultarNumeroColumnas();
		
		for(int i = 0; i < F; i++) {
			texto = texto + "\n";
			for(int j = 0; j < C; j++) {
				if(this.celda[i][j].estaVacia()) {
					texto = texto + "-";
				}else {
					char c = this.celda[i][j].consultarPieza().color().aCaracter();
					texto = texto + c;
				}
			}
		}
		texto = texto + "\n";
		
		return texto;
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
		Region other = (Region) obj;
		return Arrays.deepEquals(celda, other.celda) && columnas == other.columnas && filas == other.filas;
	}

	@Override
	public String toString() {
		return "Region [filas=" + filas + ", columnas=" + columnas + ", celda=" + Arrays.toString(celda) + "]";
	} 

}