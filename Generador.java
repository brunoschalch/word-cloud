import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.nio.file.Files;
import javafx.scene.shape.*;

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;
	protected String texto;
	protected int limite;
	protected final int maxFontSize=220;
	protected final int minFontSize=28;
	protected Color colorPalabra;
	protected Color colorFondo;
	private final int WIDTH = 1280;
	private final int HEIGHT = 1000;


	//signos en variables -no entiendo este comment

	public Generador(File texto, int limite, Color colorPalabra, Color colorFondo) {
		this.texto=archivoATexto(texto);
		this.limite=limite;
		this.colorPalabra=colorPalabra;
		this.colorFondo=colorFondo;
	}

	public String archivoATexto(File input) {
		//aqui se recibe el archivo y se regresa una string

		try {
			return new String(Files.readAllBytes(input.toPath()));

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}



	public Scene iniciar(){ //no tiene argumentos porque todo está en el estado, es el mismo metodo para generador 1 y generador 2, entonces se queda en la clase padre
		llenarArreglo();
		ordenarArreglo();
		recortarArreglo();
		determinarFontSizePorPalabra();
		determinarTonoPorPalabra();
		//ahora iniciar UI y acomodar todas las labels donde deben ir
		Scene escena = crearNube();
		acomodarPalabras(escena);
		return escena;
	}

	public abstract void llenarArreglo(); //el unico metodo que se queda abstracto es llenarArreglo porque uno tiene lista negra y el otro no

	public void determinarFontSizePorPalabra() {
		//este metodo usa el arreglo palabras y basado en la frecuencia de cada palabra y la cantidad de palabras determina el tamaño de cada una, se manda llamar despues de iniciar el arreglo

		int frecuenciaMax=palabras[0].getFrecuencia();
		//el tamaño debe ser proporcional al uso de cada palabra, todo relativo a maxFontSize con regla de 3, el arreglo palabras debe estar ordenado de mayor a menor frecuencia
		for (int i=0; i<palabras.length ; i++) {

			int fontSize = minFontSize + ((maxFontSize - minFontSize)/(frecuenciaMax - 1))*(palabras[i].getFrecuencia() - 1);
			//if (fontSize<15) fontSize=15;
			palabras[i].setFontSize(fontSize);
			//si la palabra está en el 20% mas usada sera maysuculas
			float floati = ((float) i);
			float largo = (float) palabras.length;
			if (floati/largo<=0.2) {
				palabras[i].labelAMayus();
			}

		}

	}

	public void determinarTonoPorPalabra() {
		int cantidadPalabras=palabras.length;

		//experimental
		for (int i=0; i<cantidadPalabras; i++) {
			//int tonoGris = ((250)/(cantidadPalabras))*(i);
			//y = y1 + ((y2 - y1)/(x2 - x1))*(x - x1) para i=0(x1) transparencia= 1 (y1) , para i=cantidadPalabras-1 (x2) transparencia= transMin(y2)
			float cant= (float) cantidadPalabras;
			float index = (float) i;
			float transpMin = 0.05f;
			float transparencia = 1 + ((transpMin - 1)/(cant-1))*(index);
			int R = (int) Math.round(colorPalabra.getRed()*255);
			int G = (int)Math.round(colorPalabra.getGreen()*255);
			int B = (int)Math.round(colorPalabra.getBlue()*255);
			palabras[i].getLabel().setTextFill( Color.rgb(R, G, B, transparencia));
		}
	}

	public Scene crearNube() {
		//crear UI y mostrarala como siempre y regresar la escena con un grupo raiz vacio y ya
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT, colorFondo); //cambiar después
		return scene;
	}

	public void acomodarPalabras(Scene escena) {
		for (Palabra palabra : palabras) {
		//debugging	System.out.println("La palabra que se acomodara es "+palabra.getContenido()+", ancho: "+palabra.getWidth()+", alto: "+palabra.getHeight());
			acomodarPalabra(palabra, escena);
		}
	}

	public void acomodarPalabra(Palabra palabra, Scene escena) {

		//checar colosiones primero para y positivos despues para negativos (cuadrantes II (-+), I (++), III(--), IV(+-)), iterar para cada radio del circulo
		boolean acomodado=false;
		//iterador de radio
		for (int r=0; !acomodado; r++) {

			//empezar con cuadrantes II y I, probando todas las x entre 0 y |r|
			for (int x=-r ; x<=r && !acomodado; x++) {

				int y = funcionCirculo(x,r);
				int coordX=x-palabra.getWidth()/2; //para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
				int coordY=y-palabra.getHeight()/2;//para centrar en el centro de la etiqueta y no en la esquina superior izqquerda

				boolean colision = checarColision(palabra, coordX, coordY);

			//	System.out.println("Tratando de acomodar "+ palabra.getContenido() + "en coordenadas "+coordX +","+coordY );
				if (!colision) {
					Label etiqueta = palabra.getLabel();
					//agregar etiqueta a escena

					//guardar coordenadas relativo a un centro de coordenadas 0|0
					palabra.setCoordenadas(coordX, coordY);
					etiqueta.setTranslateX(coordX+WIDTH/2);
					etiqueta.setTranslateY(coordY+HEIGHT/2);

					/*			//		usado para debuggear
						Rectangle rect1 = RectangleBuilder.create()
								.x(coordX+WIDTH/2)
								.y(coordY+HEIGHT/2)
								.width(palabra.getWidth())
								.height(palabra.getHeight())
								.build();
						((Group)escena.getRoot()).getChildren().add(rect1);
*/

					((Group)escena.getRoot()).getChildren().add(etiqueta);
					acomodado=true; //ya se acomodo
				//debugging	System.out.println("SE ACOMODO "+ palabra.getContenido() + "en coordenadas "+coordX +","+coordY );

				}
			}
			//¿cuadrantes III y IV, probando todas las x entre 0 y |r|
			//solo tiene sentido intentar acomodarlo en cuadrantes III y IV si no se ha podido acomodar arriba
				for (int x = -r; x <= r && !acomodado; x++) {

					int y = -funcionCirculo(x,r);
					int coordX=x-palabra.getWidth()/2; //para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
					int coordY=y-palabra.getHeight()/2;//para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
					boolean colision = checarColision(palabra, coordX, coordY);

				//	System.out.println("Tratando de acomodar "+ palabra.getContenido() + "en coordenadas "+coordX +","+coordY );
					if (!colision) {
						Label etiqueta = palabra.getLabel();
						//agregar etiqueta a escena
						//guardar coordenadas relativo a un centro de coordenadas 0|0 y en el centro de la palabra
						palabra.setCoordenadas(coordX, coordY);
						etiqueta.setTranslateX(coordX+WIDTH/2);
						etiqueta.setTranslateY(coordY+HEIGHT/2);
			/*			//		usado para debuggear
						Rectangle rect1 = RectangleBuilder.create()
								.x(coordX+WIDTH/2)
								.y(coordY+HEIGHT/2)
								.width(palabra.getWidth())
								.height(palabra.getHeight())
								.build();
						((Group)escena.getRoot()).getChildren().add(rect1);
*/


						((Group)escena.getRoot()).getChildren().add(etiqueta);

						acomodado=true; //ya se acomodo
				//debugging		System.out.println("SE ACOMODO "+ palabra.getContenido() + "en coordenadas "+coordX +","+coordY );

					}
				}



		}
	}

	public boolean checarColision(Palabra palabra, int x, int y) {
		//checar si no hay una superposicion de la palabra actual y alguna otra palabra en la posicion deseada
	//	int w = palabra.getWidth();
	//	int h = palabra.getHeight();
		//importante, el centro de coordenadas de las labels es la esquina de arriba izquierda, tomar en cuenta
		//la colision ocurre cuando el rectangulo de la palabra actual coincide con el de alguna palabra, es decir, cuando el rango de x && de y de ambos rectangulos coincide para una o mas combinaciones de x && y
		int aIzq= x;
		int aDer=x+(palabra.getWidth()); //CREO que aaqui esta el problema somehow
		int aAbajo= y;
		int aArriba= y+(palabra.getHeight());


		for (Palabra actual : palabras){
			int bIzq= actual.getX();
			int bDer= actual.getX()+actual.getWidth();
			int bAbajo= actual.getY();
			int bArriba= actual.getY()+actual.getHeight();

			if (actual.getX()!=-2147483648 && actual.getY()!=-2147483648) { //si no se ha acomodado la palabra actual (x o y == valor minimo de int), no tiene sentido checar colisiones
				if (!(aDer < bIzq || aIzq > bDer || aArriba < bAbajo || aAbajo > bArriba)) { //rectangulo b tiene que estar arriba o abajo o a la derecha o a la izquiera de rectangulo b. si no esta ahi es sobreposicion
					return true; //hubo colision
				}
			}
		}
		//debugging System.out.println("En colosion, ancho:"+palabra.getWidth()+" alto: "+palabra.getHeight());

		return false;
	}







	public int funcionCirculo(int x, int r) { //recibe un valor x regresa el valor positivo correspondiente de la funcion x^2 + y^2 = r^2
		return (int) Math.round(Math.sqrt(r*r-x*x));
	}

	public void agregarPalabra(String nuevaPalabra) {
		Label etiqueta = new Label(nuevaPalabra);
		palabras  = Arrays.copyOf(palabras, palabras.length + 1);
		palabras[palabras.length - 1] = new Palabra(nuevaPalabra, etiqueta);


	}

	public void ordenarArreglo() {
          // bubble sort! (ineficiente pero facil de programar)
		int n = palabras.length;
		Palabra temp;

		for(int i=0; i < n; i++){
			for(int j=1; j < (n-i); j++){

				if(palabras[j-1].getFrecuencia() < palabras[j].getFrecuencia()){
					//hacer el cambio
					temp = palabras[j-1];
					palabras[j-1] = palabras[j];
					palabras[j] = temp;
				}

			}
		}

	}

	public void recortarArreglo() { //reducir el arreglo según el limite, dejando las palabras más usadas
		if (palabras.length>limite) {
			//recortar arreglo
			palabras  = Arrays.copyOf(palabras, limite);
		}
	}

	public Palabra[] getPalabras(){ return palabras;}

}
