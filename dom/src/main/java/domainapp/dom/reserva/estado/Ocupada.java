package domainapp.dom.reserva.estado;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;

import domainapp.dom.reserva.Reserva;
@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        table = "Ocupada",
        identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY , column = "idOcupada" )
@DomainObject(objectType="OCUPADA")
public class Ocupada implements IEstadoReserva{
	public Reserva reserva;
	public Ocupada(Reserva reserva)
	{
		this.reserva=reserva;
	}
	
	public String getNombre()
	{
		return "OCUPADA";
	}
	
	@Override
	public void reservar() {
		
		
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkout() {
		this.reserva.setEstado(this.reserva.getEstadoLiberada());
	}
}
