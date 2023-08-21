package domainapp.modules.simple.dom.so.vidrio;

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

@Named(SimpleModule.NAMESPACE + ".VidrioServices")
@DomainService(nature = NatureOfService.VIEW)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class VidrioServices {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-plus")
    public Vidrio crearVidrio(@Nombre final String nombre, final int codigo, final double precio, final TipoVidrio tipoVidrio) {
        return repositoryService.persist(Vidrio.withName(nombre, codigo, precio, tipoVidrio));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-search")
    public List<Vidrio> buscarVidrio(@Nombre final String nombre) {
        return repositoryService.allMatches(
                    Query.named(Vidrio.class, Vidrio.NAMED_QUERY__FIND_BY_NAME_EXACT)
                        .withParameter("nombre", nombre));
    }


    public Vidrio findByNameExact(final String nombre) {
        return repositoryService.firstMatch(
                    Query.named(Vidrio.class, Vidrio.NAMED_QUERY__FIND_BY_NAME_EXACT)
                        .withParameter("nombre", nombre))
                .orElse(null);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR,cssClassFa = "fa-list")
    public List<Vidrio> verVidrios() {
        return repositoryService.allInstances(Vidrio.class);
    }

}