package br.com.sly.maildown;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Store;
import javax.mail.StoreClosedException;

import com.sun.mail.util.BASE64DecoderStream;

/**
 * @author Eduardo Bregaida
 * 
 */
public class ReadEmails {

	private String FILE_NFE_XML = "-NFE.xml";

	private Store store = null;
	private Folder folder = null;
	private Message message = null;
	private Message[] messages = null;
	private Object msgObj = null;
	private Multipart multipart = null;
	private Part part = null;
	
	public ReadEmails() {
	}
 	
	public void baixar () throws Exception {
		
	
		getConstantes();
		FILE_NFE_XML = Constantes.getAPELIDO() + ".xml";
		
		store  = ManipularEmail.conectar();
		folder = ManipularEmail.recuperarCaixaEntrada(store);
		messages = folder.getMessages();
		
		processMail();
		
		/* 
		Constantes.setLOGIN("nfeberkeley@gmail.com");
		Constantes.setAPELIDO("BERKELEY");
		Constantes.setSENHA("Boomera123");
		 
		getConstantes();
		FILE_NFE_XML = Constantes.getAPELIDO() + ".xml";
		
		store  = ManipularEmail.conectar();
		folder = ManipularEmail.recuperarCaixaEntrada(store);
		messages = folder.getMessages();
		
		processMail();
		
		Constantes.setLOGIN("nfecambe@boomera.com.br");
		Constantes.setAPELIDO("BOOMERA");
		Constantes.setSENHA("Boomera123");
		 
		getConstantes();
		FILE_NFE_XML = Constantes.getAPELIDO() + ".xml";
		
		store  = ManipularEmail.conectar();
		folder = ManipularEmail.recuperarCaixaEntrada(store);
		messages = folder.getMessages();
		
		processMail();		
		
		Constantes.setLOGIN("nfe.sp@boomera.com.br");
		Constantes.setAPELIDO("BOOMERA SP");
		Constantes.setSENHA("BoomeraSP");
		
		WLog.writeLogMailDown(Constantes.getAPELIDO(), " Antes Senha: " + Constantes.getSENHA() );
		
		getConstantes();
		
		WLog.writeLogMailDown(Constantes.getAPELIDO(), " Depois : " + Constantes.getSENHA() );
		
		FILE_NFE_XML = Constantes.getAPELIDO() + ".xml";
		
		store  = ManipularEmail.conectar();
		folder = ManipularEmail.recuperarCaixaEntrada(store);
		messages = folder.getMessages();
		
		processMail();
		*/		
	 
	}

	public void getConstantes() {
		
		Constantes.setEmitenteCNPJ("");
		Constantes.setARQUIVO_MSG("");
		Constantes.getHOST();
		Constantes.getPROTOCOL();
		Constantes.getPASTA_BACKUP();
		Constantes.getPASTA_PRINCIPAL();
		Constantes.getPASTA_XML();
		Constantes.getPASTA_XML();
		Constantes.getPORTA();
		Constantes.getSENHA();
		Constantes.getINTERVALO();
		
	}
	/**
	 * Processa o e-mail
	 * 
	 */
	public void processMail() throws MessagingException {	
		
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
		
		try {
						
			WLog.writeLogMailDown(Constantes.getAPELIDO(), 
					"E-Mails na Caixa [" + messages.length + "]");
			
			WLog.writeLogMailDown(Constantes.getAPELIDO(), " Tamanho da Caixa: " +messages.length );
			
			for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
				
				boolean baixou = false;
				WLog.writeLogMailDown(Constantes.getAPELIDO(), 
						"Baixando E-Mail [" + (messageNumber + 1) + "]");
				
				/*
				if (Constantes.getINTERVALO().equals("61") && messageNumber == 2) // Teste
					break;
	            */			
				message = messages[messageNumber];
				msgObj = message.getContent();
				
				// Determine o tipo de email
				if (msgObj instanceof Multipart) {
					multipart = (Multipart) message.getContent();
 
						
					 for (int i = 0; i < multipart.getCount(); i++)  {

						part = multipart.getBodyPart(i);
						// pegando um tipo do conteudo
						String contentType = part.getContentType();
						
						//String fileName = part.getFileName();
						String fileName = multipart.getBodyPart(i).getFileName();
						
						
						WLog.writeLogMailDown(Constantes.getAPELIDO(), " Tipo Arquivo: " + multipart.getBodyPart(i).getFileName() );
						
						/*Caso for um aqruivo zip, joga para a pasta o arquivo
						 * recupera o arquivo que esta compactado e por fim deleta o zip
						 */
						if (fileName != null && StringUtil.validaContemStringZIP(fileName)){
							ZipUtil.salvarArquivoZip(part, fileName);
							
							File arquivoZip = new File(Constantes.getPASTA_XML() + fileName);
							File diretorio = new File(Constantes.getPASTA_XML());
							
							String nomeFile = df.format(Calendar.getInstance().getTime())+ FILE_NFE_XML;
							ZipUtil.extrairZip(arquivoZip, diretorio,nomeFile);
							
							Message[] mensagensXML = { message };
							salvarBackup(store, folder,mensagensXML, messageNumber);
							baixou = true;
							mensagensXML = null;
							
						}
						if (fileName != null && StringUtil.validaContemStringXML(fileName)) {
							String nomeFile;
							nomeFile = df.format(Calendar.getInstance().getTime())+ FILE_NFE_XML;
							WLog.writeLogMailDown("Par/nomeFile", " : " + part + nomeFile );
							salvarArquivo(part, nomeFile);
							Message[] mensagensXML = { message };
							salvarBackup(store, folder,mensagensXML, messageNumber);
							baixou = true;
							mensagensXML = null;
							
						}
						if (fileName == null) {
							Message[] mensagensXML = { message };
							salvarBackup(store, folder,mensagensXML, messageNumber);
							baixou = true;
							mensagensXML = null;							
							
						}
												
					}
				} else if (msgObj instanceof BASE64DecoderStream) {
					String nomeFile;
					nomeFile = df.format(Calendar.getInstance().getTime())+ FILE_NFE_XML;
					
					WLog.writeLogMailDown("Else/nomeFile", " : " + part + nomeFile );
					BASE64DecoderStream base = (BASE64DecoderStream) message.getContent();
					FileOutputStream fileOutputStream = new FileOutputStream(Constantes.getPASTA_XML() + nomeFile);					
					int c = -1;

					while ((c = base.read()) != -1) {
						fileOutputStream.write(c);
					}

					Message[] mensagensXML = { message };
					salvarBackup(store, folder, mensagensXML,messageNumber);
					mensagensXML = null;
				} else {
					
					Message[] mensagensXML = { message };
					salvarBackup(store, folder, mensagensXML,messageNumber);
					mensagensXML = null;
					
					//WLog.writeLogMailDown(Constantes.getAPELIDO(), 
					//		"E-mail: ["+message.getSubject()+"] não contém arquivo em anexo.");
				}
				

				baixou = true;
				
				
				if (!baixou) {
					Message[] mensagensXML = { message };
					salvarBackup(store, folder, mensagensXML,messageNumber);
					mensagensXML = null;
				}
				
				ZipUtil.removerArquivosZipDiretorio(Constantes.getPASTA_XML());
				
			}
			// Fecha a pasta
			folder.close(true);
			// Historico de mensagens
			store.close();
			
		} catch (AuthenticationFailedException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(), 
					"Falha na Autenticação: "+ e.getMessage());
			store.close();
		} catch (FolderClosedException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Falha no fechamento da pasta: "+ e.getMessage());
			store.close();
		} catch (FolderNotFoundException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Pasta não encontrada: "+ e.getMessage());
			store.close();
		} catch (NoSuchProviderException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Provider não localizado: " + e.getMessage());
			store.close();
		} catch (ReadOnlyFolderException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Pasta com permissão de somente leitura: "+ e.getMessage());
			store.close();
		} catch (StoreClosedException e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Erro ao fechar pasta auxiliar: "+ e.getMessage());
			store.close();
		} catch (Exception e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Erro no método Principal: "+ e.getMessage());
			store.close();
		}
		
	}
	

	/**
	 * Envia os arquivos da pasta princial para a pasta reserva
	 * 
	 * @param store
	 * @param folder
	 * @param messages
	 * @throws MessagingException
	 */
	private void salvarBackup(Store store, Folder folder,Message[] messages, int i) throws MessagingException {
		Folder folderAux;
		folderAux = ManipularEmail.recuperarPastaAuxiliar(store);
		folder.copyMessages(messages, folderAux);
		folderAux.close(true);
		try {
			ManipularEmail.excluirMensagemInbox(this.messages, i);
		} catch (Exception e) {
			WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
					"Erro ao excluir e-mail: " + i + e.getMessage());
		}
	}

	/**
	 * Salva o arquivo em uma pasta
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void salvarArquivo(Part part, String nomeFile) throws IOException,MessagingException {
		FileOutputStream fileOutputStream = new FileOutputStream(Constantes.getPASTA_XML() + 
				File.separator + nomeFile);
		Object obj = part.getContent();
		if (obj instanceof InputStream) {
			InputStream is = (InputStream) obj;
			int ch = -1;
			while ((ch = is.read()) != -1) {
				fileOutputStream.write(ch);
			}
			fileOutputStream.close();
		}if(obj instanceof String){
			InputStream is = new ByteArrayInputStream(((String) obj).getBytes("UTF-8"));
			int ch = -1;
			while ((ch = is.read()) != -1) {
				fileOutputStream.write(ch);
			}
			fileOutputStream.close();
		}
		WLog.writeLogErroMailDown(Constantes.getAPELIDO(),
				"Arquivo salvo [" + nomeFile + "]");
	}

}
