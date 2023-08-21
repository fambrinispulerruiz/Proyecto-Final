package domainapp.modules.simple.dom.so.vehiculo;

import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jdo.JDOQLTypedQuery;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.query.Query;
import org.apache.causeway.applib.services.repository.RepositoryService;
import org.apache.causeway.persistence.jdo.applib.services.JdoSupportService;

import lombok.RequiredArgsConstructor;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.types.Nombre;

@Named(SimpleModule.NAMESPACE + ".VehiculoServices")
@DomainService(nature = NatureOfService.VIEW)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class VehiculoServices {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-plus")
    public Vehiculo crearVehiculo(final String marca, final String modelo) {
        return repositoryService.persist(Vehiculo.withName(marca, modelo));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-search")
    public List<Vehiculo> buscarVehiculo(final String marca) {
        return repositoryService.allMatches(
                    Query.named(Vehiculo.class, Vehiculo.NAMED_QUERY__FIND_BY_MARCA)
                        .withParameter("marca", marca));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR,cssClassFa = "fa-list")
    public List<Vehiculo> verVehiculos() {
        return repositoryService.allInstances(Vehiculo.class);
    }


}