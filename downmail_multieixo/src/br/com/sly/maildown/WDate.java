package br.com.sly.maildown;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WDate {

    public String getCurrentDateStr () {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		java.util.Date data = new java.util.Date();
		return df.format(data);
   	}
    
    public java.util.Date getCurrentDateDate () {
		return new java.util.Date();
   	}
    
    /*
     * Data formatada para persistencia na base de dados
     */
    public String getDateToString (java.util.Date piData) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		return df.format(piData);
    }

    public java.sql.Date getJavaSqlDate(java.util.Date piData) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	java.util.Date parsedUtilDate;
		try {
			parsedUtilDate = df.parse(getDateToString(piData));
	    	return new java.sql.Date(parsedUtilDate.getTime());      	
		} catch (ParseException e) {
			return null;
		}  
    }
    
    public boolean isDataValidaDDMMAAAA (String piDataStr) {
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
    	java.util.Date parsedUtilDate;
    	df.setLenient(false);
		try {
			parsedUtilDate = df.parse(piDataStr);
	    	return true;      	
		} catch (ParseException e) {
		}     	
    	
    	return false;
    }
    
    public java.sql.Date getStrToDate (String piDataStr) { // MySQL
    	if (piDataStr.length() == "9999-99-99".length())
    		piDataStr += " 00:00:00";
    	piDataStr = piDataStr.replace("T", " ");
    	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return this.getJavaSqlDate((java.util.Date) df.parse(piDataStr));
		} catch (ParseException e) {
			return null;
		}
    }

    public java.sql.Date getStrFullToDate (String piDataStr) { // MySQL
    	piDataStr = piDataStr.replace("T", " ");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return this.getJavaSqlDate((java.util.Date) df.parse(piDataStr));
		} catch (ParseException e) {
			return null;
		}
    }
    
    
    public java.sql.Date getStrDDMMAAAAToDate (String piDataStr) { // MySQL
		while (piDataStr.indexOf("/") > -1)
			piDataStr = piDataStr.replace("/", "-");
    	
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return this.getJavaSqlDate((java.util.Date) df.parse(piDataStr));
		} catch (ParseException e) {
			return null;
		}
    }
    
    public int getDiaDoMes (Date piDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		java.util.Date data = piDate;
		String sData = df.format(data);
		int iDia = 0;
		try {
			iDia = new Integer((String)sData.subSequence(8, 10)).intValue();
		} catch (Exception ex) {
		}
    	
		return iDia;
    }

    public int getMesInt (Date piDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		java.util.Date data = piDate;
		String sData = df.format(data);
		int iMes = 0;
		try {
			iMes = new Integer((String)sData.subSequence(5, 7)).intValue();
		} catch (Exception ex) {
		}
    	
		return iMes;
    }

    public int getAnoInt (Date piDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		java.util.Date data = piDate;
		String sData = df.format(data);
		int iMes = 0;
		try {
			iMes = new Integer((String)sData.subSequence(0, 4)).intValue();
		} catch (Exception ex) {
		}
    	
		return iMes;
    }
    
    public String getDataMMDDAAAAStr (Date piDateAAAAMMDD) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");   
		java.util.Date data = piDateAAAAMMDD;
		String sData = df.format(data);
    	
		return sData;
    }
    
    public String getDiaDoMesStr (Date piDate) {
		return String.valueOf(getDiaDoMes(piDate));
    }

    public String getMesStr (Date piDate) {
    	String mes = String.valueOf(this.getMesInt(piDate));
    	if (Integer.valueOf(mes).intValue()<10)
    		mes = "0" + mes;
		return mes;
    }

    public String getAnoStr (Date piDate) {
    	String ano = String.valueOf(this.getAnoInt(piDate));
		return ano;
    }
    
}
