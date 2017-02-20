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
package domainapp.dom.caja;


import java.util.ArrayList;
import java.util.List;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;
import org.apache.isis.applib.annotation.MemberOrder;

import domainapp.dom.huesped.Huesped;
import domainapp.dom.reportes.GenerarReporte;
import domainapp.dom.reportes.TicketReporte;
import domainapp.dom.caja.RepoCaja;
import net.sf.jasperreports.engine.JRException;
import org.joda.time.LocalDate;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Caja"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.Caja "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.Caja "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Caja_name_UNQ", members = {"name"})
@DomainObject
public class Caja implements Comparable<Caja> {

    public static final int NAME_LENGTH = 40;


    public TranslatableString title() {
        return TranslatableString.tr("Object: {name}", "name", getName());
    }


    public static class NameDomainEvent extends PropertyDomainEvent<Caja,String> {}
    @javax.jdo.annotations.Column(
            allowsNull="false",
            length = NAME_LENGTH
    )
    @Property(
        domainEvent = NameDomainEvent.class
    )
    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public TranslatableString validateName(final String name) {
        return name != null && name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }
    
    private Huesped huesped;
    @javax.jdo.annotations.Column(allowsNull="false")
    public Huesped getHuesped() {
        return huesped;
    }
    @javax.jdo.annotations.Column(allowsNull="false")
    public void setHuesped(final Huesped huesped) {
        this.huesped = huesped;
    }

    private Double monto;
    @javax.jdo.annotations.Column(allowsNull="false")
    public Double getMonto() {
        return monto;
    }
    public void setMonto(final Double monto) {
        this.monto = monto;
    }
    
   /* private
    private String concepto;
    @javax.jdo.annotations.Column(allowsNull="false")
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }*/
    
    @MemberOrder(sequence = "2")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Fecha de Pago")
	private LocalDate fechaDePago;
	public LocalDate getFechaDePago() {return fechaDePago;}
	public void setFechaDePago(LocalDate fechaDePago) {this.fechaDePago = fechaDePago;}

    public static class DeleteDomainEvent extends ActionDomainEvent<Caja> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    @ActionLayout(named="Eliminar Ingreso")
    public void delete() {
        repositoryService.remove(this);
    }

public String imprimirTicket() throws JRException{
		
		List<Object> objectsReport = new ArrayList<Object>();
		
		TicketReporte caja = new TicketReporte();
			
	//	Caja.setMonto(getMonto());
		//caja.setValor(valor);// .setValor(); // .setMonto(getMonto());
		//caja.setMonto(getMonto());
		
		//caja.setValor(null);
		//caja.setMonto(getMonto());
		//caja.setMonto(getMonto());
		//caja.setHuesped(getHuesped());
		//caja.setHuesped(huesped);
	
		caja.setFechaDePago(String.valueOf(getFechaDePago()));
	
		objectsReport.add(caja);
		
		String nombreArchivo ="reportes/Caja_"/* + String.valueOf(caja.getNroRecibo())*/ ;
		
		GenerarReporte.generarReporte("reportes/Ticketdepago.jrxml", objectsReport, nombreArchivo);
		
		return "Reporte Generado.";
}
    
    @Action
    public Blob descargar() {
    	String palabra = "hola que tal";
    	byte[] bitsDeArchivo=palabra.getBytes();
    	MimeType tipoDeArchivo = null;
    	try {
			tipoDeArchivo=new MimeType("text/plain");
		} catch (MimeTypeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new Blob("archivo.txt",tipoDeArchivo,bitsDeArchivo);
    }
    @Override
    public int compareTo(final Caja other) {
        return ObjectContracts.compare(this, other, "name");
    }


    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject	
	RepoCaja repoCaja;

}
