package rslgp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class Text {

	private static void readInFolder(String pasta, String arquivo) throws Exception{
		File folder = new File(pasta);
		File[] listOfFiles = folder.listFiles();
		String content="";

		for (int i = 0; i < listOfFiles.length; i++) {
		  File file = listOfFiles[i];
		  if (file.isFile() && file.getName().endsWith(".txt")) {
		    content += FileUtils.readFileToString(file);
		    /* do somthing with content */
		  } 
		}
		
		printarTexto(content, arquivo);
	}
	
	private static void printarTexto(String text, String arquivo) throws Exception{
		try(  PrintWriter out = new PrintWriter( arquivo )  ){
		    out.println( text );
		}
	}
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public static void main(String[] args) {
		try {
			readInFolder("C:\\cargoSalario","resultado.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//public static void main(String[] args) {
//	try {
//		printarTexto(readFile("C:\\cargoSalario", StandardCharsets.UTF_8), "cargoSalario.txt");
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//public static void main(String[] args) throws IOException {
//    String content="";
//    for(int i=1;i<=72;i++){
//        content+= new String(Files.readAllBytes(Paths.get("C:\\out\\"+i+"IdFuncao.txt")));
//    	
//    }
//    try {
//		printarTexto(content, "out.txt");
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//}
//	public static void main(String[] args) {
//		try {
//			String content = new String(Files.readAllBytes(Paths.get("C:\\out\\out.txt")));
//			String[] contentArray= content.split("\r\n");
//			String resultado="";
//			for(int i=0,tamanho=contentArray.length;i<tamanho;i+=2){
//				if(!contentArray[i].equalsIgnoreCase("")){
//					resultado+=contentArray[i]+"		"+contentArray[i+1]+"\r\n";
//				}else{
//					do{
//						i++;
//					}while(contentArray[i].equalsIgnoreCase(""));
//				}
//			};
//			try {
//				printarTexto(resultado, "resultado.txt");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
