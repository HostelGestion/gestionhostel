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
package domainapp.dom.caja;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.huesped.Huesped;
import domainapp.dom.reserva.Reserva;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Caja"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.Caja "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.Caja "
                        + "WHERE name.indexOf(:name) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Caja_name_UNQ", members = {"name"})
@DomainObject
public class Caja implements Comparable<Caja> {

    public static final int NAME_LENGTH = 40;


    public TranslatableString title() {
        return TranslatableString.tr("Object: {name}", "name", getName());
    }


    public static class NameDomainEvent extends PropertyDomainEvent<Caja,String> {}
    @javax.jdo.annotations.Column(
            allowsNull="false",
            length = NAME_LENGTH
    )
    @Property(
        domainEvent = NameDomainEvent.class
    )
    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public TranslatableString validateName(final String name) {
        return name != null && name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
    }
    
    private Huesped huesped;
    @javax.jdo.annotations.Column(allowsNull="false")
    public Huesped getHuesped() {
        return huesped;
    }
    @javax.jdo.annotations.Column(allowsNull="false")
    public void setHuesped(final Huesped huesped) {
        this.huesped = huesped;
    }

    private Double monto;
    @javax.jdo.annotations.Column(allowsNull="false")
    public Double getMonto() {
        return monto;
    }
    public void setMonto(final Double monto) {
        this.monto = monto;
    }
    
    private String concepto;
    @javax.jdo.annotations.Column(allowsNull="false")
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }


    public static class DeleteDomainEvent extends ActionDomainEvent<Caja> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }



    @Override
    public int compareTo(final Caja other) {
        return ObjectContracts.compare(this, other, "name");
    }


    @javax.inject.Inject
    RepositoryService repositoryService;

}
