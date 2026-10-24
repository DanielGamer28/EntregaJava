package pentago.modelo;

import java.util.Arrays;
import java.util.Objects;

import pentago.util.Coordenada;

/**
 * Representa una región rectangular de celdas extraída de un tablero,
 * permitiendo aplicar rotaciones en sentido horario y antihorario.
 * 
 * @author Daniel Acitores Martinez
 * @author Claudia Berezo Berzosa
 * 
 */
public class Region {

	/** Número de filas de la región. */
	private int filas;

	/** Número de columnas de la región. */
	private int columnas;

	/** Matriz bidimensional de celdas que componen la región. */
	private Celda[][] celda;

	/**
	 * Construye una región rectangular de celdas vacías.
	 * Si el valor de filas o columnas es menor de 1, se acota a un mínimo de 1.
	 * 
	 * @param filas número de filas de la región
	 * @param columnas número de columnas de la región
	 */
	public Region(int filas, int columnas) {
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
	 * Construye una región a partir de una copia en profundidad de las piezas
	 * contenidas en la matriz de celdas recibida.
	 * 
	 * @param celda matriz bidimensional de celdas origen
	 */
	public Region(Celda[][] celda) {
		this.filas = celda.length;
		this.columnas = celda[0].length;
		this.celda = new Celda[this.filas][this.columnas];

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				Celda celdaAux = new Celda(new Coordenada(i, j));
				if (celda[i][j] != null && !celda[i][j].estaVacia()) {
					Pieza piezaAux = celda[i][j].consultarPieza();
					celdaAux.establecerPieza(piezaAux);
				}
				this.celda[i][j] = celdaAux;
			}
		}
	}

	/**
	 * Consulta el número de filas de la región.
	 * 
	 * @return número de filas
	 */
	public int consultarNumeroFilas() {
		return this.filas;
	}

	/**
	 * Consulta el número de columnas de la región.
	 * 
	 * @return número de columnas
	 */
	public int consultarNumeroColumnas() {
		return this.columnas;
	}

	/**
	 * Coloca la pieza indicada en la celda correspondiente a la coordenada dada,
	 * siempre que la coordenada pertenezca a la región.
	 * 
	 * @param pieza pieza a colocar
	 * @param coordenada coordenada dentro de la región
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if (pieza != null && coordenada != null && this.estaEnRegion(coordenada)) {
			this.celda[coordenada.fila()][coordenada.columna()].establecerPieza(pieza);
		}
	}

	/**
	 * Consulta una copia en profundidad de la celda situada en la coordenada dada.
	 * 
	 * @param coordenada coordenada de la celda dentro de la región
	 * @return clon de la celda consultada, o null si está fuera de la región
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		Celda celdaAux = null;
		if (coordenada != null && this.estaEnRegion(coordenada)) {
			celdaAux = new Celda(coordenada);
			if (!this.celda[coordenada.fila()][coordenada.columna()].estaVacia()) {
				celdaAux.establecerPieza(this.celda[coordenada.fila()][coordenada.columna()].consultarPieza());
			}
		}
		return celdaAux;
	}

	/**
	 * Genera una nueva región rotando 90 grados en sentido horario las piezas
	 * contenidas en la región actual.
	 * 
	 * @return nueva región con las piezas rotadas en sentido horario
	 */
	public Region rotarSentidoHorario() {
		int numFilas = this.consultarNumeroFilas();
		int numColumnas = this.consultarNumeroColumnas();
		Region regionGirada = new Region(numColumnas, numFilas);

		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numColumnas; j++) {
				if (!this.celda[i][j].estaVacia()) {
					regionGirada.colocar(this.celda[i][j].consultarPieza(), new Coordenada(j, numFilas - 1 - i));
				}
			}
		}
		return regionGirada;
	}

	/**
	 * Genera una nueva región rotando 90 grados en sentido antihorario las piezas
	 * contenidas en la región actual.
	 * 
	 * @return nueva región con las piezas rotadas en sentido antihorario
	 */
	public Region rotarSentidoAntihorario() {
		int numFilas = this.consultarNumeroFilas();
		int numColumnas = this.consultarNumeroColumnas();
		Region regionGirada = new Region(numColumnas, numFilas);

		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numColumnas; j++) {
				if (!this.celda[i][j].estaVacia()) {
					regionGirada.colocar(this.celda[i][j].consultarPieza(), new Coordenada(numColumnas - 1 - j, i));
				}
			}
		}
		return regionGirada;
	}

	/**
	 * Comprueba si la coordenada indicada pertenece a los límites de la región.
	 * 
	 * @param coordenada coordenada a comprobar
	 * @return true si está dentro de la región, false en caso contrario
	 */
	public boolean estaEnRegion(Coordenada coordenada) {
		boolean enRegion = false;
		if (coordenada != null && coordenada.fila() >= 0 && coordenada.columna() >= 0) {
			if (coordenada.fila() < this.consultarNumeroFilas()
					&& coordenada.columna() < this.consultarNumeroColumnas()) {
				enRegion = true;
			}
		}
		return enRegion;
	}

	/**
	 * Genera una representación textual de la región con caracteres 'X', 'O'
	 * y '-' para celdas vacías.
	 * 
	 * @return cadena de texto con la representación de la región
	 */
	public String aTexto() {
		String texto = "";
		int numFilas = this.consultarNumeroFilas();
		int numColumnas = this.consultarNumeroColumnas();

		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numColumnas; j++) {
				if (this.celda[i][j].estaVacia()) {
					texto = texto + "-";
				} else {
					char c = this.celda[i][j].consultarPieza().color().aCaracter();
					texto = texto + c;
				}
			}
			if (i < numFilas - 1) {
				texto = texto + "\n";
			}
		}
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
