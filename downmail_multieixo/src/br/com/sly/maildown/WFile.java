package br.com.sly.maildown;

import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WFile extends File {

    public WFile (String pathName) {
    	super (pathName);
    }
	
	public WFile(File parent, String child) {
		super(parent, child);
	}

	public static String corrigePath (String pathFile) {

		// O replaceAll tem um bug ao substituir: replaceAll("/", "\\"),  String index out of range: 1
		
		String sRet = "";
		if (File.separator.equals("/") && pathFile.indexOf("\\") > -1) {
			for (int xx=0;xx<pathFile.length();xx++) {
				if (pathFile.substring(xx,xx+1).equals("\\"))
					sRet += "/";
				else
					sRet += pathFile.substring(xx,xx+1);
			}
		} else if (File.separator.equals("\\") && pathFile.indexOf("/")>-1) {
			for (int xx=0;xx<pathFile.length();xx++) {
				if (pathFile.substring(xx,xx+1).equals("/"))
					sRet += "\\";
				else
					sRet += pathFile.substring(xx,xx+1);
			}
		} else {
			sRet = pathFile;
		}
		 
		return sRet;
	}
	
	public static String getSeparator () {
		if (File.separator.equals("/"))
			return "/";
		return "\\"; /* Windows */
	}
	
	public static void copiar (String origem, String destino) throws Exception {
		copiarArquivo(origem, destino);
	}
	
	public static void criarArquivoFromString (String piNomeArquivo, String piConteudo) {
        FileWriter fw;
        
		try {
			fw = new FileWriter(piNomeArquivo);
	        fw.write(piConteudo);
	        fw.close();
		} catch (IOException e) {
		}
	}
	
	private static void copiarArquivo (String origem, String destino) throws Exception {

	    File inputFile = new File(corrigePath(origem));
	    File outputFile = new File(corrigePath(destino));

	    if (!inputFile.exists())
	    	throw new Exception("Arquivo de origem [" + origem + "] nao encontrado para copia [WFile].");

	    if (inputFile.isDirectory())
	    	throw new Exception("Arquivo de origem [" + origem + "] nao pode ser um diretorio [WFile].");
	    
	    if (outputFile.isDirectory())
	    	throw new Exception("Arquivo de destino [" + destino + "] nao pode ser um diretorio [WFile].");

	    FileReader in = new FileReader(inputFile);
	    FileWriter out = new FileWriter(outputFile);
	    int xx;

	    while ((xx = in.read()) != -1)
	      out.write(xx);

	    in.close();
	    out.close();		
	}

}
