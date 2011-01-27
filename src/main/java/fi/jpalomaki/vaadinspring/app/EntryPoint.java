package fi.jpalomaki.vaadinspring.app;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.Application;
import fi.jpalomaki.vaadinspring.app.Claim.Type;
import java.math.BigDecimal;

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
        Window window = new Window("Claims (Vaadin-Spring sample app)");
        window.setContent(buildLayout());
        window.addComponent(new Label("Claims"));
        window.addComponent(new ClaimTable());
        setMainWindow(window);
    }
    
    private Layout buildLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }
    
    private void addSomeClaims() {
        Claim.of(Type.FRAUD, "Huijausyritys", "Puhumme totta", new BigDecimal("100.99")).persist();
        Claim.of(Type.FRAUD, "Huijausyritys 2", "Puhumme totta tosiaan", new BigDecimal("10000")).persist();
        Claim.of(Type.LEGITIMATE, "Digikamera tippui lattialle", "Ihan varmasti", new BigDecimal("150")).persist();
    }
}
