package fi.jpalomaki.claims;

import com.vaadin.ui.*;
import com.vaadin.Application;

/**
 * Entry point to the Claims app.
 * 
 * @author jpalomaki
 */
public final class ClaimsApp extends Application {

    private static final long serialVersionUID = 1L;
    
    private final ClaimsTable table = new ClaimsTable();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        Window window = buildWindow();
        window.addComponent(new Label("All claims"));
        window.addComponent(table);
        window.addComponent(new CreateClaimButton());
        setMainWindow(window);
    }
    
    private Window buildWindow() {
        Window window = new Window("Claims");
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        window.setContent(layout);
        return window;
    }
    
    ClaimsTable getTable() {
        return table;
    }
}
