package br.com.buch.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class CodeUtils {

	
	public static Integer diasDiferenca(Date data1, Date data2)throws IllegalArgumentException{
		if(data1 == null || data2 == null){
			throw new IllegalArgumentException("Datas estão Nulas");
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(data1);
		c2.setTime(data2);
		
		return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
	}
	
	
	public static String getDataFormatada(String patern, Date data)throws IllegalArgumentException{
		if(data == null || patern.equals("")){
			throw new IllegalArgumentException("Parametros incorretos ou nulos.");
		}
		return new SimpleDateFormat(patern).format(data);
	}
}
