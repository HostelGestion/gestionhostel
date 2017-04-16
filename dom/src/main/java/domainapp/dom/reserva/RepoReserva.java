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
package domainapp.dom.reserva;

	
import java.math.BigDecimal;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.habitacion.Habitaciones;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.reserva.estado.Solicitada;
import domainapp.dom.tipodehabitacion.TipodeHabitacion;
import domainapp.dom.reserva.estado.Solicitada;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Reserva.class
)
@DomainServiceLayout(
        named="Reservas",
        menuOrder = "1"
)
public class RepoReserva {
	
	
	
    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Reserva> listarReservas() {
        return repositorio.allInstances(Reserva.class);
    }
    
    //region > buscarPorNombre (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Reserva> buscarPorHuesped(
            @ParameterLayout(named="Huesped:")
            final Huesped huesped
            
    ) {
        return repositorio.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByHuesped",
                        "huesped", huesped));
    }
    
    @Programmatic
    public List<Huesped> choices0BuscarPorHuesped() {
        return huespedes.listarHuespedes();
   	}
    
    @ActionLayout(
            cssClassFa = "fa fa-thumbs-up"
    )
    
    	
        @Action(semantics = SemanticsOf.SAFE)
        @MemberOrder(sequence = "20")
    
    public Reserva crearReserva(final
            @ParameterLayout(named="Huesped (ingrese email del titular)") Huesped huesped,
            @ParameterLayout(named="Fecha llegada") LocalDate fechaIn,
            @ParameterLayout(named="Fecha salida") LocalDate fechaSal,
    		@ParameterLayout(named="Habitación") Habitacion habitacion,
    		@ParameterLayout(named="Huéspedes?") int numHues,
    		@ParameterLayout(named="Canal de venta")@Parameter(optionality = Optionality.MANDATORY) String canalVenta
    		
    			

    		)
 {
    	
    	Reserva mireserva = repositorio.instantiate(Reserva.class);
    	mireserva.setHuesped(huesped);
    	mireserva.setFechaIn(fechaIn);
    	mireserva.setFechaSal(fechaSal);
    	mireserva.setHabitacion(habitacion);
    	mireserva.setNumHues(numHues);
    	mireserva.setCanalVenta(canalVenta);
    	mireserva.setGasto(new BigDecimal(habitacion.getTipodeHabitacion().getPrecio() * Days.daysBetween(fechaIn, fechaSal).getDays() * numHues));
    	
    	mireserva.getEstado().reservar();
    	repositorio.persist(mireserva);
    	return mireserva;
    }
    
    // Fin de Region Crear Reserva.
    
    //Región validar Reserva
    @Programmatic
    public String validateCrearReserva(
    		final Huesped huesped,
    		final LocalDate fechaIn,
    		final LocalDate fechaSal,
    		final Habitacion habitacion,
    		final int numHues,
    	
    		final String canalVenta,
    		final BigDecimal consumo) {
    
    	if (fechaIn.isBefore(LocalDate.now()))
    		{return "Corregir la fecha inicial.";}
    	if (fechaSal.isBefore(fechaIn))
    		{return "Corregir la fecha de salida.";}
    	if (numHues > habitacion.getTipodeHabitacion().getCamas())
    		{return "La habitación no admite ese número de huéspedes.";}
   
    
    return "";
    }
    
  //Fin región validar Reserva
    
    // Autocompleta el Huésped a partir de su email:
    public Collection<Huesped> autoComplete0CrearReserva(final @MinLength(2) String email) {
        return huespedes.buscarPorEmail(email);
    }

    // Lista las habitaciones creadas en la clase correspondiente:    
     public List<Habitacion> choices3CrearReserva() {
     
        return habitaciones.listarHabitaciones();
   	}	
    
     public Collection<Integer> choices4CrearReserva() {
         return Arrays.asList(1,2,3,4,5,6,7,8,9,10);
     }
     

     
     public List<String> choices5CrearReserva() {
         return Arrays.asList("Despegar","Avantrip");
         
     }

     
    
    
    @javax.inject.Inject
    private Huespedes huespedes;
    
    @javax.inject.Inject
    private Habitaciones habitaciones;
    
    
    @Inject
    RepositoryService repositorio;
    

    
    //endregion
}

