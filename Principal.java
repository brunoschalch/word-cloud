import java.io.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.*;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Principal extends Application implements EventHandler<ActionEvent>{

	private TextField palabrasContar;
	private Button buscarP, buscarList, generar;
	private Label contMas, contMenos;
	private File texto, listaNegra;

	public static void main(String[] args){
		launch(args);
	}

        MenuBar menuBar = new MenuBar();
        Menu menuFormato = new Menu("Formato");
        ColorPicker colorPickerFondo = new ColorPicker();
        MenuItem colorFondo = new MenuItem("Color Fondo",colorPickerFondo);
        ColorPicker colorPickerFuente = new ColorPicker();
        MenuItem colorFuente = new MenuItem("Color Fuente",colorPickerFuente);
        Pane root = new Pane();

	@Override
    	public void start(Stage primaryStage) {

		colorFondo.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            colorFondo();
                        }
                    });


                     colorFuente.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            colorFuente();
                        }
                    });
                     colorFondo.setGraphic(colorPickerFondo);
                     colorFuente.setGraphic(colorPickerFuente);
                     menuFormato.getItems().addAll(colorFuente,colorFondo);
                     menuBar.getMenus().addAll(menuFormato);
		
		Label nubeL, archivoPalabrasL, archivoListaL, numeroPalabrasL, respuestaP, respuestaL;

		Scene scene = new Scene(root, 550, 500, Color.WHITE);
		primaryStage.setScene(scene);

		nubeL = new Label("Nube");
		archivoPalabrasL = new Label("Archivo principal");
		archivoListaL = new Label("'Lista Negra'");
		numeroPalabrasL = new Label("Numero de palabras diferentes que se contaran");
		respuestaP = new Label();
		respuestaL = new Label();

		FileChooser fileChooser = new FileChooser();
		texto = null;
		listaNegra = null;

		buscarP = new Button("Buscar");
		buscarP.setOnAction(
			new EventHandler<ActionEvent>(){
        			@Override
                		public void handle(final ActionEvent e){
                    			texto = fileChooser.showOpenDialog(primaryStage);
                    			respuestaP.setText(texto.getName());
                    			respuestaP.setTextFill(Color.DARKGRAY);
				}
            		});

		buscarList = new Button("Buscar");
		buscarList.setOnAction(
			new EventHandler<ActionEvent>(){
	        		@Override
	                	public void handle(final ActionEvent e){
		                    	listaNegra = fileChooser.showOpenDialog(primaryStage);
		                    	respuestaL.setText(listaNegra.getName());
		                    	respuestaL.setTextFill(Color.DARKGRAY);
				}
            		});

		palabrasContar = new TextField();

		generar = new Button("Generar");
		generar.setOnAction(this);

		contMas = new Label();
		contMenos = new Label();

		nubeL.setFont(Font.font("Arial",32));
		archivoPalabrasL.setFont(Font.font("Arial",18));
		respuestaP.setFont(Font.font("Arial",18));
		archivoListaL.setFont(Font.font("Arial",18));
		respuestaL.setFont(Font.font("Arial",18));
		numeroPalabrasL.setFont(Font.font("Arial",18));
		contMas.setFont(Font.font("Arial",18));
		contMenos.setFont(Font.font("Arial",18));

		palabrasContar.setPrefWidth(50);

		nubeL.setTranslateX(235);
		nubeL.setTranslateY(30);
		archivoPalabrasL.setTranslateX(80);
		archivoPalabrasL.setTranslateY(100);
		buscarP.setTranslateX(270);
		buscarP.setTranslateY(100);
		respuestaP.setTranslateX(380);
		respuestaP.setTranslateY(100);
		archivoListaL.setTranslateX(80);
		archivoListaL.setTranslateY(180);
		buscarList.setTranslateX(270);
		buscarList.setTranslateY(180);
		respuestaL.setTranslateX(380);
		respuestaL.setTranslateY(180);
		numeroPalabrasL.setTranslateX(80);
		numeroPalabrasL.setTranslateY(260);
		palabrasContar.setTranslateX(250);
		palabrasContar.setTranslateY(295);
		generar.setTranslateX(240);
		generar.setTranslateY(355);
		contMas.setTranslateX(80);
		contMas.setTranslateY(420);
		contMenos.setTranslateX(80);
		contMenos.setTranslateY(460);

		root.getChildren().add(nubeL);
		root.getChildren().add(archivoPalabrasL);
		root.getChildren().add(buscarP);
		root.getChildren().add(respuestaP);
		root.getChildren().add(archivoListaL);
		root.getChildren().add(buscarList);
		root.getChildren().add(respuestaL);
		root.getChildren().add(numeroPalabrasL);
		root.getChildren().add(palabrasContar);
		root.getChildren().add(generar);
		root.getChildren().add(contMas);
		root.getChildren().add(contMenos);

  		primaryStage.show();
    }
    
    private void colorFondo() {
       root.setBackground(new Background(new BackgroundFill(colorPickerFondo.getValue(),null,null)));
    }


    private void colorFuente() {
       //label1.setTextFill(colorPickerFuente.getValue(),null,null); Aqu[i no se como llamar a las labels para cambiar su color
    }
    
    public void handle(ActionEvent e) {

    	int lim = 0;

    	try{
    		lim = Integer.parseInt(palabrasContar.getText());
    	}
    	catch(NumberFormatException nfe){
    		Alert alert1 = new Alert(AlertType.ERROR, "Por favor, ingresa un numero");
	    	alert1.showAndWait();
    	}

    	Generador g = null;

        //paso 1: crear objeto

    	if(texto!=null && texto.exists()){

        	if(listaNegra!=null && listaNegra.exists()){
        		g = new Generador2(texto, listaNegra, lim);
        	}
        	else{
        		g = new Generador1(texto, lim);
        	}
        }
        else{
        	//opciones
        	Alert alert2 = new Alert(AlertType.ERROR, "No se encontro tu archivo principal");
        	alert2.showAndWait();
        	throw new NullPointerException("No se encontro tu archivo principal");
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
