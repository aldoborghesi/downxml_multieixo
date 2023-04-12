package br.com.sly.maildown;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class WLog  {
	
	private static PrintStream ps          = null;
	private static String      instanciou  = null;

	public static String writeLog (String classe, String msgLog) {
		inicializaSaida ();
		System.out.println(  
				"[LOG]" +
			    new WDate().getCurrentDateStr() + " - " + msgLog + " [" + classe + "]");
		return msgLog + " [" + new WDate().getCurrentDateStr() + "]-[" + classe + "]\n";
	}

	public static String writeLogErro (String classe, String msgErro) {
		inicializaSaida ();
		System.err.println(  
				"[ERR]" +
			    new WDate().getCurrentDateStr() + " - " +  msgErro + " [" + classe + "]"	);
		return msgErro + " [" + new WDate().getCurrentDateStr() + "]-[" + classe + "]\n";
	}
	
	public static String writeLogMailDown (String classe, String msgLog) {
		inicializaSaida ();
		System.out.println("[MAIL_LOG]-" + new WDate().getCurrentDateStr() + " - " + msgLog);
		return msgLog + " [" + new WDate().getCurrentDateStr() + "]-[" + classe + "]\n";
	}

	public static String writeLogErroMailDown (String classe, String msgErro) {
		inicializaSaida ();
		System.err.println("[MAIL_ERR]-" +  new WDate().getCurrentDateStr() + " - " +  msgErro);
		return msgErro + " [" + new WDate().getCurrentDateStr() + "]-[" + classe + "]\n";
	}
	
    private static void inicializaSaida () {
 
    		
   		    String fileLog = Constantes.getARQLOG();    	
    		if (fileLog != null && fileLog.length()>0) {
				try {
			        FileOutputStream outStr = new FileOutputStream(fileLog, true);
			        ps         = new PrintStream(outStr);
			        instanciou = ps.toString();
		    		System.setOut(ps);
		    		System.setErr(ps);
		    		
		    		System.out.println("");
		    		System.out.println("");
				} catch (FileNotFoundException e) {
					System.err.println("IDO-NFE, falha na tentativa de iniciar o log da aplicacao. " + (e!=null&&e.getMessage()!=null?e.getMessage():""));
				}
    		} else {
				System.out.println("IDO-NFE, utilizando o log do Catalina.");
    			instanciou = new String();
    		}
    	}    
}
