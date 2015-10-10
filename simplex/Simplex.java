import artilugios.*;

public class Simplex{
	
	 boolean Access = false;//Variable de control 
	
	//Objetos
	 Accion pmenu      = new Accion();
	 Read read         = new Read();
	 Redimensiona size = new Redimensiona();
	 Ecuaciones metod  = new Ecuaciones();
	 Matrices M        = new Matrices();
	 float Arreglo[][] = new float [1][1];
	 float Backup[][]  = new float [1][1];
	 boolean Pasos []  = {false,false,false};
	
	public Simplex(){}
	
	/**
	 * @param args
	public  void main(String [] args){
		Menu();
	}
	*/
	
	/**
	* Logica principal Algoritmo Simplex
	* @param Array
	*/
	public  void trySimplex(float Array[][]){
		
			boolean go = true;			
			while(go){
				int fila = Analiza1(Array);
				int columna = 0;
				if(fila != -1){
					columna = getVectorDiv(Array,fila);
					M.Paso1Gauss(Arreglo, fila, columna);
					M.Paso2Gauss(Arreglo, fila, columna);
				}else{
					go = false;
				}
			}
	}
	
	/**
	 * Menu Principal
	 * @para No recibe valores
	 */
	public void Menu(){
		boolean Go = true;
		int Option = 0;

		while(Go){

			pmenu.MenuPrint("Introducir funcion objetivo a Minimizar/Introducir Restricciones/Introducir Resultados de Restricciones/Comenzar Simplex >>/Resultado >>/Mostrar Simplex Tableu >>/Restart>>/Salir ->");
			System.out.println("Introduzca Su Opcion: ");
			Option = read.getIntf();

			switch(Option){
				case 1: 
					metod.getZEquation(Access);
					System.out.println();
					metod.viewZ();
					Access = true;
					Pasos[0] = true;
					break;

				case 2:
					metod.Restric(Access, Pasos[2]);
					System.out.println();
					metod.viewRestric();
					Access = true;
					Pasos[1] = true;
					break;

				case 3:
					metod.getbEquation(Pasos[1]);
					metod.viewb();
					System.out.println();
					Pasos[2] = true;
					break;
					
				case 4: 
					if(Requisitos()){
						Arreglo = metod.ArraySimplex(Arreglo);
						Backup = metod.ArraySimplex(Backup);
						System.out.println("\tSistema Resuelto\n");
						trySimplex(Arreglo);
						M.PrintMatrixFloat(Arreglo);
						System.out.println("\n");
					}
					break;
				
				case 5:
					if(Requisitos())
						System.out.println("\n\tVariables a Maximizar\n");
						Resultados(Arreglo);
					break;

				case 6: 
					if(Requisitos()){
						Arreglo = metod.ArraySimplex(Arreglo);
						Backup = metod.ArraySimplex(Backup);
						M.PrintMatrixFloat(Backup);
						System.out.println("\n");
					}
					break;
				case 7:
					Restart();
					break;
					
				default:
					Go = false;
					break;
			}
		}
	}
	
	private  void Restart(){
		metod.variableRestart();
		Access = false;
		for(int i = 0; i < 3; i++){
			Pasos[i] = false;
		}
		
		size.reallocFloatBidimentionArray(Arreglo, 1, 1);
		Arreglo[0][0] = 0;
		size.reallocFloatBidimentionArray(Backup, 1, 1);
		Backup[0][0] = 0;
	}

	/**
	 * 
	 * @param Array Simplex tableu
	 * @return Valor entero positivo (Fila) en caso de encontrar un numero negativo
	 */
	public  int Analiza1(float Array[][]){
		
		int x = Array.length;
		int L = Array[0].length - 1;
		
		for(int i = L; i >-1 ; i--){
			int index = 0;
			if(Array[x-1][i] < 0){
				
				for(int u  = L; u > -1; u--){
					if(Array[x-1][i] <= Array[x-1][u])
						index++;
				}
				if(index == L+1)
					return i;
			}
		}
		return -1;
	}
	
	/**
	 * Obtiene un vector para localizar el pivote en para el algoritmo
	 * 
	 * @param Array flotante que contiene los datos de la simplex tableu
	 * 
	 * @param fila Pisicion de la fila
	 * 
	 * @return Retorna el valor de la columna para realizar el intercambio
	 */
	public  int getVectorDiv(float Array[][],int fila){
		int y = Array.length;
		int x = Array[0].length;
		float Vector[] = new float[x];
		for(int i = 0; i < y; i++){
			Vector[i] = Array[i][x-1] / Array[i][fila];
		}
		int send = Analiza2(Vector);
		return send;
	}
	
	/**
	 * 
	 * @param Vector flotante, contiene los datos de la division.
	 * @return Valor entero positivo que contiene la posicion de la columna
	 */
	public  int Analiza2(float Vector[]){
		int Buff = 0;
		for(int i = 0; i < Vector.length ; i++){
			if(Vector[i] > 0)
				Buff = i;
		}
		for(int i = 0; i < Vector.length ; i++){
			if(Vector[Buff] > Vector[i] && Vector[i] > 0)
				Buff = i;
		}
		return Buff;
	}

	/**
	 * Encuentra los valores de Xn
	 * 
	 * @param A Array float,es resultado del algoritmo simplex
	 */
	public  void Resultados(float A[][]){
		
		boolean go = true;
		int y = metod.cSize;
		int x = metod.fSize;
		
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				if(Arreglo[j][i] == 1 && Arreglo[y][i] == 0){
					System.out.println("X" + (i + 1) + " = " + (Arreglo[j][Arreglo[j].length - 1]));
					go = false;
				}
			}
			if(go)
				System.out.println("X" + (i + 1) + " = " + 0);
			go = true;
		}
	}

	/**
	 * Verifica si los datos se han colocado correctamente antes de arrancar simplex
	 * @return valor booleano, false si hacen falta datos
	 */
	private  boolean Requisitos(){
		int ok = 0;
		for(int i = 0; i < 3;i++){
			if(Pasos[i])
				ok++;	
			else
				Mensajes(i);
		}
		return (ok == 3) ? true:false;
	}
	
	/**
	 * @param Recive indicador de tipo de error o dato faltante.
	 */
	private  void Mensajes(int i){
		switch(i){
		case 0:
			System.out.println("No se ha introducido una ecuacion a maximizar.");
			break;
		case 1:
			System.out.println("No se han introducido las restricciones.");
			break;
		case 2:
			System.out.println("No se han introducido los resultados de las restricciones.");
			break;
		}
		
		System.out.println("");
	}
}