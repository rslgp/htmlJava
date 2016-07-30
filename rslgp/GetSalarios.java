package rslgp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetSalarios {
	
	static String[] nomeFuncoes, idFuncoes;
	final static String espacamento="		",breakLine="\r\n";
	
    private static void loadProperties(){
    	Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./src/rslgp/config.properties"));
			
		} catch (IOException e) {e.printStackTrace();}


		idFuncoes = prop.getProperty("idFuncoes").split(",");

		nomeFuncoes = prop.getProperty("nomeFuncoes").split(",");
    }

	private static void printarTexto(String text, String arquivo) throws Exception{
		try(  PrintWriter out = new PrintWriter( arquivo )  ){
		    out.println( text );
		}
	}

    private static String get(String urlStr, String nomeCargo) throws Exception {    	
        InputStream is =  new URL(urlStr).openStream();
        byte[] buf = new byte[1024*64];
        String htmlContent="";
        
        while((is.read(buf))!=-1) {
        	htmlContent+= new String(buf, "UTF-8");
        }
        is.close();
        int[] MinMax= getMinMaxJson(htmlContent);
        return nomeCargo+espacamento+MinMax[0]+espacamento+MinMax[1]+breakLine;
    }
    

    public static void workThread(int indice, int indiceFinal, int numeroThread) throws Exception{
    	String retorno="";
		for(;indice<=indiceFinal;indice++){
			retorno += get("-linkprivado-/RelatorioSalarialDeMercado/GerarGraficoPesquisaSalarialPorte?salario=0&nivel=0&funcao="+idFuncoes[indice],nomeFuncoes[indice]);
		}
		printarTexto(retorno,numeroThread+"salario.txt");
		System.out.println("end "+numeroThread);
    }
    
    public static Thread createThread(int indiceInicial, int indiceFinal, int numeroThread){
    	return new Thread(new Runnable() {
			public void run() {
				 try {
					workThread(indiceInicial, indiceFinal, numeroThread);				
				} catch (Exception e) {e.printStackTrace();}
			}   	     
    	});
    }
    
    public static int[] getMinMaxJson(String jsonString) throws JSONException{
    	int[] MinMax = new int[2];
    	int[] valores = new int[15];
    	JSONArray recs= new JSONArray(jsonString);
    	JSONObject rec;
		for (int i = 0,j=0, tamanhoJson=recs.length(); i < tamanhoJson; ++i,j+=5) {
	    	rec = recs.getJSONObject(i).getJSONObject("ValoresMedios");//i==0 pequena empresa
	    	
	    	valores[j] = rec.getInt("Trainee");
	    	valores[j+1] = rec.getInt("Junior");
	    	valores[j+2] = rec.getInt("Pleno");
	    	valores[j+3] = rec.getInt("Senior");
	    	valores[j+4] = rec.getInt("Master");
	    	
		}
		
		 Arrays.sort(valores);
		 MinMax[0]= valores[0];
		 MinMax[1]= valores[valores.length - 1];
		 
		 return MinMax;
    	
    }
    
	public static void main(String[] args) {
		loadProperties();	

    	Thread [] threads = new Thread[71];
    	int indiceInicial=0, numeroThread=1,tamanhoThread=threads.length-1;
    	
    	for(int i=0; i<tamanhoThread;i++){ 
    		threads[i]=createThread(indiceInicial+=101, indiceInicial+100, numeroThread++);
    	}
    	
//    	System.out.println(indiceInicial-101+" "+(indiceInicial-1)+" "+(idFuncoes.length-1));
    	Thread threadFinal = createThread(7070, idFuncoes.length-1, numeroThread++);
    	
    	for(int i=0; i<tamanhoThread;i++){
    		threads[i].start();
    	}
    	threadFinal.start();
	}
}
