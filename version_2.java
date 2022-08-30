package libre;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class version_2 {
	final static int LARGO_MAXIMO_BARCO = 5;
	final static double PORCENTAJE = 0.20;

	public static void main(String[] args) {

		int maxFila = elegir_entero("ancho");
		int maxColum = elegir_entero("largo");
		maxFila += 1;
		maxColum += 1;
		int[][] tableroPlayer1 = new int[maxFila][maxColum];
		int[][] tableroPlayer2 = new int[maxFila][maxColum];
		int cantidadDeEspaciosBarcos = calcular_porcentaje(maxFila, maxColum);
		System.out.println("total_barcos " + cantidadDeEspaciosBarcos);
		cargar_barcos_a_tablero(tableroPlayer1, maxFila, maxColum, cantidadDeEspaciosBarcos);
		cargar_barcos_a_tablero(tableroPlayer2, maxFila, maxColum, cantidadDeEspaciosBarcos);
		System.out.println("tableroPlayer1");
		mostrar_matriz(maxFila, maxColum, tableroPlayer1);
		System.out.println("tableroPlayer2"); // <----saca el porcentaje y muestra las matrices cargadas por pantalla
		mostrar_matriz(maxFila, maxColum, tableroPlayer2);
		char pc = 'P';
		char vs = 'V';
		char modoseleccionado = modoDeJuego(pc, vs);
		if (modoseleccionado == pc) {
			jugarContralaPc(tableroPlayer1, tableroPlayer2, maxFila, maxColum, cantidadDeEspaciosBarcos);
		} else if (modoseleccionado == vs) {
			jugadorContraJugador(tableroPlayer1, tableroPlayer2, maxFila, maxColum, cantidadDeEspaciosBarcos);

		}
	}

	private static void jugadorContraJugador(int[][] tablero, int[][] tablero2, int maxF, int maxC, int limit) {
		int[][] tablero_pantalla1 = new int[maxF][maxC];
		int[][] tablero_pantalla2 = new int[maxF][maxC];
		int fila_elegida = 0;
		int colum_elegida = 0;
		boolean valido = false;
		while (!valido) {
			
			System.out.println("NUESTRO MAPA CAPITAN");
			mostrar_matriz(maxF, maxC, tablero_pantalla1);
			System.out.println("NUESTRO MAPA CAPITAN 2");
			mostrar_matriz(maxF, maxC, tablero_pantalla2);
			System.out.println("Elige Capitan 1");
			fila_elegida = elegir_fila_para_la_bomba(maxF);
			colum_elegida = elegir_colum_para_la_bomba(maxC);
			boolean bombardeo_realizado = comprobarSiFilaColumYaFueronBombardeadas(fila_elegida, colum_elegida,
					tablero_pantalla2);
			if (bombardeo_realizado == false) {
				tirar_bomba(fila_elegida, colum_elegida, tablero2, tablero_pantalla2);
				valido = comprobar_si_gano_alguno(tablero_pantalla2, limit, maxF, maxC);
				if (valido == true) {
					System.out.println("!!!Gano El Capitan frente al Capitan 2!!!");
					System.out.println("NUESTRO MAPA CAPITAN 1");
					mostrar_matriz(maxF, maxC, tablero_pantalla1);
					System.out.println("NUESTRO MAPA CAPITAN 2");
					mostrar_matriz(maxF, maxC, tablero_pantalla2);
				}
				if (valido == false) {
					System.out.println("Elige capitan 2");
					fila_elegida = elegir_fila_para_la_bomba(maxF);
					colum_elegida = elegir_colum_para_la_bomba(maxC);
					bombardeo_realizado = comprobarSiFilaColumYaFueronBombardeadas(fila_elegida, colum_elegida,
							tablero_pantalla1);
					if (bombardeo_realizado == false) {
						tirar_bomba(fila_elegida, colum_elegida, tablero, tablero_pantalla1);
						valido = comprobar_si_gano_alguno(tablero_pantalla1, limit, maxF, maxC);
						if (valido == true) {
							System.out.println("!!!Gano El Capitan 2 frente al Capitan 1!!!");
							System.out.println("NUESTRO MAPA CAPITAN 1");
							mostrar_matriz(maxF, maxC, tablero_pantalla1);
							System.out.println("NUESTRO MAPA CAPITAN 2");
							mostrar_matriz(maxF, maxC, tablero_pantalla2);
						}
					} else {
						System.out.println("esa fila colum ya fue bombardeada por favor elige otra");
						fila_elegida = elegir_fila_para_la_bomba(maxF);
						colum_elegida = elegir_colum_para_la_bomba(maxC);
						if (tablero_pantalla1[fila_elegida][colum_elegida] != 2
								&& tablero_pantalla1[fila_elegida][colum_elegida] != 1) {
							tirar_bomba(fila_elegida, colum_elegida, tablero, tablero_pantalla1);
							valido = comprobar_si_gano_alguno(tablero_pantalla1, limit, maxF, maxC);
						}

					}
				}
			} else {
				System.out.println("la fila columna ya fue bombardeada por favor elige otra");

			}
		}
	}

	private static void jugarContralaPc(int[][] tablero, int[][] tablero2, int maxF, int maxC, int limit) {
		int[][] tablero_pantalla1 = new int[maxF][maxC];
		int[][] tablero_pantalla2 = new int[maxF][maxC];
		int fila_elegida = 0;
		int colum_elegida = 0;
		boolean valido = false;
		while (!valido) {
			System.out.println("NUESTRO MAPA CAPITAN");
			mostrar_matriz(maxF, maxC, tablero_pantalla1);
			System.out.println("MAPA DEL ENEMIGO");
			mostrar_matriz(maxF, maxC, tablero_pantalla2);
			fila_elegida = elegir_fila_para_la_bomba(maxF);
			colum_elegida = elegir_colum_para_la_bomba(maxC);
			boolean bombardeo_realizado = comprobarSiFilaColumYaFueronBombardeadas(fila_elegida, colum_elegida,
					tablero_pantalla2);
			if (bombardeo_realizado == false) {
				tirar_bomba(fila_elegida, colum_elegida, tablero2, tablero_pantalla2);
				valido = comprobar_si_gano_alguno(tablero_pantalla2, limit, maxF, maxC);
				if (valido == true) {
					System.out.println("!!!Gano el Capitan a la pc!!!");
					System.out.println("NUESTRO MAPA CAPITAN");
					mostrar_matriz(maxF, maxC, tablero_pantalla1);
					System.out.println("MAPA DEL ENEMIGO");
					mostrar_matriz(maxF, maxC, tablero_pantalla2);
				}

				if (valido == false) {
					tirar_bomba_random(maxF, maxC, tablero, tablero_pantalla1);
					valido = comprobar_si_gano_alguno(tablero_pantalla1, limit, maxF, maxC);
					if (valido == true) {
						System.out.println("!!!!Gano la Pc al Capitan!!!");
						System.out.println("NUESTRO MAPA CAPITAN");
						mostrar_matriz(maxF, maxC, tablero_pantalla1);
						System.out.println("MAPA DEL ENEMIGO");
						mostrar_matriz(maxF, maxC, tablero_pantalla2);
					}
				}
			} else {
				System.out.println("la fila columna ya fue bombardeada por favor elige otra");

			}
		}

	}

	private static boolean comprobar_si_gano_alguno(int[][] tablero_p, int limit, int maxF, int maxC) {
		boolean valido = false;
		int cantadad_de_barcos_hundidos = 0;
		for (int fila = 1; fila < maxF; fila++) {
			for (int colum = 1; colum < maxC; colum++) {
				if (tablero_p[fila][colum] == 1) {
					cantadad_de_barcos_hundidos++;
				}
			}
		}
		if (cantadad_de_barcos_hundidos == limit) {
			valido = true;
		}
		return valido;
	}

	private static boolean comprobarSiFilaColumYaFueronBombardeadas(int fila, int colum, int[][] tablero_p) {
		boolean valido = false;
		if (tablero_p[fila][colum] == 2 || tablero_p[fila][colum] == 1) {
			valido = true;
		}
		return valido;
	}

	private static void tirar_bomba_random(int maxF, int maxC, int[][] tablero, int[][] tablero_p) {
		int fila = (maxF - 1);
		int colum = (maxC - 1);
		boolean valido = false;
		while (!valido) {
			int fila_r = (int) (fila * Math.random() + 1);
			int colum_r = (int) (colum * Math.random() + 1);
			if (!(tablero_p[fila_r][colum_r] == 2 || tablero_p[fila_r][colum_r] == 1)) {
				if (tablero[fila_r][colum_r] == -1) {
					tablero_p[fila_r][colum_r] = 1;
					valido = true;

				} else if (tablero[fila_r][colum_r] == 0) {
					tablero_p[fila_r][colum_r] = 2;
					valido = true;
				}
			}
		}

	}

	private static void tirar_bomba(int fila_e, int colum_e, int[][] tablero, int[][] tablero_p) {
		if (tablero[fila_e][colum_e] == -1) {
			tablero_p[fila_e][colum_e] = 1;
		} else {
			tablero_p[fila_e][colum_e] = 2;
		}

	}

	private static int elegir_fila_para_la_bomba(int maxF) {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		boolean valido = false;
		int fila = 0;
		while (!valido) {
			try {
				System.out.println("elegir fila ala cual se debe tirar la bomba");
				fila = new Integer(entrada.readLine());
				if (fila >= 1 && fila <= maxF) {
					valido = true;
				}

			} catch (Exception err) {
				System.out.println("err");
			}

		}
		return fila;
	}

	private static int elegir_colum_para_la_bomba(int maxC) {
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		boolean valido = false;
		int colum = 0;
		while (!valido) {
			try {
				System.out.println("elegir colum a la cual se debe tirar la bomba");
				colum = new Integer(entrada.readLine());
				if (colum >= 1 && colum <= maxC) {
					valido = true;
				}

			} catch (Exception err) {
				System.out.println("err");
			}

		}
		return colum;
	}

	private static char modoDeJuego(char pc, char vs) {
		boolean valido = false;
		char modoseleccionado = ' ';
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		while (!valido) {
			try {
				System.out.println("ingrese el modo de juego que desea jugar");
				System.out
						.println("escriba P si quiere jugar contra la PC o escriba V si quiere jugar contra un amigo");
				modoseleccionado = (char) (entrada.readLine().charAt(0));
				if (modoseleccionado == pc) {
					modoseleccionado = pc;
					valido = true;
				} else if (modoseleccionado == vs) {
					modoseleccionado = vs;
					valido = true;
				}
			} catch (Exception err) {
				System.out.println("err");
			}
		}
		return modoseleccionado;
	}

	public static void cargar_barcos_a_tablero(int[][] tablero, int maxF, int maxC, int EspacioBarcos) {
		int limit = 0;
		int tamanio_barco = 0;
		int maxfila_R = (maxF - 1);
		int maxcolum_R = (maxC - 1);
		while (!(limit >= EspacioBarcos)) {
			int tamanio_anterior_barco = tamanio_barco;
			tamanio_barco++;
			int fila_random = (int) (maxfila_R * Math.random() + 1);
			int colum_random = (int) (maxcolum_R * Math.random() + 1);
			int columFinalBarco = (colum_random + tamanio_barco - 1);
			boolean mePase = me_pase_de_max_c(columFinalBarco, maxC);
			boolean limiteMasTamanioBarco = comprobar(limit, tamanio_barco, EspacioBarcos);
			if (limiteMasTamanioBarco == false) {
				if (mePase == false) {
					boolean valido = siEntraBarco(fila_random, colum_random, tablero, columFinalBarco);
					if (valido == true) {
						ubicar_barco(fila_random, colum_random, tablero, columFinalBarco);

						if (tamanio_barco > LARGO_MAXIMO_BARCO) {
							tamanio_barco = 0;
						}
					} else {
						tamanio_barco = tamanio_anterior_barco;
					}
					limit = espaciosUtilizadosBarcos(tablero, maxF, maxC);
				} else {
					tamanio_barco = tamanio_anterior_barco;
					limit = espaciosUtilizadosBarcos(tablero, maxF, maxC);
				}
			} else {
				tamanio_barco = 0;
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

	private static int espaciosUtilizadosBarcos(int[][] tablero, int maxF, int maxC) {
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

	private static boolean me_pase_de_max_c(int colum, int maxC) {
		boolean valido = true;
		if (colum >= 0 && colum <= maxC - 1) {
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
				System.out.println("ingrese el " + dimension + " del tablero que sea mayor a 5 y menor a o igual 10");
				valor = new Integer(entrada.readLine());

				if (valor >= 5 && valor <= 10) {
					valido = true;
				}
			} catch (Exception err) {
				System.out.println("err");
			}
		}
		return valor;
	}

	public static void mostrar_matriz(int maxF, int maxC, int[][] matriz) {
		cargar_cordenadas_ala_matriz(maxF, maxC, matriz);
		for (int fila = 0; fila < maxF; fila++) {
			mostrar_arreglo(matriz[fila], maxC);
		}
	}

	private static void cargar_cordenadas_ala_matriz(int maxF, int maxC, int[][] matriz) {
		int fila_o = 0;
		int colum_o = 0;
		for (int columna = 0; columna < maxC; columna++) {
			matriz[fila_o][columna] = columna;
		}
		for (int fila = 0; fila < maxF; fila++) {
			matriz[fila][colum_o] = fila;
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
		ancho -= 1;
		largo -= 1;
		multipliclasion = ancho * largo;
		resultado = (int) (multipliclasion * PORCENTAJE);

		return resultado;
	}

	public static boolean siEntraBarco(int fila, int colum, int[][] tablero, int colum_F) {
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
f
