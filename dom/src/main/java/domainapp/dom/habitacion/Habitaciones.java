
package domainapp.dom.habitacion;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.tipodehabitacion.TipodeHabitacion;
import domainapp.dom.tipodehabitacion.TipodeHabitaciones;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Habitaciones.class
)

@DomainServiceLayout(
        menuOrder = "10",
        named="Habitaciones"
)
public class Habitaciones {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Habitacion");
    }
    //endregion

    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Habitacion> listAll() {
        return repositoryService.allInstances(Habitacion.class);
    }
    //endregion

    //region > findByName (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Habitacion> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Habitacion.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Habitaciones> {
        public CreateDomainEvent(final Habitaciones source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "1")
    public Habitacion crearHabitacion(
            final @ParameterLayout(named="Nombre") String name,
            final @ParameterLayout(named="Tipo de Habitación")	 TipodeHabitacion tipodeHabitacion
           ) {
        final Habitacion obj = repositoryService.instantiate(Habitacion.class);
        obj.setName(name);
        obj.setTipodeHabitacion(tipodeHabitacion);
        repositoryService.persist(obj);
        return obj;
    }
    
    @Programmatic
    public List<TipodeHabitacion> choices1CrearHabitacion() {
        return tipodeHabitaciones.listAll();
   	}
    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    private TipodeHabitaciones tipodeHabitaciones;

    //endregion
}
