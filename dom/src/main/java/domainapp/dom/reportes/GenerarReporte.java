/*
 *	Copyright ( C ) 2017 , GESTION HOSTEL
 *
 *   GESTION HOSTEL is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.dom.reportes;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.ReportContext;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class GenerarReporte {
	
	public static void generarReporte(String jrxml, List<Object> parametros, String nombreArchivo)  throws JRException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//Levanta el jrxml
		File file = new File(jrxml);
		
		//Almacena el array de datos
		JRBeanArrayDataSource jArray= new JRBeanArrayDataSource(parametros.toArray());
		
		FileInputStream input = null;
		
		try{
			input = new FileInputStream(file);

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		//Levanta el modelo del reporte			
		JasperDesign jd = JRXmlLoader.load(input);
						
		//Compila el reporte
		JasperReport reporte = JasperCompileManager.compileReport(jd);
		
		//Lo llena con los datos del datasource
		JasperPrint print = JasperFillManager.fillReport(reporte, map, jArray);
		
//		Lo muestra con el jasperviewer
//		JasperViewer.viewReport(print, false);
		
		JasperExportManager.exportReportToPdfFile(print, nombreArchivo + ".pdf");
		
		// Muestra el reporte en otra ventana
 		// JasperExportManager.exportReportToHtmlFile(print, "nuevo.html");
		
		//Abre el reporte recien generado
		try {
		     File path = new File (nombreArchivo + ".pdf");
		     Desktop.getDesktop().open(path);
		}catch (IOException ex) {
		     ex.printStackTrace();
		}
	}	
	
	@javax.inject.Inject	
	public ReportContext reportContext;	

	@javax.inject.Inject
	DomainObjectContainer container;
	 	
}