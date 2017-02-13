/*

#	Copyright ( C ) 2017
#
#   Gestion Hostel  is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with Gestion Hostel.  If not, see <http://www.gnu.org/licenses/>.
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