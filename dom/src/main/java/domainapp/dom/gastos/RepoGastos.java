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
package domainapp.dom.gastos;

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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
/*
import domainapp.dom.habitacion.Habitacion;
import domainapp.dom.huesped.Huesped;
import domainapp.dom.huesped.Huespedes;
import domainapp.dom.reserva.Reserva;
import domainapp.dom.reserva.Reservas;*/
import org.apache.isis.applib.value.Time;

import com.fasterxml.jackson.core.Version;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = gastos.class
)
@DomainServiceLayout(
        menuOrder = "10",
        named="Gastos"
)
public class RepoGastos {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Gastos");
    }
    //endregion

    //region > listarGastos (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<gastos> listarGastos() {
        return repositoryService.allInstances(gastos.class);
      //  return repositoryService.Version(Version.class);
        //return Time.DAY.allInstances(java.sql.Time.class);
        
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
    public List<gastos> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        gastos.class,
                        "findByName",
                        "name", name));
    }
    //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<RepoGastos> {
        public CreateDomainEvent(final RepoGastos source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(sequence = "3")
    public gastos crearMovimiento(
            final @ParameterLayout(named="N° Factura") String name,
           // final @ParameterLayout(named="Huésped") Huesped huesped,
            final @ParameterLayout(named="Proveedor") String proveedor,
            final @ParameterLayout(named="Monto (ARS)") Double monto,
            final @ParameterLayout(named="Categoria") String Categoria
            
    		) {
        final gastos obj = repositoryService.instantiate(gastos.class);
        obj.setName(name);
       // obj.setHuesped(huesped);
        obj.setProveedor(proveedor);
        obj.setMonto(monto);
        obj.setCategoria(Categoria);
        repositoryService.persist(obj);
        return obj;
    }
    
 /*   @Programmatic
    public List<Huesped> choices1CrearMovimiento() {
        
        return huespedes.listAll();
   	}*/

    public Collection<String> choices3CrearMovimiento() {
        return Arrays.asList("Almacen", "Lavanderia", "Bebidas", "Sueldos", "Limpieza", "Gastos Varios");
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

   // @javax.inject.Inject
  //  private Huespedes huespedes;
    //endregion
}
