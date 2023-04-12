package br.com.sly.maildown;

import java.security.GeneralSecurityException;
import java.util.Properties;


import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.sun.mail.util.MailSSLSocketFactory;

import br.com.sly.maildown.WLog;


/*
 * Não esqueça de desligar o Antivirus !
 */
public class ManipularEmail {

    static Session session = null;

	
	/**
	 * Autenticacao e conexao com o Servidor de e-mail
	 * 
	 * @return
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 */
    
    
	/**
	 * to login to the mail host server
	 * @throws GeneralSecurityException 
	 */
	public static Store conectar()
			throws  Exception  {
		
		//System.out.println("Conectanto ao servidor");
		
		WLog.writeLogMailDown(Constantes.getAPELIDO(), 
				"Conectanto ao servidor [" + Constantes.getHOST() + "] Conta [" + Constantes.getLOGIN() + "]");
		
		//URLName url = new URLName("imaps", Constantes.getHOST(), 993, "INBOX", Constantes.getLOGIN(), Constantes.getSENHA());

		URLName url = new URLName(Constantes.getPROTOCOL(), Constantes.getHOST(), Constantes.getPORTA(), "INBOX", Constantes.getLOGIN(), Constantes.getSENHA());
		
		System.out.println(url);
		
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustedHosts(new String[] { "my-server"});
		sf.setTrustAllHosts(true); 
 

		
		if (session == null) {
			Properties props = null;
			try {
				props = System.getProperties();
				/*
				props.setProperty("mail.imap.ssl.enable", "true");
				props.put("mail.smtp.ssl.checkserveridentity", "true");
				props.put("mail.imap.ssl.trust", "*");
				props.put("mail.imap.ssl.socketFactory", sf);
				props.put("mail.smtps.socketFactory", sf);
				props.setProperty("mail.imaps.partialfetch", "false");
				props.setProperty("mail.mime.base64.ignoreerrors", "true");
				*/
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.host", Constantes.getHOST());
				props.put("mail.smtp.port", "587");
				
			} catch (SecurityException sex) {
				props = new Properties();
			}
			session = Session.getDefaultInstance(props, null);
			
		}
		Store store = session.getStore(url);
		store.connect();
		
		WLog.writeLogMailDown(Constantes.getAPELIDO(), 
				"Servidor Conectado     [" + Constantes.getHOST() + "] Conta [" + Constantes.getLOGIN() + "] - OK");
		
		return store;
		
		/*
		folder = store.getFolder(url);

		folder.open(Folder.READ_WRITE);
		*/
	}
	
	
	/**
	 * Acessa a Caixa de Entrada (Inbox)
	 * 
	 * @param store
	 * @return
	 * @throws MessagingException
	 */
	public static Folder recuperarCaixaEntrada(Store store) throws MessagingException {
		Folder folder;
		folder = store.getFolder(Constantes.getPASTA_PRINCIPAL());
		folder.open(Folder.READ_WRITE);
		return folder;
	}
	
	/**
	 * Acessa a Pasta Auxiliar
	 * 
	 * @param store
	 * @return
	 * @throws MessagingException
	 */
	public static Folder recuperarPastaAuxiliar(Store store) throws MessagingException {
		Folder folder;
		folder = store.getFolder(Constantes.getPASTA_BACKUP());
		folder.open(Folder.READ_WRITE);
		return folder;
	}
	
	/**
	 * @param messages
	 * @param i
	 * @throws MessagingException
	 */
	public static void excluirMensagemInbox(Message[] messages, int i)throws MessagingException {
		messages[i].setFlag(Flags.Flag.DELETED, true);
	}
}
