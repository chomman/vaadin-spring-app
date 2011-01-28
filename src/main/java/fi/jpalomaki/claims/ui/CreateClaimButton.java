package fi.jpalomaki.claims.ui;

import com.vaadin.ui.*;
import fi.jpalomaki.claims.Claim;

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
                parent.addWindow(EditClaimForm.newCreateClaimFormWindow());
            }
        });
    }
}
