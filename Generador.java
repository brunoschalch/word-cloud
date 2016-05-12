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

public abstract class Generador implements Graficable{

	protected Palabra[] palabras;
	protected String texto;
	protected int limite;
	protected final int maxFontSize=150;
	private final int WIDTH = 1280;
	private final int HEIGHT = 1000;

	//signos en variables -no entiendo este comment

	public Generador(File texto, int limite) {
		this.texto=archivoATexto(texto);
		this.limite=limite;
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
		//ahora iniciar UI y acomodar todas las labels donde deben ir
		Scene escena = crearNube();
		acomodarPalabras(escena);
		return escena;
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
		//crear UI y mostrarala como siempre y regresar la escena con un grupo raiz vacio y ya
		Group root = new Group();
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.DEEPSKYBLUE); //cambiar después
		return scene;
	}

	public void acomodarPalabras(Scene escena) {
		for (Palabra palabra : palabras) {
			System.out.println("La palabra que se acomodara es "+palabra.getContenido()+", ancho: "+palabra.getWidth()+", alto: "+palabra.getHeight());
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
				x=x-palabra.getWidth()/2; //para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
				y=y-palabra.getHeight()/2;//para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
				boolean colision = checarColision(palabra, x, y);

				System.out.println("Tratando de acomodar "+ palabra.getContenido() + "en coordenadas "+x +","+y );
				if (!colision) {
					Label etiqueta = palabra.getLabel();
					//agregar etiqueta a escena

					//guardar coordenadas relativo a un centro de coordenadas 0|0
					palabra.setCoordenadas(x, y);
					etiqueta.setTranslateX(x+WIDTH/2);
					etiqueta.setTranslateY(y+HEIGHT/2);
					System.out.println("x es: " + x + " e y es: " +y);
					((Group)escena.getRoot()).getChildren().add(etiqueta);
					acomodado=true; //ya se acomodo
					System.out.println("SE ACOMODO "+ palabra.getContenido() + "en coordenadas "+x +","+y );

				}
			}
			//¿cuadrantes III y IV, probando todas las x entre 0 y |r|
			//solo tiene sentido intentar acomodarlo en cuadrantes III y IV si no se ha podido acomodar arriba
				for (int x = -r; x <= r && !acomodado; x++) {
					int y = -funcionCirculo(x, r);
					x=x-palabra.getWidth()/2; //para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
					y=y-palabra.getHeight()/2; //para centrar en el centro de la etiqueta y no en la esquina superior izqquerda
					boolean colision = checarColision(palabra, x, y);

					System.out.println("Tratando de acomodar "+ palabra.getContenido() + "en coordenadas "+x +","+y );
					if (!colision) {
						Label etiqueta = palabra.getLabel();
						//agregar etiqueta a escena

						//guardar coordenadas relativo a un centro de coordenadas 0|0 y en el centro de la palabra
						palabra.setCoordenadas(x, y);
						etiqueta.setTranslateX(x+WIDTH/2);
						etiqueta.setTranslateY(y+HEIGHT/2);
						((Group)escena.getRoot()).getChildren().add(etiqueta);
						acomodado=true; //ya se acomodo
						System.out.println("SE ACOMODO "+ palabra.getContenido() + "en coordenadas "+x +","+y );

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

		for (Palabra actual : palabras){
			if (actual.getX()!=-2147483648 && actual.getY()!=-2147483648) { //si no se ha acomodado la palabra actual (x o y == valor minimo de int), no tiene sentido checar colisiones
				//if (RectA.Left < RectB.Right && RectA.Right > RectB.Left && RectA.Top < RectB.Bottom && RectA.Bottom > RectB.Top )

				if (x-palabra.getWidth()/2 < actual.getX()+actual.getWidth()/2 && x+palabra.getWidth()/2 > actual.getX()-actual.getWidth()/2 &&
						y+palabra.getHeight()/2 < actual.getY()-actual.getHeight()/2 && y-palabra.getHeight()/2> actual.getY()+actual.getHeight()/2 ) {

					return true; //hubo colision
				}
			}
		}
		return false;

	}

	public int funcionCirculo(int x, int r) { //recibe un valor x regresa el valor positivo correspondiente de la funcion x^2 + y^2 = r^2
		return (int)Math.sqrt(r*r-x*x);
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
			for(int j=1; j > (n-i); j++){

				if(palabras[j-1].getFrecuencia() > palabras[j].getFrecuencia()){
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
