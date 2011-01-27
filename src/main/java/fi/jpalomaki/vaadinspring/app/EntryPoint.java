package fi.jpalomaki.vaadinspring.app;

import java.math.BigDecimal;
import com.vaadin.ui.Window;
import com.vaadin.Application;
import fi.jpalomaki.vaadinspring.app.Claim.Type;

/**
 * Entry point to the vaadin-spring-app.
 * 
 * @author jpalomaki
 */
public final class EntryPoint extends Application {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
        addSomeClaims();
        Window window = new Window("Vaadin-Spring sample app");
        window.addComponent(new ClaimList());
        setMainWindow(window);
    }

    private void addSomeClaims() {
        Claim.of(Type.FRAUD, "Huijausyritys", "Puhumme totta", new BigDecimal("100.99")).persist();
        Claim.of(Type.FRAUD, "Huijausyritys 2", "Puhumme totta tosiaan", new BigDecimal("10000")).persist();
        Claim.of(Type.LEGITIMATE, "Digikamera tippui lattialle", "Ihan varmasti", new BigDecimal("150")).persist();
    }
}
