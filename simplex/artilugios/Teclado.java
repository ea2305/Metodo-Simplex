package artilugios;

/**
*@author Elihu Alejandro Cruz Albores
*@version 1.1
*/

import java.io.*;

public class Teclado{
	
	String Test = "";
	
	public Teclado(){}

/**
*@param no recive parametros 
*@return Devuelve un caracter
*/
	public char LecturaChar(){//Lee e imprime un caracter
		char A  = ' ';
		try{
			A = (char)System.in.read();

		}catch(IOException ioex){

			System.out.println("Error Detectado :c");
		}

		return A;
	}

/**
*@param no recive parametros 
*/
	public void LecturaEnter(){//Lectura de caracteres hasta presionar "\n"
		char A;
		while((A = LecturaChar()) != '\n'){}
	}

/**
*@param no recive parametros
*/
	public void LecturaSimbolo(){//Lectura de caracteres hasta presionar "-"

		char A;
		while((A = LecturaChar()) != '-'){}
	}

/**
*@param no recive parametros 
*@return Devuelve una cadena de caracteres
*/
	public String getStr(){//Obtiene una cadena y la envia
		String send = "";
		char A;

		while((A = LecturaChar()) != '\n'){send = send + A;}
		return send;
	}

/**
*@param no recive parametros 
*@return Devuelve un valor entero
*/
	public int StrToInt(){//Obtiene una cadena  y devulve un entero sin validar.
		String DataInt = "";
		int send = 0;
		send = Integer.parseInt(getStr().trim());
		return send;
	}
}
