import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Principal extends Application implements EventHandler<ActionEvent>{

	private TextField textoP, textoListaNegra, palabrasContar;
	private Button generar;
	private Label contMas, contMenos;

	public static void main(String[] args){
		launch(args);
	}

	@Override
    	public void start(Stage primaryStage) {

	Label wordCloudL, archivoPalabrasL, archivoListaL, numeroPalabrasL;

      	Group root = new Group();

      	Scene scene = new Scene(root, 1280, 1000, Color.WHITE);
      	primaryStage.setScene(scene);

      	wordCloudL = new Label("Nube");
	archivoPalabrasL = new Label("Nombre del archivo principal");
	archivoListaL = new Label("Nombre del archivo de las palabras que se ignoraran");
	numeroPalabrasL = new Label("Numero de palabras diferentes que se contaran");

	textoP = new TextField();
	textoListaNegra = new TextField();
	palabrasContar = new TextField();

	generar = new Button("Generar");
	generar.setOnAction(this);

	contMas = new Label("La palabra que mas se conto fue: "); //cambiar
	contMenos = new Label("La que menos se conto fue: ");
	wordCloudL.setFont(Font.font("Arial",32));
	archivoPalabrasL.setFont(Font.font("Arial",18));
	archivoListaL.setFont(Font.font("Arial",18));
	numeroPalabrasL.setFont(Font.font("Arial",18));
	contMas.setFont(Font.font("Arial",18));
	contMenos.setFont(Font.font("Arial",18));

	textoP.setPrefWidth(150);
	textoListaNegra.setPrefWidth(150);
	palabrasContar.setPrefWidth(50);

	wordCloudL.setTranslateX(600);
	wordCloudL.setTranslateY(30);
	archivoPalabrasL.setTranslateX(100);
	archivoPalabrasL.setTranslateY(100);
	textoP.setTranslateX(100);
	textoP.setTranslateY(130);
	archivoListaL.setTranslateX(100);
	archivoListaL.setTranslateY(180);
	textoListaNegra.setTranslateX(100);
	textoListaNegra.setTranslateY(210);
	numeroPalabrasL.setTranslateX(100);
	numeroPalabrasL.setTranslateY(260);
	palabrasContar.setTranslateX(100);
	palabrasContar.setTranslateY(290);
	generar.setTranslateX(230);
	generar.setTranslateY(350);
	contMas.setTranslateX(100);
	contMas.setTranslateY(520);
	contMenos.setTranslateX(100);
	contMenos.setTranslateY(580);

	root.getChildren().add(wordCloudL);
	root.getChildren().add(archivoPalabrasL);
	root.getChildren().add(textoP);
	root.getChildren().add(archivoListaL);
	root.getChildren().add(textoListaNegra);
	root.getChildren().add(numeroPalabrasL);
	root.getChildren().add(palabrasContar);
	root.getChildren().add(generar);
	root.getChildren().add(contMas);
	root.getChildren().add(contMenos);

  	primaryStage.show();
    }

    public void handle(ActionEvent e) {

	String nombreP = textoP.getText()+".txt";
    	String nombreLN = textoListaNegra.getText()+".txt";
    	int lim = Integer.parseInt(palabrasContar.getText()); //checar si es un número con exceptions (pendiente)
    	File texto = new File(nombreP); //? try y catch? o no son necesarios?
    	File listaNegra = new File(nombreLN);
    	Generador g = null;
        //paso 1: crear objeto
    	if(listaNegra.exists()){ //?
    		g = new Generador1(texto, lim);
    	}
    	else{
    		g = new Generador2(texto, listaNegra, lim);
    	}





        //paso 2: recibir escena con nube y mostrar en una nueva ventana
        Stage stage = new Stage();
        stage.setTitle("LA NUBE");
        Scene contenido = g.iniciar();
        stage.setScene(contenido);
        stage.show();


        Palabra[] p = g.getPalabras();
         contMas.setText("La palabra que mas se conto fue: "+p[0].getContenido());
    	contMenos.setText("La que menos se conto fue: "+p[(p.length-1)].getContenido());

    	try{
		BufferedWriter writer = new BufferedWriter(new FileWriter("cuentas.txt"));
		for(int i=0; i<p.length; i++){
			writer.write(p[i].getContenido()+" "+p[i].getFrecuencia()+"/n");
		}
		writer.close();
	}
	catch(IOException ioe){
		ioe.printStackTrace();
	}

    }
}
