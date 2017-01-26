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

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.reserva.Reserva;
import domainapp.dom.reserva.Reservas;
import domainapp.dom.reserva.Reservas.CreateDomainEvent;
import domainapp.dom.simple.SimpleObject;
import domainapp.dom.simple.SimpleObjects;
import domainapp.dom.tipodehabitacion.TipodeHabitacion;
import domainapp.dom.tipodehabitacion.TipodeHabitaciones;

@ViewModel
public class HomePageViewModel {

    //region > title
    public String title() {
        return getReservas().size() + " reservas";
    }
    //endregion

    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    public List<Reserva> getReservas() {
        return reservas.listarReservas();
    }
    //endregion

    @org.apache.isis.applib.annotation.HomePage
    public List<Reserva> getListaReservas() {
        return listaReservas.listarReservas();
    }
    //region > injected services
    @org.apache.isis.applib.annotation.Action
    public String crear()
    {
    	return "okey"; 
    }
    @javax.inject.Inject
    Reservas reservas;
    
    @javax.inject.Inject
    Reservas listaReservas;

    //endregion
}
