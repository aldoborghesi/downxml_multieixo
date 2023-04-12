package br.com.sly.maildown;

import java.util.ArrayList;




import javax.mail.MessagingException;


import br.com.sly.maildown.WLog;
import br.com.sly.maildown.ReadEmails;

public class MonitorEmailNFeTH extends Thread   {
	
	private int  inLeEmail = 0;
	private int  inPause   = 10000;
	private long timeIni   = 0;
	private long timeInii  = 0;
    private long intervalo = 50000;
	
	boolean shutdown = false;
 	

	/* Constructor - Thread */
    public MonitorEmailNFeTH (String name)  {
    	
    	super(name);
    	
    	if (name.equals("")) {
    		this.setName("IDODownMail-Monitor");
    	}
    	
    }
	
	@Override
	public void interrupt() {
		super.interrupt();
		System.out.println("Interrupt" + shutdown);		
		shutdown   = true;
   		WLog.writeLog(this.getClass().getSimpleName(), "Recebeu notificacao para Shutdown.");
	}

	@Override
	public void  run() {
		super.run();
		
		
	    WLog.writeLog(this.getClass().getSimpleName(), "Startup");
		
		intervalo = Integer.valueOf(getIntervalo());

		
		// Atraso so serviço
	    try {this.sleep(30000);} catch (InterruptedException e) {}

    	shutdown   = false;
	    while (!shutdown  ) {

	    	if (!shutdown)
	    	    try {this.sleep(inPause);} catch (InterruptedException e) {}

            if (((System.currentTimeMillis() - timeIni) / 1000) >= intervalo ||	timeIni == 0) {	
            	try {
					baixarEmail();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	timeIni = System.currentTimeMillis();	
            }

	    	if (shutdown)
	    		WLog.writeLog(this.getClass().getSimpleName(), "Shutdown");
	    	    
	    }
	}
	
	/*
	 * Acordar as conexões para que não caiam no timeout
	 * Em alguns clientes os bancos nunca saem do AR 
	 */
	private void baixarEmail () throws Exception {
		int contasConfiguradas = 0; 
		
		if (shutdown)
			return;
 
		
		WLog.writeLogMailDown(Constantes.getAPELIDO(), 
				"Download de E-Mails. Intervalo espera [" + intervalo + "]. Iniciado ...");
		
    	timeInii = System.currentTimeMillis();	

		ReadEmails readMail;
		try {	
 
				contasConfiguradas++;
				
				readMail = new ReadEmails();
				readMail.baixar();
				readMail = null;
			
		 }
		
			catch (MessagingException e) {
			e.printStackTrace();
		}

		WLog.writeLogMailDown(Constantes.getAPELIDO(), 
				"Download de E-Mails. Tempo [" + ((System.currentTimeMillis() - timeInii) / 1000) + "]. Concluido.");
		
		if (contasConfiguradas==0) {
			WLog.writeLogMailDown(Constantes.getAPELIDO(), 
					"Nenhuma conta de e-mail ATIVA para Download de NFe, serviço parado.");
			shutdown = true;
		}
	}
	
	private String getIntervalo () {
 
			
			long tempo = 0;
   
		
		return "30";
	}
	
}
