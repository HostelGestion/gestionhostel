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
package domainapp.dom.caja;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.math.BigDecimal;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MinLength;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.LocalDate;

//import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.reserva.Reserva;
import domainapp.dom.reserva.RepoReserva;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Caja.class
)
@DomainServiceLayout(
        menuOrder = "10",
        named="Caja"
)

public class RepoCaja {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Caja");
    }
    //endregion

    //region > listarCompras (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Caja> listarCompras() {
        return repositoryService.allInstances(Caja.class);
    }
    //endregion

    //region > buscarPorNombre (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Caja> buscarPorNombre(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Caja.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > registrarCompra (action)
    public static class CreateDomainEvent extends ActionDomainEvent<RepoCaja> {
        public CreateDomainEvent(final RepoCaja source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")
    public Caja registrarCompra(
            
            final @ParameterLayout(named="Huésped") Huesped huesped,
            final @ParameterLayout(named="Monto (ARS)") Double monto,
            final @ParameterLayout(named="Concepto") String concepto
    		) {
        final Caja obj = repositoryService.instantiate(Caja.class);
        
        obj.setHuesped(huesped);
        obj.setMonto(monto);
        obj.setConcepto(concepto);
        repositoryService.persist(obj);
       
        obj.setFechaDePago(LocalDate.now());
        return obj;
    }
    
    
 
    @Programmatic
    public List<Huesped> choices0RegistrarCompra() {
        
        return huespedes.listarHuespedes();
   	}

    public Collection<String> choices2RegistrarCompra() {
        return Arrays.asList("Pago de Estadía", "Adicional");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    private Huespedes huespedes;
    //endregion
}
