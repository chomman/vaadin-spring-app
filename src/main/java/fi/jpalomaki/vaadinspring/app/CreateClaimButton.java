package fi.jpalomaki.vaadinspring.app;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Button for creating a new {@link Claim}.
 *
 * @author jpalomak
 */
@SuppressWarnings({"unchecked", "serial"})
public final class CreateClaimButton extends Button implements Component {    
    
    private final Window parent;
    private final ClaimTable table;
    
    public CreateClaimButton(Window parent, ClaimTable table) {
        super();
        this.parent = parent;
        this.table = table;
        setCaption("Create");
        addClickListener();
    }
    
    private void addClickListener() {
        addListener(new ClickListener() {  
            @Override
            public void buttonClick(ClickEvent event) {
                parent.addWindow(newCreateFormWindow());
            }
        });
    }
    
    private Window newCreateFormWindow() {
        Window window = new Window();
        window.setModal(true);
        window.setCaption("Create new claim");
        window.addComponent(new CreateClaimForm(table));
        VerticalLayout layout = (VerticalLayout)window.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        return window;
    }
}
