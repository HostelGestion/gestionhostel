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
package domainapp.dom.simple;

import java.net.MalformedURLException;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = SimpleObject.class
)
@DomainServiceLayout(
		named="Ayuda",
        menuOrder = "10"
)
public class SimpleObjects {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Ayuda");
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
    @Programmatic
    public List<SimpleObject> listAll() {
        return repositoryService.allInstances(SimpleObject.class);
    }
    //endregion
    
    
    
    	

    
    
    

    @Action(semantics=SemanticsOf.SAFE)
    @MemberOrder(name="Ayuda", sequence="1")
    public java.net.URL verAyuda() throws MalformedURLException {
        return new java.net.URL(getLink());
    }
    
    
    
    
    
    
    

    private String getLink() {
		// TODO Auto-generated method stub
		return "http://www.google.com";
	}








	//region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<SimpleObjects> {
        public CreateDomainEvent(final SimpleObjects source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    
	@Action(
            domainEvent = CreateDomainEvent.class
    )	
    @MemberOrder(sequence = "3")
	@Programmatic
    public SimpleObject create(
            final @ParameterLayout(named="Name") String name) {
        final SimpleObject obj = repositoryService.instantiate(SimpleObject.class);
        obj.setName(name);
        repositoryService.persist(obj);
        return obj;
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion
}
