import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Principal extends Application implements EventHandler<ActionEvent>{

	private GridPane pane;

	public static void main(String[] args){
		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {

        //dos archivos: principal y lista negra

        //Se le debe preguntar al usuario el máximo de palabras diferentes que el generador debe contar.

        //http://static.mrfeinberg.com/bv_ch03.pdf


      Label wordCloudL, archivoPalabrasL, archivoListaL, numeroPalabrasL;

      Group root = new Group();

      Scene scene = new Scene(root, 1280, 1000, Color.DEEPSKYBLUE); //cambiar después
      primaryStage.setScene(scene);

      pane = new GridPane();

      wordCloudL = new Label("Nube");
      archivoPalabrasL = new Label("Archivo principal");
      archivoListaL = new Label("Archivo de palabras que se ignoraran");
      numeroPalabrasL = new Label("Numero de palabras diferentes que se contaran"); //throw exception

  		pane.add(archivoPalabrasL,0,1);
  		pane.add(archivoListaL,1,1);
  		pane.add(numeroPalabrasL,2,1);

		  pane.setTranslateX(30);
  		pane.setTranslateY(60);

      root.getChildren().add(pane);

  		primaryStage.show();
    }

    public void handle(ActionEvent e) {

		    //al presionar el botón

        //paso 1: crear objeto

        //paso 3: recibir escena con nube y mostrar en una nueva ventana
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();


		    //Las cuentas de palabras deberán poderse guardar en un archivo
		    //deberán poder indicar qué palabra fue la que más se contó y la que menos se contó.

    }
}
