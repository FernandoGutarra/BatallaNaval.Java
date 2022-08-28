package libre;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class version_2 {
	final static int LARGO_MAXIMO_BARCO = 5;
	final static double PORCENTAJE = 0.20;

	public static void main(String[] args) {

		int maxFila = elegir_entero("ancho");
		int maxColum = elegir_entero("largo");
		int[][] tableroPlayer1 = new int[maxFila][maxColum];
		int[][] tableroPlayer2 = new int[maxFila][maxColum];
		System.out.println("tableroPlayer1");
		mostrar_matriz(maxFila, maxColum, tableroPlayer1);
		System.out.println("tableroPlayer2");
		mostrar_matriz(maxFila, maxColum, tableroPlayer2);
		int cantidadDeEspaciosBarcos = calcular_porcentaje(maxFila, maxColum);
		System.out.println("total_barcos " + cantidadDeEspaciosBarcos);
		cargar_barcos_a_tablero(tableroPlayer1, maxFila, maxColum, cantidadDeEspaciosBarcos);
		cargar_barcos_a_tablero(tableroPlayer2, maxFila, maxColum, cantidadDeEspaciosBarcos);
		System.out.println("tableroPlayer1");
		mostrar_matriz(maxFila, maxColum, tableroPlayer1);
		System.out.println("tableroPlayer2");
		mostrar_matriz(maxFila, maxColum, tableroPlayer2);
	}

	public static void cargar_barcos_a_tablero(int[][] tablero, int maxF, int maxC, int EspacioBarcos) {
		int limit = 0;
		int tamanio_barco = 0;
		int maxF_r = (maxF - 1);
		int maxC_r = (maxC - 1);
		while (!(limit >= EspacioBarcos)) {
			int tamanio_anterior_barco = tamanio_barco;
			tamanio_barco++;
			int fila_random = (int) (maxF_r * Math.random() + 1);
			int colum_random = (int) (maxC_r * Math.random() + 1);
			int ColumFinalBarco = (colum_random + tamanio_barco - 1);
			boolean MePase = MePaseDeMaxC(ColumFinalBarco, maxC);
			boolean limiteMasTamanioBarco = comprobar(limit, tamanio_barco, EspacioBarcos);
			if (limiteMasTamanioBarco == false) {
				if (MePase == false) {
					boolean valido = SiEntraBarco(fila_random, colum_random, tablero, ColumFinalBarco);
					if (valido == true) {
						ubicar_barco(fila_random, colum_random, tablero, ColumFinalBarco);

						if (tamanio_barco > LARGO_MAXIMO_BARCO) {
							tamanio_barco = 0;
						}
					} else {
						tamanio_barco = tamanio_anterior_barco;
					}
					limit = EspaciosUtilizadosBarcos(tablero, maxF, maxC);
				} else {
					tamanio_barco = tamanio_anterior_barco;
					limit = EspaciosUtilizadosBarcos(tablero, maxF, maxC);
				}
			}
			else {
				tamanio_barco=0;
			}
		}

	}

	private static boolean comprobar(int limit, int tamanio_barco, int EspacioBarco) {
		boolean valido = false;
		int suma = limit + tamanio_barco;
		if (suma > EspacioBarco) {
			valido = true;
		}
		return valido;
	}

	private static int EspaciosUtilizadosBarcos(int[][] tablero, int maxF, int maxC) {
		int valor = 0;
		for (int fila = 0; fila < maxF; fila++) {
			for (int colum = 0; colum < maxC; colum++) {
				if (tablero[fila][colum] == -1) {
					valor++;
				}
			}
		}
		return valor;
	}

	private static boolean MePaseDeMaxC(int Colum, int maxC) {
		boolean valido = true;
		if (Colum >= 0 && Colum <= maxC - 1) {
			valido = false;
		}
		return valido;
	}

	private static void ubicar_barco(int fila, int colum, int[][] tablero, int columFinal) {
		boolean valido = false;
		while (!valido) {
			if (colum == columFinal) {
				tablero[fila][colum] = -1;
				valido = true;
			}
			if (colum < columFinal) {
				tablero[fila][colum] = -1;
				colum++;
			}
		}
	}

	private static int elegir_entero(String dimension) {
		int valor = 0;
		boolean valido = false;
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		while (!valido) {
			try {
				System.out.println("ingrese el " + dimension + " del tablero que sea mayor a 5");
				valor = new Integer(entrada.readLine());

				if (valor >= 5) {
					valido = true;
				}
			} catch (Exception err) {
				System.out.println("err");
			}
		}
		return valor;
	}

	public static void mostrar_matriz(int maxF, int maxC, int[][] matriz) {

		for (int fila = 0; fila < maxF; fila++) {
			mostrar_arreglo(matriz[fila], maxC);
		}
	}

	public static void mostrar_arreglo(int[] array, int maxC) {
		for (int colum = 0; colum < maxC; colum++) {
			System.out.print(" [" + array[colum] + "] ");

		}
		System.out.println("");
	}

	public static int calcular_porcentaje(int ancho, int largo) {
		int multipliclasion = 0;
		int resultado = 0;
		multipliclasion = ancho * largo;
		resultado = (int) (multipliclasion * PORCENTAJE);

		return resultado;
	}

	public static boolean SiEntraBarco(int fila, int colum, int[][] tablero, int colum_F) {
		boolean valido = true;
		boolean parar = false;
		while (!parar) {
			if (colum == colum_F) {
				if (tablero[fila][colum] == -1) {
					valido = false;
				}
				parar = true;
			}
			if (colum < colum_F) {

				if (tablero[fila][colum] == -1) {
					valido = false;
					parar = true;
				}
				colum++;
			}
		}
		return valido;
	}

}
