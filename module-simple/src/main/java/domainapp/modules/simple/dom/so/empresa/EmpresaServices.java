package domainapp.modules.simple.dom.so.empresa;

import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jdo.JDOQLTypedQuery;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.BookmarkPolicy;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.DomainServiceLayout;
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

@Named(SimpleModule.NAMESPACE + ".EmpresaServices")
@DomainService(nature = NatureOfService.VIEW)
@DomainServiceLayout(named = "Empresa", menuBar = DomainServiceLayout.MenuBar.PRIMARY)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class EmpresaServices {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-plus")
    public Empresa crearEmpresa(@Nombre final String nombre, final TipoEmpresa tipoEmpresa, final String domicilio, final long telefono) {
        return repositoryService.persist(Empresa.withName(nombre, tipoEmpresa, domicilio, telefono));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR, cssClassFa = "fa-search")
    public List<Empresa> buscarEmpresa(@Nombre final String nombre) {
        return repositoryService.allMatches(
                    Query.named(Empresa.class, Empresa.NAMED_QUERY__FIND_BY_NOMBRE)
                        .withParameter("nombre", nombre));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR,cssClassFa = "fa-list")
    public List<Empresa> verEmpresas() {
        return repositoryService.allInstances(Empresa.class);
    }

}
