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

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;
import org.assertj.core.util.Lists;
import org.datanucleus.store.types.wrappers.Collection;
//import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.google.inject.Inject;

//import domainapp.dom.huesped.Huesped.E_canalVenta;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable; 

/**
 * @author Matt
 *
 */
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Reserva"
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
                    + "FROM domainapp.dom.reserva.Reserva "),
    @javax.jdo.annotations.Query(
            name = "findByName", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.reserva.Reserva "
                    + "WHERE name.indexOf(:name) >= 0 "),
    		@javax.jdo.annotations.Query(
        	
            name = "findByEmail", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.reserva.Huesped "
                    + "WHERE name.indexOf(:email) >= 0 ")
    
})

@javax.jdo.annotations.Unique(name="Reserva_name_UNQ", members = {"name"})
@DomainObject
	public class Reserva implements Comparable<Reserva>, Serializable, CalendarEventable {

    public static final int NAME_LENGTH = 40;


    public TranslatableString title() {
        return TranslatableString.tr("Reserva: {name}", "name", getName());
    }


    public static class NameDomainEvent extends PropertyDomainEvent<Reserva,String> {}
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


    
    private String email;
    @javax.jdo.annotations.Column(allowsNull="true")
    public String getEmail() {
        return email;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setEmail(final String email) {
        this.email = email;
    }
    
    @Column	(allowsNull = "false")
    @Property()
    private LocalDate fechaIn;
    @javax.jdo.annotations.Column(allowsNull="false")
    public LocalDate getFechaIn() {
        return fechaIn;
    }
    @javax.jdo.annotations.Column(allowsNull="false")
    public void setFechaIn(final LocalDate fechaIn) {
        this.fechaIn = fechaIn;
    }
    
    @Column	(allowsNull = "false")
    @Property()
    private LocalDate fechaSal;
    @javax.jdo.annotations.Column(allowsNull="true")
    public LocalDate getFechaSal() {
        return fechaSal;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setFechaSal(final LocalDate fechaSal) {
        this.fechaSal = fechaSal;
    }
    

    
    private int numHues;
    @javax.jdo.annotations.Column(allowsNull="true")
    public int getNumHues() {
        return numHues;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setNumHues(final int numHues) {
        this.numHues = numHues;
    }


    
    private String habitacion;
    @javax.jdo.annotations.Column(allowsNull="true")
    public String getHabitacion() {
        return habitacion;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setHabitacion(final String habitacion) {
        this.habitacion = habitacion;
    }
    
    
    private E_canalVenta canalVenta;
    @javax.jdo.annotations.Column(allowsNull="false")
    public E_canalVenta getCanalVenta() {
    	return canalVenta; 
    }
    public void setCanalVenta(E_canalVenta canalVenta) {
    	this.canalVenta = canalVenta;
    }
    
    

    @Programmatic
    @Override
	public String getCalendarName() {
		
		return getName();
	}
    
    @Programmatic
    public String getNotes() {
    	//String not[] = new String[2];
        //not[0]= getNumHues();
        //not[1] = getHabitacion();
        return getNumHues() + " cama/s, " + getName();
    	
    }
    


	@Programmatic
	@Override
	public CalendarEvent toCalendarEvent() {
		
		//if (getFechaIn() == getFechaSal())
		return new CalendarEvent(getFechaIn().toDateTimeAtStartOfDay(), getCalendarName(), getNotes());
		/*else
		for (getFechaIn(); ; getFechaIn().plusDays(1))
		return new CalendarEvent(getFechaIn().toDateTimeAtStartOfDay(), getCalendarName(), getName());	*/
	}

    

    
    
    public TranslatableString validateName(final String name) {
        return name != null && name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }



    public static class DeleteDomainEvent extends ActionDomainEvent<Reserva> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }
    


	@Inject
	TitleService titleService;


    @Override
    public int compareTo(final Reserva other) {
        return ObjectContracts.compare(this, other, "name");
    }
    
    public enum E_canalVenta{
    	Booking, Despegar;
    }


    @javax.inject.Inject
    RepositoryService repositoryService;
    
    
    	
	

}
