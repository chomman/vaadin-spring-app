package fi.jpalomaki.claims;

import com.vaadin.ui.*;

/**
 * Button for triggering {@link Claim} creation.
 *
 * @author jpalomaki
 */
@SuppressWarnings("serial")
public final class CreateClaimButton extends Button {    
    
    public CreateClaimButton() {
        super();
        setCaption("Create");
        addClickListener();
    }
    
    private void addClickListener() {
        addListener(new ClickListener() {  
            @Override
            public void buttonClick(ClickEvent event) {
                Window parent = getApplication().getMainWindow();
                parent.addWindow(newCreateFormWindow());
            }
        });
    }
    
    private Window newCreateFormWindow() {
        Window window = new Window();
        window.setModal(true);
        window.setResizable(false);
        window.setCaption("Create new claim");
        window.addComponent(new CreateClaimForm());
        VerticalLayout layout = (VerticalLayout)window.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        return window;
    }
}
