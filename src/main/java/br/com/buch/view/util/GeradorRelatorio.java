package br.com.buch.view.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class GeradorRelatorio implements Work{

	private String caminhoRelatorio;
	private HttpServletResponse response;
	private Map<String, Object> parametros;
	
	
	
	public GeradorRelatorio(String caminhoRelatorio,Map<String, Object> parametros) {
		this.caminhoRelatorio = caminhoRelatorio;
		this.response = 
				(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		this.parametros = parametros;
	}


	@Override
	public void execute(Connection connection) throws SQLException {
				
		try {			
			//gerando o jasper design
            JasperDesign desenho = JRXmlLoader.load(this.getClass().getResourceAsStream(this.caminhoRelatorio));

            //compila o relatório
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
			

			JasperPrint print = JasperFillManager.fillReport(relatorio, parametros,connection);			

			
			//Primeira Forma de Fazer 
			byte[] b = JasperExportManager.exportReportToPdf(print);
			response.setContentType("application/pdf");
			response.getOutputStream().write(b);
			response.getCharacterEncoding();
			
			
			//Segunda Forma de fazer
			//JRExporter exporter = new JRPdfExporter();		
			//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			//exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			//exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
			//exporter.exportReport();
			//response.setContentType("application/pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("Erro ao gerar o relatório /n" + this.caminhoRelatorio,e);			
		}		
	}
}
