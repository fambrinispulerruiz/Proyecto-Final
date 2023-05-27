package domainapp.modules.simple.fixture;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;

import org.apache.causeway.applib.services.clock.ClockService;
import org.apache.causeway.applib.services.registry.ServiceRegistry;
import org.apache.causeway.applib.value.Blob;
import org.apache.causeway.testing.fakedata.applib.services.FakeDataService;
import org.apache.causeway.testing.fixtures.applib.personas.BuilderScriptWithResult;
import org.apache.causeway.testing.fixtures.applib.personas.Persona;
import org.apache.causeway.testing.fixtures.applib.setup.PersonaEnumPersistAll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.experimental.Accessors;

import domainapp.modules.simple.dom.so.Vidrio;
import domainapp.modules.simple.dom.so.Vidrios;

@RequiredArgsConstructor
public enum Vidrio_persona
implements Persona<Vidrio, Vidrio_persona.Builder> {

    FOO("Foo", "Foo.pdf"),
    BAR("Bar", "Bar.pdf"),
    BAZ("Baz", null),
    FRODO("Frodo", "Frodo.pdf"),
    FROYO("Froyo", null),
    FIZZ("Fizz", "Fizz.pdf"),
    BIP("Bip", null),
    BOP("Bop", null),
    BANG("Bang", "Bang.pdf"),
    BOO("Boo", null);

    private final String name;
    private final String contentFileName;

    @Override
    public Builder builder() {
        return new Builder().setPersona(this);
    }

    @Override
    public Vidrio findUsing(final ServiceRegistry serviceRegistry) {
        return serviceRegistry.lookupService(Vidrios.class).map(x -> x.findByNameExact(name)).orElseThrow();
    }

    @Accessors(chain = true)
    public static class Builder extends BuilderScriptWithResult<Vidrio> {

        @Getter @Setter private Vidrio_persona persona;

        @Override
        protected Vidrio buildResult(final ExecutionContext ec) {

            val vidrio = wrap(vidrios).create(persona.name);

            if (persona.contentFileName != null) {
                val bytes = toBytes(persona.contentFileName);
                val attachment = new Blob(persona.contentFileName, "application/pdf", bytes);
                vidrio.updateAttachment(attachment);
            }

            vidrio.setLastCheckedIn(clockService.getClock().nowAsLocalDate().plusDays(fakeDataService.ints().between(-10, +10)));

            return vidrio;
        }

        @SneakyThrows
        private byte[] toBytes(String fileName){
            InputStream inputStream = new ClassPathResource(fileName, getClass()).getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return buffer.toByteArray();
        }

        // -- DEPENDENCIES

        @Inject Vidrios vidrios;
        @Inject ClockService clockService;
        @Inject FakeDataService fakeDataService;
    }

    public static class PersistAll
            extends PersonaEnumPersistAll<Vidrio, Vidrio_persona, Builder> {
        public PersistAll() {
            super(Vidrio_persona.class);
        }
    }


}
