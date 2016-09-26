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

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.assertj.core.util.Lists;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import domainapp.dom.simple.SimpleObject;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Reserva.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class Reservas {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Reservas");
    }
    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Reserva> listAll() {
        return repositoryService.allInstances(Reserva.class);
    }
    //endregion
    //calendar 
/*    public interface CalendarEventable {
        String getCalendarName();
        CalendarEvent toCalendarEvent();
    } */
    
    //fin calendar
    
    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Reserva> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByName",
                        "name", name));
    }
    

    
  //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Reservas> {
        public CreateDomainEvent(final Reservas source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    
    
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")

    public Reserva create(
    		
            final @ParameterLayout(named="Nombre")String name, 
            //@ParameterLayout(named="Teléfono")String numTel,
            @Parameter(
                    regexPattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+",
                    regexPatternFlags = Pattern.CASE_INSENSITIVE,
                    regexPatternReplacement = "Ingrese una dirección de correo electrónico válida (contienen un símbolo '@') -"   
                )
    		@ParameterLayout(named="Email") String email,
    		
    		@ParameterLayout(named="Fecha llegada") LocalDate fechaIn,
    		@ParameterLayout(named="Fecha salida") LocalDate fechaSal,
    		@ParameterLayout(named="Húespedes?") int numHues,
    		@ParameterLayout(named="Habitación") String habitacion) 

    	
    
    {
        final Reserva obj = repositoryService.instantiate(Reserva.class);
        obj.setName(name);
        obj.setEmail(email);
        obj.setFechaIn(fechaIn);
        obj.setFechaSal(fechaSal);
        obj.setNumHues(numHues);
        obj.setHabitacion(habitacion);
        
        
        
        repositoryService.persist(obj);
        return obj;
    }
	
    //endregion
    

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    
    
    //endregion
}

