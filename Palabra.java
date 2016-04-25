public class Palabra {

	final private String valor;
	private int x;
	private int y;
	private int w;
	private int h;


	public Palabra(String valor, int x, int y, int w, int h) {
		this.valor = valor;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}

	public int frecuencia(File archivo){
		
		int cont = 1;
		
		try{
			FileReader fileReader = new FileReader(archivo);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			
			while((line = reader.readLine()) != null){
			
				String[] p = line.split(" "); //separa palabra por palabra y las guarda en el arreglo palabras
			
				for(int i=0; i<p.length; i++){
				
					if(valor.equals(p[i]))
						cont++;
				}
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return cont;
	}

}


}
