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
