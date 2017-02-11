package domainapp.dom.reserva.estado;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.services.message.MessageService;

import com.google.inject.Inject;

import domainapp.dom.reserva.Reserva;
@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        table = "Liberada",
        identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY , column = "idLiberada" )
@DomainObject(objectType="LIBERADA")
public class Liberada implements IEstadoReserva{
	private Reserva reserva;
	
	public Liberada(Reserva reserva) {
		this.reserva=reserva;
	}
	@Override
	public void reservar() {
		msg.informUser("No se puede realizar esta accion en este estado de la reserva");
		
	}

	@Override
	public void disponer() {

	}

	@Override
	public void confirmar() {
	}

	@Override
	public void checkin() {

	}

	@Override
	public void checkout() {

	}
	@Inject
	MessageService msg;
}
