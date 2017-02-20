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

import java.math.BigDecimal;
import domainapp.dom.caja.Caja;
import domainapp.dom.huesped.Huesped;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class TicketReporte {
	/*
	private Double monto;
	public Double getMonto() {return monto;}
	public void setMonto(Double monto) 
	{this.monto = monto;}
	
	private Huesped huesped;
	public Huesped getHuesped() {return huesped;}
	public void setHuesped(Huesped huesped)
	{this.huesped = huesped;}
	
	/*private String nroRecibo;
	public String getNroRecibo() {return nroRecibo;}
	public void setNroRecibo(String nroRecibo) {this.nroRecibo = nroRecibo;}
	*/
	private String fechaDePago;
	public String getFechaDePago() {return fechaDePago;}
	public void setFechaDePago(String fechaDePago)
	{this.fechaDePago = fechaDePago;}
/*
	private BigDecimal valor;
	public BigDecimal getValor() {return valor;}
	public void setValor(BigDecimal valor) {this.valor = valor;}
	
	private String club;
	public String getClub() {return club;}
	public void setClub(String club) {this.club = club;}
	
	private String cuotaClub;
	public String getCuotaClub() {return cuotaClub;}
	public void setCuotaClub(String cuotaClub) 
	{this.cuotaClub = cuotaClub;}*/
}