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
package domainapp.app.services.homepage;

import java.util.List;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.ViewModel;

import java.util.List;





import domainapp.dom.reserva.RepoReserva;
import domainapp.dom.reserva.Reserva;
import domainapp.dom.habitacion.Habitaciones;
import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;


@ViewModel
public class HomePageViewModel {
	
    //region > title
    public String title() {
    	
    	//if (getReservas().size() < 1) {
    		//return "Cargue Húesped para poder hacer su primer Reserva";
    	//}
    	if ((habitaciones.listarHabitaciones().size() < 1)) {
    		return "Cargue un Tipo de Habitación y luego una Habitación";
    	} 
    	
    	if (huespedes.listarHuespedes().size() < 1) {
    		return "Cargue al Húesped para poder hacer su primer Reserva";
    	} 	
    	
    	

    	
    	else
    	{
    	
        return getReservas().size() + " reservas";}
    }
    //endregion

    

    
    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    public List<Reserva> getReservas() {
        return repoReserva.listarActuales();
    }
 	
    
    

    

    /*
    //region > Botón nueva Reserva - seguir trabajándolo
    @org.apache.isis.applib.annotation.Action
    public String crear()
    {
    	return "okey"; 
    }
    */
	//endregion
	
	@javax.inject.Inject
    RepoReserva repoReserva;
	
	@javax.inject.Inject
    Habitaciones habitaciones;
	
	@javax.inject.Inject
    Huespedes huespedes;
    
   
    
    
	
    
}
