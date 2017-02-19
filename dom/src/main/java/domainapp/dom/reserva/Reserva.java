/*
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
package domainapp.dom.reserva;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.reserva.estado.Confirmada;
import domainapp.dom.reserva.estado.Disponible;
import domainapp.dom.reserva.estado.IEstadoReserva;
import domainapp.dom.reserva.estado.Liberada;
import domainapp.dom.reserva.estado.Ocupada;
import domainapp.dom.reserva.estado.Solicitada;




@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        table = "Reserva",
        identityType=IdentityType.DATASTORE
)
/*
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")*/
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(
            name = "listarTodas", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.reserva.Reserva ")
    
    /*
    @javax.jdo.annotations.Query(
            name = "findByName", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.reserva.Reserva "
                    + "WHERE name.indexOf(:name) >= 0 "),
    		@javax.jdo.annotations.Query(
        	
            name = "findByEmail", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.huesped.Huesped "
                    + "WHERE name.indexOf(:email) >= 0 ")
                    */
    
})





@DomainObject(objectType="RESERVA")

public class Reserva implements CalendarEventable {
	
    //public TranslatableString title() {
        //return TranslatableString.tr("Reserva: {name}", "name", huesped.getName());
    //}
    
    public Reserva()
	{
		this.estadoDisponible = new Disponible(this);
		this.estadoSolicitada = new Solicitada(this);
		this.estadoConfirmada = new Confirmada(this);
		this.estadoOcupada = new Ocupada(this);
		this.estadoLiberada = new Liberada(this);
		this.estado=this.estadoDisponible;
	}
    
	private IEstadoReserva estado;
	private Solicitada estadoSolicitada;
	private Disponible estadoDisponible;
	private Confirmada estadoConfirmada;
	private Liberada estadoLiberada;
	private Ocupada estadoOcupada;
	
	
	@javax.jdo.annotations.Column(allowsNull="true")
    @Property(
        hidden = Where.ANYWHERE
    )	
	
	
	public Liberada getEstadoLiberada() {
		return estadoLiberada;
	}

	public void setEstadoLiberada(Liberada estadoLiberada) {
		this.estadoLiberada = estadoLiberada;
	}

	@javax.jdo.annotations.Column(allowsNull="false")
    @Property(
        hidden = Where.ANYWHERE
    )	
	public Ocupada getEstadoOcupada() {
		return estadoOcupada;
	}

	public void setEstadoOcupada(Ocupada estadoOcupada) {
		this.estadoOcupada = estadoOcupada;
	}

	@javax.jdo.annotations.Column(allowsNull="false")
    @Property(
        hidden = Where.ANYWHERE
    )
    public Confirmada getEstadoConfirmada() {
		return estadoConfirmada;
	}

	public void setEstadoConfirmada(Confirmada estadoConfirmada) {
		this.estadoConfirmada = estadoConfirmada;
	}

	@javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            hidden = Where.ANYWHERE
        )
	public Solicitada getEstadoSolicitada() {
		return this.estadoSolicitada;
	}

	public void setEstadoSolicitada(Solicitada estadoSolicitada) {
		this.estadoSolicitada = estadoSolicitada;
	}
	
	
    @javax.jdo.annotations.Column(allowsNull="false")
    @Property(
            hidden = Where.ANYWHERE
        )
	public Disponible getEstadoDisponible() {
		return estadoDisponible;
	}

	public void setEstadoDisponible(Disponible estadoDisponible) {
		this.estadoDisponible = estadoDisponible;
	}
	
	

    @Property(hidden = Where.ANYWHERE)
    @Column(allowsNull = "false")
	@Persistent(extensions= {
			@Extension(vendorName = "datanucleous", key = "mapping-strategy",
			value = "per-implementation"),
			@Extension(vendorName = "datanucleus", key = "implementation-clases", value = 
			"domainapp.dom.reserva.estado.Disponible"
			+ ",domainapp.dom.reserva.estado.Solicitada"
			+ ",domainapp.dom.reserva.estado.Confirmada"
			+ ",domainapp.dom.reserva.estado.Ocupada"
			+ ",domainapp.dom.reserva.estado.Liberada"
					)}
					, columns = {
			@Column(name = "idDisponible"),
			@Column(name = "idSolicitado"),
			@Column(name = "idConfirmada"),
			@Column(name = "idOcupada"),
			@Column(name = "idLiberada") })
    
	public IEstadoReserva getEstado() {
		return estado;
	}
    @SuppressWarnings("deprecation")
	@Disabled	
	public void setEstado(IEstadoReserva estadoReserva) {
		this.estado = estadoReserva;
	}

    
	@MemberOrder(name="Estado", sequence="1")
	public String getNombreEstado()
	{
		return estado.getNombre();
	}
    
	
	public Reserva reservar()
	{
		this.getEstado().reservar();
		return this;
	}
	public boolean hideReservar()
	{
		final String ocultarSolicitar= this.getEstado().getClass().getSimpleName();
		if (ocultarSolicitar.equals("Ocupada") || ocultarSolicitar.equals("Solicitada") || ocultarSolicitar.equals("Confirmada")  || ocultarSolicitar.equals("Liberada"))
		{  
			
			return true;
		}
		else
		{
		return false;
		}
	}
	
    private Huesped huesped;
    @javax.jdo.annotations.Column(allowsNull="false")
    public Huesped getHuesped() {
        return huesped;
    }
    
    public void setHuesped(final Huesped huesped) {
        this.huesped = huesped;
    }
    

    
    
    @Property()
    @javax.jdo.annotations.Column(allowsNull="false")
    private LocalDate fechaIn;
    
    public LocalDate getFechaIn() {
        return fechaIn;
    }
    
    public void setFechaIn(final LocalDate fechaIn) {
        this.fechaIn = fechaIn;
    }
    
    
    
    @Property()
    @javax.jdo.annotations.Column(allowsNull="false")
    private LocalDate fechaSal;
    
    public LocalDate getFechaSal() {
        return fechaSal;
    }
    public void setFechaSal(final LocalDate fechaSal) {
        this.fechaSal = fechaSal;
    }
    
    
   
    
    @Property()
    @javax.jdo.annotations.Column(allowsNull="false")
    private int numHues;
    public int getNumHues() {
        return numHues;
    }
   
    public void setNumHues(final int numHues) {
        this.numHues = numHues;
    }


    @Property()
    @javax.jdo.annotations.Column(allowsNull="false")
    private Habitacion habitacion;
    
    public Habitacion getHabitacion() {
        return habitacion;
    }
    
    public void setHabitacion(final Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    

    @Property()
    @javax.jdo.annotations.Column(allowsNull="false")
    private String canalVenta;
    
    public String getCanalVenta() {
    	return canalVenta; 
    }
    public void setCanalVenta(String canalVenta) {
    	this.canalVenta = canalVenta;
    }
    
    
    

	
    
    
    
    
	public Reserva disponer() {
		this.getEstado().disponer();
		return this;
	}
	public boolean hideDisponer()
	{
		final String ocultarDisponer= this.getEstado().getClass().getSimpleName();
		if (ocultarDisponer.equals("Ocupada") ||
				ocultarDisponer.equals("Disponible")||
				ocultarDisponer.equals("Solicitada")||
				ocultarDisponer.equals("Confirmada") ||
				ocultarDisponer.equals("Liberada"))
		{  
			
			return true;
		}
		else
		{
		return false;
		}
	}
	
	
	public Reserva confirmar() {
		this.getEstado().confirmar();
		return this;
	}
	//@Action()
	public boolean hideConfirmar()
	{
		final String ocultarConfirmar= this.getEstado().getClass().getSimpleName();
		if (ocultarConfirmar.equals("Ocupada") ||
				ocultarConfirmar.equals("Liberada") ||
				ocultarConfirmar.equals("Confirmada"))
		{  
			
			return true;
		}
		else
		{
		return false;
		}
	}

	
	public Reserva checkin() {
		this.getEstado().checkin();
		return this;
	}
	
	public boolean hideCheckin()
	{
		final String ocultarCheckin= this.getEstado().getClass().getSimpleName();
		if (ocultarCheckin.equals("Checkin") ||
				ocultarCheckin.equals("Solicitada") ||
				ocultarCheckin.equals("Liberada") ||
				ocultarCheckin.equals("Ocupada"))
		{  
			
			return true;
		}
		else
		{
		return false;
		}
	}

	
	public Reserva checkout() {
		this.getEstado().checkout();
		return this;
	}
	
	public boolean hideCheckout()
	{
		final String ocultarCheckout= this.getEstado().getClass().getSimpleName();
		if (ocultarCheckout.equals("Checkin") ||
				
				ocultarCheckout.equals("Liberada"))
		{  
			
			return true;
		}
		else
		{
		return false;
		}
	}
	
	public String title()
	{
		return "Reserva "+ this.getHuesped().getName() + ", " + this.getEstado().getClass().getSimpleName();
	}

	@Override
	public String getCalendarName() {
		
		return "Dormi: " + getHabitacion().getName();
	}

	//@Programmatic
	//@Override
	public CalendarEvent toCalendarEvent() {
		
		return new CalendarEvent(this.getFechaIn().toDateTimeAtStartOfDay(), getCalendarName(), getNotes());
		//return new CalendarEvent(this.getFechaIn().toDateTimeAtStartOfDay(), getCalendarName(), "");

	}
	
	
	//@Programmatic
    public String getNotes() {
    	
    	 //return getNumHues() + " cama/s, " + huesped.getName() + " @ dormi " + getHabitacion().getName();
    	 return " Cama/s: " + getNumHues() + " @ dormi " + getHabitacion().getName() + ", " + getHuesped().getName() + ".";

    }
    /*
    @Programmatic
    @Override
	public String getCalendarName() {
		
		return getHabitacion().getName();
	}
    
    
    



    
	/*
    

    public static class DeleteDomainEvent extends ActionDomainEvent<Reserva> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }
    */


	@Inject
	TitleService titleService;

	/*
    @Override
    public int compareTo(final Reserva other) {
        return ObjectContracts.compare(this, other, "fechaIn");
    }
    */
	
	
    public enum E_canalVenta{
    	Booking, Despegar;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;
    
    

}
