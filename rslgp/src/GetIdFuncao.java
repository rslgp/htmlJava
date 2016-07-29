package rslgp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import org.jsoup.Jsoup;

public class GetIdFuncao {
	private static String textoidFuncao="";
	
	private static String[] arrayNomeCargo;
	final static String espacamento="		",breakLine="\r\n";
	
    private static void loadProperties(){
    	Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("./configOld.properties"));
			
		} catch (IOException e) {e.printStackTrace();}

		arrayNomeCargo = prop.getProperty("arrayNomeCargo").split(",");
    }
	
	private static void printarTexto(String text, String arquivo) throws Exception{
		try(  PrintWriter out = new PrintWriter( arquivo )  ){
		    out.println( text );
		}
	}
    
    private static String get(String urlStr, String nomeCargo) throws IOException {    	
        InputStream is =  new URL(urlStr).openStream();
        byte[] buf = new byte[1024*64];
        String htmlContent="";
        
        while((is.read(buf))!=-1) {
        	htmlContent+= new String(buf, "UTF-8");
        }
        is.close();
        return nomeCargo+espacamento+getIdFuncaoNumber(htmlContent)+breakLine;
    }
    
    public static String getIdFuncaoNumber(String html) throws IOException{
    	String retorno;
    	try{
    	retorno = Jsoup.parse(html).select("input[id=idFuncao]").first().val();
    	}catch(Exception e){
    		return "null";
    	}
		return retorno;
    }
    
    public static void workThread(int indice, int indiceFinal, int numeroThread) throws Exception{
    	String retorno="";
//    	try{
		for(;indice<=indiceFinal;indice++){
			retorno += get("-linkprivado-/PesquisaSalarialPorPorte?funcao="+arrayNomeCargo[indice], arrayNomeCargo[indice]);
		}
//    	}catch(Exception e){
//    		System.out.println(numeroThread + " "+ indiceFinal+" "+indice);
//    	}
		
		printarTexto(retorno,numeroThread+"IdFuncao.txt");
		System.out.println("end "+numeroThread);
    }
    
    public static Thread createThread(int indiceInicial, int indiceFinal, int numeroThread){
    	return new Thread(new Runnable() {
   	     public void run() {
   	          // code goes here.
   	    	 try {
				workThread(indiceInicial, indiceFinal, numeroThread);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	     }
    	});
    }
    
    public static void main(String[] args) {
    	loadProperties();
		try {
			workThread(0, 101, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//    public static void main(String[] args) {
//		loadProperties();
//    	final int tamanhoMax = arrayNomeCargo.length;
//    	Thread [] threads = new Thread[71];
//    	int indiceInicial=0, indiceFinal=0, numeroThread=1;
////    	for(Thread thread : threads){,
//    	for(int i=0, tamanhoThread=threads.length; i<tamanhoThread;i++){  		
//    		threads[i]=createThread(indiceInicial+=101, indiceInicial+100, numeroThread++);
//    	}
//
//    	Thread threadFinal = createThread(7171, 7245, numeroThread++);
//    	
//    	for(int i=0, tamanhoThread=threads.length; i<tamanhoThread;i++){
//    		threads[i].start();
//    	}
//    	threadFinal.start();
//	}
    
//    public static void main(String[] arg) {
//		loadProperties();
//    	try {
//    		for(String nomeCargo : arrayNomeCargo){
//    			get("-linkprivado-/PesquisaSalarialPorPorte?funcao="+nomeCargo, nomeCargo);
//    		}
////			get("-linkprivado-/PesquisaSalarialPorPorte?funcao=Abatedor");
////			get("-linkprivado-/PesquisaSalarialPorPorte?funcao=Acafelador");
//			printarTexto(textoidFuncao,"filename.txt");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
}
