import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;
	protected String texto;
	protected int limite;
	protected final int maxFontSize=150;
	
	//signos en variables -no entiendo este comment

	public Generador(File texto, int limite) {
		this.text=archivoATexto(texto);
		this.limite=limite;
	}

	public String archivoATexto(File input) {
		//aqui se recibe el archivo y se regresa una string

	}

	public void iniciar(){ //no tiene argumentos porque todo está en el estado, es el mismo metodo para generador 1 y generador 2, entonces se queda en la clase padre
		llenarArreglo();
		determinarFontSizePorPalabra();
		//ahora iniciar UI y acomodar todas las labels donde deben ir
		Scene escena = crearNube();
		acomodarPalabras(escena);

	}

	public abstract void llenarArreglo(); //el unico metodo que se queda abstracto es llenarArreglo porque uno tiene lista negra y el otro no

	public void determinarFontSizePorPalabra() {
		//este metodo usa el arreglo palabras y basado en la frecuencia de cada palabra y la cantidad de palabras determina el tamaño de cada una, se manda llamar despues de iniciar el arreglo
		int cantidadPalabras=0;
		int frecuenciaMax=palabras[0].getFrecuencia();
		//el tamaño debe ser proporcional al uso de cada palabra, todo relativo a maxFontSize con regla de 3, el arreglo palabras debe estar ordenado de mayor a menor frecuencia
		for (Palabra palabra:palabras) {
			palabra.setFontSize(palabra.getFrecuencia()*maxFontSize/frecuenciaMax);
		}

	}

	public Scene crearNube() {
		//crear UI y mostrarala como siempre y regresar la escena
	}

	public void acomodarPalabras(Scene escena) {
		//gran metodo donde se va acomodando la label de cada palabra del arreglo, para esto se usa el metodo setCoordenadas(int x, int y) de cada palabra y se saca la info con getX(), getY(), getwidth() y getHeight()

	}

	public int funcionCirculo(int x) { //recibe un valor x  regresa el valor (positivo?) correspondiente de la funcion x^2 + y^2 = r^2

	}

}
