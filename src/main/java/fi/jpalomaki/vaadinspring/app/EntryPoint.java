package fi.jpalomaki.vaadinspring.app;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.Application;

/**
 * Entry point to the vaadin-spring-app.
 * 
 * @author jpalomaki
 */
public final class EntryPoint extends Application {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
        Window window = new Window("Claims");
        window.setContent(buildLayout());
        window.addComponent(new Label("Claims"));
        ClaimTable table = new ClaimTable();
        window.addComponent(table);
        window.addComponent(new CreateClaimButton(window, table));
        setMainWindow(window);
    }
    
    private Layout buildLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);
        return layout;
    }
}
