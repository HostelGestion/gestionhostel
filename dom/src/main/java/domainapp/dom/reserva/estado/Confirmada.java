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
package domainapp.dom.reserva.estado;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;

import domainapp.dom.reserva.Reserva;
@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        table = "Confirmada",
        identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY , column = "idConfirmada" )
@DomainObject(objectType="CONFIRMADA")
public class Confirmada implements IEstadoReserva {
	private Reserva reserva;
	
	public String getNombre()
	{
		return "CONFIRMADA";
	}
	
	public Confirmada (Reserva reserva)
	{
		this.reserva= reserva;
		
	}
	@Override
	public void reservar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disponer() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void confirmar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkin() {
		this.reserva.setEstado(this.reserva.getEstadoOcupada());
	}
	@Override
	public void checkout() {
		// TODO Auto-generated method stub
		
	}

}
