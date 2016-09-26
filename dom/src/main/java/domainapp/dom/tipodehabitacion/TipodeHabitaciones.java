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
package domainapp.dom.tipodehabitacion;

import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.tipodehabitacion.TipodeHabitacion.Ecama;
import domainapp.dom.tipodehabitacion.TipodeHabitacion.Etipodeprecio;
import domainapp.dom.tipodehabitacion.TipodeHabitacion.Etipodesexo;


/*import domainapp.dom.simple.TipodeHabitacion.Etipodeprecio; 
import domainapp.dom.simple.TipodeHabitacion.Etipodesexo; 
*/
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = TipodeHabitacion.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class TipodeHabitaciones {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo de Habitación");
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
    public List<TipodeHabitacion> listAll() {
        return repositoryService.allInstances(TipodeHabitacion.class);
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
    public List<TipodeHabitacion> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        TipodeHabitacion.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<TipodeHabitaciones> {
        public CreateDomainEvent(final TipodeHabitaciones source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")
    public TipodeHabitacion create(
            final @ParameterLayout(named="Nombre") String name,
           final @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named="Cantidad de Camas") Ecama ccama,
           final @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named="Tipo de Precio") Etipodeprecio tprecio,
           final @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named="Tipo de Sexo admitido en Habitaciones") Etipodesexo tsexo
           ) {
        final TipodeHabitacion obj = repositoryService.instantiate(TipodeHabitacion.class);
        obj.setName(name);
        obj.setCama(ccama);
        obj.setTprecio(tprecio);
        obj.setTsexo(tsexo);
       
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion
}
