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

import java.util.Arrays;
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
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.assertj.core.util.Lists;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

import domainapp.dom.configuracion.Configuracion;
import domainapp.dom.configuracion.Configuraciones;
import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.habitacion.Habitaciones;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.simple.SimpleObject;
import domainapp.dom.tipodehabitacion.TipodeHabitacion;
import java.time.temporal.ChronoUnit;

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
    public List<Reserva> listarReservas() {
        return repositoryService.allInstances(Reserva.class);
    }
    
    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Reserva> buscarPorNombre(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByName",
                        "name", name));
    }
    

  //region > Crear Reserva (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Reservas> {
        public CreateDomainEvent(final Reservas source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    
    
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")

    public Reserva crearReserva(
    		
            final
            @ParameterLayout(named="Huesped (ingrese email del titular)") Huesped huesped,
            //@Parameter( mustSatisfy = validaFechas.FechaInEspecificaciones.class )
            @ParameterLayout(named="Fecha llegada") LocalDate fechaIn,
            @ParameterLayout(named="Fecha llegada") LocalDate fechaSal,
    		@ParameterLayout(named="Días de estadía") int diasEstadia,
    		@ParameterLayout(named="Habitación") Habitacion habitacion,
    		@ParameterLayout(named="Huéspedes?") int numHues,
    		@ParameterLayout(named="Canal de venta")@Parameter(optionality = Optionality.MANDATORY) String canalVenta)

        
    {
        final Reserva obj = repositoryService.instantiate(Reserva.class);
        obj.setHuesped(huesped);
        obj.setFechaIn(fechaIn);
        obj.setFechaSal(fechaSal);
        obj.setDiasEstadia(diasEstadia);
        obj.setHabitacion(habitacion);
        obj.setNumHues(numHues);
        obj.setCanalVenta(canalVenta);
        
        
        repositoryService.persist(obj);	
        return obj;
    }
    
    // Fin de Region Crear Reserva.
    
    
    //diasEstadia = daysBetween(fechaIn, fechaSal);
    
    // Validación fecha de Reserva inicial
    @Programmatic
    public String validate1CrearReserva(final LocalDate fechaIn){
    	
    	
    	LocalDate hoyy = LocalDate.now();
    	if (fechaIn.isBefore(hoyy)) {
			return "Una Reserva no puede empezar en el pasado";
		}
    	  	
    	
    	return null;
    }
    
    //
    
 
    
    // Autocompleta el Huésped a partir de su email:
    public Collection<Huesped> autoComplete0CrearReserva(final @MinLength(2) String email) {
        return huespedes.findByEmail(email);
    }

    // Lista las habitaciones creadas en la clase correspondiente:    
     public List<Habitacion> choices3CrearReserva() {
     
        return habitaciones.listAll();
   	}	
    
     public Collection<Integer> choices4CrearReserva() {
         return Arrays.asList(1,2,3,4,5,6,7,8,9,10);
     }
     
     public List<String> choices5CrearReserva() {
         return Arrays.asList("Despegar","Avantrip");
         
     }

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    private Huespedes huespedes;
    
    @javax.inject.Inject
    private Habitaciones habitaciones;
    
    @javax.inject.Inject
    private Configuraciones configuraciones;
    

    
    //endregion
}

