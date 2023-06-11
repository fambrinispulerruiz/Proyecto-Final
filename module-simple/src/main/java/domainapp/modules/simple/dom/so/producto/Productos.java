package domainapp.modules.simple.dom.so.producto;

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

@Named(SimpleModule.NAMESPACE + ".Productos")
@DomainService(nature = NatureOfService.VIEW)
@Priority(PriorityPrecedence.EARLY)
@RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Productos {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Producto create(
            @Nombre final String nombre, final int precio, final int stock, final int cantidad) {
        return repositoryService.persist(Producto.withName(nombre, precio, stock, cantidad));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Producto> findByName(
            @Nombre final String name
            ) {
        return repositoryService.allMatches(
                    Query.named(Producto.class, Producto.NAMED_QUERY__FIND_BY_NAME_LIKE)
                        .withParameter("nombre", name));
    }


    public Producto findByNameExact(final String name) {
        return repositoryService.firstMatch(
                    Query.named(Producto.class, Producto.NAMED_QUERY__FIND_BY_NAME_EXACT)
                        .withParameter("nombre", name))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Producto> listAll() {
        return repositoryService.allInstances(Producto.class);
    }



//    public void ping() {
//        JDOQLTypedQuery<SimpleObject> q = jdoSupportService.newTypesafeQuery(SimpleObject.class);
//        final QSimpleObject candidate = QSimpleObject.candidate();
//        q.range(0,2);
//        q.orderBy(candidate.name.asc());
//        q.executeList();
//    }

}
