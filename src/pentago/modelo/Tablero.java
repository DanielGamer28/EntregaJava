package pentago.modelo;

import java.util.Arrays;
import java.util.Objects;

import pentago.util.Coordenada;
import pentago.util.Direccion;

/**
 * Representa un tablero bidimensional de celdas para el juego Pentago.
 * 
 * @author Daniel Acitores Martinez
 * @author Claudia Berezo Berzosa
 * 
 */
public class Tablero {

	/** Número de filas del tablero. */
	private int filas;

	/** Número de columnas del tablero. */
	private int columnas;

	/** Matriz bidimensional de celdas que componen el tablero. */
	private Celda[][] celda;

	/**
	 * Construye un tablero rectangular de celdas vacías.
	 * Si el valor de filas o columnas es menor de 1, se acota a un mínimo de 1.
	 * 
	 * @param filas número de filas del tablero
	 * @param columnas número de columnas del tablero
	 */
	public Tablero(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;

		if (filas < 1) {
			this.filas = 1;
		}
		if (columnas < 1) {
			this.columnas = 1;
		}

		this.celda = new Celda[this.filas][this.columnas];
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				this.celda[i][j] = new Celda(new Coordenada(i, j));
			}
		}
	}

	/**
	 * Genera una representación textual del tablero con caracteres 'X', 'O'
	 * y '-' para celdas vacías.
	 * 
	 * @return cadena de texto con el estado actual del tablero
	 */
	public String aTexto() {
		String texto = "";

		for (int i = 0; i < this.consultarNumeroFilas(); i++) {
			for (int j = 0; j < this.consultarNumeroColumnas(); j++) {
				if (this.celda[i][j].estaVacia()) {
					texto = texto + "-";
				} else {
					char c = this.celda[i][j].consultarPieza().color().aCaracter();
					texto = texto + c;
				}
			}
			if (i < this.consultarNumeroFilas() - 1) {
				texto = texto + "\n";
			}
		}
		return texto;
	}

	/**
	 * Coloca una pieza en la celda correspondiente a la coordenada indicada,
	 * siempre que la coordenada esté dentro del tablero y la pieza no sea nula.
	 * 
	 * @param pieza pieza a colocar en el tablero
	 * @param coordenada coordenada de la celda destino
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if (pieza != null && coordenada != null && this.estaEnTablero(coordenada)) {
			this.celda[coordenada.fila()][coordenada.columna()].establecerPieza(pieza);
		}
	}

	/**
	 * Consulta una copia en profundidad de la celda situada en la coordenada dada.
	 * 
	 * @param coordenada coordenada de la celda a consultar
	 * @return clon de la celda consultada, o null si está fuera del tablero
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		Celda celdaAux = null;
		if (coordenada != null && this.estaEnTablero(coordenada)) {
			celdaAux = new Celda(coordenada);
			if (!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
				celdaAux.establecerPieza(this.celda[coordenada.fila()][coordenada.columna()].consultarPieza());
			}
		}
		return celdaAux;
	}

	/**
	 * Consulta el número de columnas del tablero.
	 * 
	 * @return número de columnas
	 */
	public int consultarNumeroColumnas() {
		return this.columnas;
	}

	/**
	 * Consulta el número de filas del tablero.
	 * 
	 * @return número de filas
	 */
	public int consultarNumeroFilas() {
		return this.filas;
	}

	/**
	 * Cuenta el número total de piezas en el tablero que tienen el color indicado.
	 * 
	 * @param color color de las piezas a contar
	 * @return número de piezas de dicho color en el tablero
	 */
	public int consultarNumeroPiezas(Color color) {
		int total = 0;

		for (int i = 0; i < this.consultarNumeroFilas(); i++) {
			for (int j = 0; j < this.consultarNumeroColumnas(); j++) {
				if (!this.celda[i][j].estaVacia()) {
					if (this.celda[i][j].consultarPieza().color() == color) {
						total++;
					}
				}
			}
		}
		return total;
	}

	/**
	 * Genera una nueva región copiando el contenido de una porción del tablero
	 * desde la esquina superior izquierda indicada.
	 * 
	 * @param esquinaSuperiorIzquierda coordenada de referencia de inicio
	 * @param tamañoFilas número de filas de la región a extraer
	 * @param tamañoColumnas número de columnas de la región a extraer
	 * @return nueva región con la copia de las celdas correspondientes
	 */
	public Region consultarRegion(Coordenada esquinaSuperiorIzquierda, int tamañoFilas, int tamañoColumnas) {
		Celda[][] celdaAux = new Celda[tamañoFilas][tamañoColumnas];

		for (int i = 0; i < tamañoFilas; i++) {
			for (int j = 0; j < tamañoColumnas; j++) {
				int fila = i + esquinaSuperiorIzquierda.fila();
				int columna = j + esquinaSuperiorIzquierda.columna();
				celdaAux[i][j] = new Celda(new Coordenada(i, j));
				if (this.estaEnTablero(new Coordenada(fila, columna)) && !this.celda[fila][columna].estaVacia()) {
					celdaAux[i][j].establecerPieza(this.celda[fila][columna].consultarPieza());
				}
			}
		}
		return new Region(celdaAux);
	}

	/**
	 * Cuenta el número de piezas consecutivas del mismo color que la pieza situada
	 * en la coordenada dada, evaluando ambos sentidos de la dirección indicada.
	 * 
	 * @param coordenada coordenada de partida
	 * @param dir dirección en la que se cuentan las piezas consecutivas
	 * @return número de piezas consecutivas del mismo color
	 */
	public int contarPiezasConsecutivas(Coordenada coordenada, Direccion dir) {
		int total = 0;

		if (coordenada != null && dir != null && this.estaEnTablero(coordenada)
				&& !this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
			total = 1;
			Color colorBuscado = this.celda[coordenada.fila()][coordenada.columna()].consultarPieza().color();
			total += this.contarEnSentido(coordenada, dir.consultarDesplazamientoEnFilas(),
					dir.consultarDesplazamientoEnColumnas(), colorBuscado);
			total += this.contarEnSentido(coordenada, -dir.consultarDesplazamientoEnFilas(),
					-dir.consultarDesplazamientoEnColumnas(), colorBuscado);
		}
		return total;
	}

	/**
	 * Cuenta las piezas consecutivas de un mismo color avanzando en un sentido concreto.
	 * 
	 * @param origen coordenada inicial desde la que se avanza
	 * @param despFila desplazamiento vertical en cada paso
	 * @param despCol desplazamiento horizontal en cada paso
	 * @param color color de la pieza que se busca
	 * @return cantidad de piezas consecutivas encontradas en ese sentido
	 */
	private int contarEnSentido(Coordenada origen, int despFila, int despCol, Color color) {
		int contador = 0;
		int filaActual = origen.fila() + despFila;
		int colActual = origen.columna() + despCol;
		boolean seguirContando = true;

		while (this.estaEnTablero(new Coordenada(filaActual, colActual)) && seguirContando) {
			Celda celdaActual = this.celda[filaActual][colActual];
			if (!celdaActual.estaVacia() && celdaActual.consultarPieza().color() == color) {
				contador++;
				filaActual += despFila;
				colActual += despCol;
			} else {
				seguirContando = false;
			}
		}
		return contador;
	}

	/**
	 * Comprueba si la coordenada dada se encuentra dentro de los límites del tablero.
	 * 
	 * @param coordenada coordenada a verificar
	 * @return true si pertenece al tablero, false en caso contrario
	 */
	public boolean estaEnTablero(Coordenada coordenada) {
		boolean dentro = false;
		if (coordenada != null && coordenada.fila() >= 0 && coordenada.columna() >= 0) {
			if (coordenada.fila() < this.consultarNumeroFilas()
					&& coordenada.columna() < this.consultarNumeroColumnas()) {
				dentro = true;
			}
		}
		return dentro;
	}

	/**
	 * Comprueba si todas las celdas del tablero contienen una pieza.
	 * 
	 * @return true si el tablero está lleno, false en caso contrario
	 */
	public boolean estaCompleto() {
		boolean completo = false;
		int totalCeldas = this.consultarNumeroColumnas() * this.consultarNumeroFilas();
		int totalPiezas = this.consultarNumeroPiezas(Color.NEGRO) + this.consultarNumeroPiezas(Color.BLANCO);

		if (totalCeldas == totalPiezas) {
			completo = true;
		}
		return completo;
	}

	/**
	 * Obtiene la referencia directa a la celda situada en la coordenada indicada.
	 * Si la coordenada no pertenece al tablero, retorna null.
	 * 
	 * @param coordenada coordenada de la celda a obtener
	 * @return referencia a la celda en el tablero, o null si está fuera de rango
	 */
	Celda obtenerCelda(Coordenada coordenada) {
		Celda celdaObtenida = null;
		if (this.estaEnTablero(coordenada)) {
			celdaObtenida = this.celda[coordenada.fila()][coordenada.columna()];
		}
		return celdaObtenida;
	}

	/**
	 * Vuelca sobre el tablero el contenido en piezas de la región recibida,
	 * sobrescribiendo el contenido previo desde la esquina superior izquierda dada.
	 * 
	 * @param esquinaSuperiorIzquierda coordenada del tablero donde coincide el (0,0) de la región
	 * @param region región cuyas piezas se van a restaurar en el tablero
	 */
	public void restaurarPiezas(Coordenada esquinaSuperiorIzquierda, Region region) {
		if (esquinaSuperiorIzquierda != null && region != null && this.estaEnTablero(esquinaSuperiorIzquierda)) {
			for (int i = 0; i < region.consultarNumeroFilas(); i++) {
				for (int j = 0; j < region.consultarNumeroColumnas(); j++) {
					int fila = i + esquinaSuperiorIzquierda.fila();
					int columna = j + esquinaSuperiorIzquierda.columna();
					Coordenada coordenadaReal = new Coordenada(fila, columna);

					if (this.estaEnTablero(coordenadaReal)) {
						Pieza piezaRegion = region.consultarCelda(new Coordenada(i, j)).consultarPieza();
						this.celda[fila][columna].establecerPieza(piezaRegion);
					}
				}
			}
		}
	}

	/**
	 * Realiza una copia en profundidad del tablero actual.
	 * 
	 * @return nuevo objeto tablero clonado
	 */
	public Tablero clonar() {
		Tablero tableroAux = new Tablero(this.consultarNumeroFilas(), this.consultarNumeroColumnas());
		for (int i = 0; i < this.consultarNumeroFilas(); i++) {
			for (int j = 0; j < this.consultarNumeroColumnas(); j++) {
				if (!this.celda[i][j].estaVacia()) {
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
