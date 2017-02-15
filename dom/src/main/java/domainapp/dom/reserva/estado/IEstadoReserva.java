package domainapp.dom.reserva.estado;

public interface IEstadoReserva {
	public String getNombre();
	public void reservar();
	public void disponer();
	public void confirmar();
	public void checkin();
	public void checkout();
	
}
