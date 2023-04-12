package br.com.sly.maildown;
/**
 * 
 */


/**
 * 
 *
 */
public class Constantes {
	 
	private static String emitenteCNPJ 	   = "";
	private static String PASTA_XML  	   = "/u/siglo/spool/xml";
	private static String PROTOCOL	       = "imap";
	private static String HOST			   = "imap.emailexchangeonline.com";
	private static int    PORTA			   = 143;
	private static String ARQUIVO_MSG	   = "Inbox";
	private static String LOGIN			   = "nfe@multieixo.com";
	private static String SENHA			   = "Ancd2020@";
	private static String PASTA_PRINCIPAL  = "Inbox";
	private static String PASTA_BACKUP     = "backup";
	private static String INTERVALO        = "30"; // segundos
	private static String APELIDO          = "DOWN_NFE_MULTIEIXO"; // segundos
	private static String ARQLOG           = "/u/siglo/confignfe/log/downemailnfe.log";
 
	 
	
	
	public static String getEmitenteCNPJ() {
		return emitenteCNPJ;
	}
	public static void setEmitenteCNPJ(String emitenteCNPJ) {
		Constantes.emitenteCNPJ = emitenteCNPJ;
	}
	public static String getPASTA_XML() {
		return PASTA_XML;
	}
	public static String getAPELIDO() {
		return APELIDO;
	}
	public static void setAPELIDO(String aPELIDO) {
		APELIDO = aPELIDO;
	}
	public static String getINTERVALO() {
		return INTERVALO;
	}
	public static void setINTERVALO(String iNTERVALO) {
		INTERVALO = iNTERVALO;
	}
	public static void setPASTA_XML(String pASTA_XML) {
		PASTA_XML = pASTA_XML;
	}
	public static String getPROTOCOL() {
		return PROTOCOL;
	}
	public static void setPROTOCOL(String protocol) {
		PROTOCOL = protocol;
	}
	public static String getHOST() {
		return HOST;
	}
	public static void setHOST(String hOST) {
		HOST = hOST;
	}
	public static int getPORTA() {
		return PORTA;
	}
	public static void setPORTA(int pORTA) {
		PORTA = pORTA;
	}
	public static String getARQUIVO_MSG() {
		return ARQUIVO_MSG;
	}
	public static void setARQUIVO_MSG(String aRQUIVO_MSG) {
		ARQUIVO_MSG = aRQUIVO_MSG;
	}
	public static String getLOGIN() {
		return LOGIN;
	}
	public static void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}
	public static String getSENHA() {
		return SENHA;
	}
	public static void setSENHA(String sENHA) {
		SENHA = sENHA;
	}
	public static String getPASTA_PRINCIPAL() {
		return PASTA_PRINCIPAL;
	}
	public static void setPASTA_PRINCIPAL(String pASTA_PRINCIPAL) {
		PASTA_PRINCIPAL = pASTA_PRINCIPAL;
	}
	public static String getPASTA_BACKUP() {
		return PASTA_BACKUP;
	}
	public static void setPASTA_BACKUP(String pASTA_BACKUP) {
		PASTA_BACKUP = pASTA_BACKUP;
	}
	public static String getARQLOG() {
		return ARQLOG;
	}
	public static void setARQLOG(String aRQLOG) {
		ARQLOG = aRQLOG;
	}
	
}
