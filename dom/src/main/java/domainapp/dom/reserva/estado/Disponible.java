package domainapp.dom.reserva.estado;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.services.message.MessageService;

import com.google.inject.Inject;

import java.awt.Container;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;

import domainapp.dom.reserva.Reserva;
@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        table = "Disponible",
        identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY , column = "idDisponible" )
@DomainObject(objectType="DISPONIBLE")
public class Disponible implements IEstadoReserva{
	private Reserva reserva;
	public Disponible(Reserva reserva)
	{
		this.reserva=reserva;
	}
	@Override
	public void reservar() {
		this.reserva.setEstado(this.reserva.getEstadoSolicitada());
		
	}
	@Override
	public void disponer() {
		msg.informUser("No se puede realizar esta accion en este estado de la reserva");
	}
	@Override
	public void confirmar() {
		
		
	}
	@Override
	public void checkin() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkout() {
		// TODO Auto-generated method stub
		
	}
	@Inject
	MessageService msg;
}
