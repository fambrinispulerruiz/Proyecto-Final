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

@Named(SimpleModule.NAMESPACE + ".Empresas")
@DomainService(nature = NatureOfService.VIEW)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Empresas {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Empresa create(
            @Nombre final String nombre, final TipoEmpresa tipoEmpresa) {
        return repositoryService.persist(Empresa.withName(nombre, tipoEmpresa));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Empresa> findByName(
            @Nombre final String name
            ) {
        return repositoryService.allMatches(
                    Query.named(Empresa.class, Empresa.NAMED_QUERY__FIND_BY_NAME_LIKE)
                        .withParameter("nombre", name));
    }


    public Empresa findByNameExact(final String name) {
        return repositoryService.firstMatch(
                    Query.named(Empresa.class, Empresa.NAMED_QUERY__FIND_BY_NAME_EXACT)
                        .withParameter("nombre", name))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Empresa> listAll() {
        return repositoryService.allInstances(Empresa.class);
    }



//    public void ping() {
//        JDOQLTypedQuery<SimpleObject> q = jdoSupportService.newTypesafeQuery(SimpleObject.class);
//        final QSimpleObject candidate = QSimpleObject.candidate();
//        q.range(0,2);
//        q.orderBy(candidate.name.asc());
//        q.executeList();
//    }

}
