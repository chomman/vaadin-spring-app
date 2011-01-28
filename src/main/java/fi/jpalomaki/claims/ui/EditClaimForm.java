package fi.jpalomaki.claims.ui;

import com.vaadin.ui.*;

import java.math.BigDecimal;
import java.util.Arrays;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.addon.beanvalidation.BeanValidationForm;
import fi.jpalomaki.claims.Claim;
import fi.jpalomaki.claims.Claim.Type;

/**
 * Form for editing a {@link Claim}.
 *
 * @author jpalomaki
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class EditClaimForm extends BeanValidationForm<Claim> {
    
    private final Claim claim;
    
    public EditClaimForm(Claim claim) {
        super(Claim.class);
        this.claim = claim;
        setupFormFields();
        addButtons();
    }
    
    private void setupFormFields() {
        setImmediate(true);
        setItemDataSource(new BeanItem<Claim>(claim));
        setFormFieldFactory(new ClaimFormFieldFactory());
        setVisibleItemProperties(Arrays.asList(new String[] {"type", "summary", "description", "amount"}));
    }
    
    private void addButtons() {
        Button saveButton = newSaveButton();
        Label orLabel = new Label("or");
        Button cancelButton = newCancelButton();
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponent(saveButton);
        buttons.addComponent(orLabel);
        buttons.addComponent(cancelButton);
        buttons.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);
        buttons.setComponentAlignment(orLabel, Alignment.MIDDLE_CENTER);
        buttons.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
        getFooter().addComponent(buttons);
    }

    private Button newSaveButton() {
        return new Button("Save", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    saveClaim();
                    updateTable();
                    closeWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    
    private Button newCancelButton() {
        Button button = new Button("Cancel", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    discard();
                    closeWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        button.setStyleName(BaseTheme.BUTTON_LINK);
        return button;
    }
    
    private void saveClaim() {
        commit();
        if (claim.getId() == null) {
            claim.persist();
        } else {
            claim.update();
        }
    }
    
    private void updateTable() {
        ClaimsTable table = ((ClaimsApp)getApplication()).getTable();
        if (!table.containsId(claim)) {
            table.add(claim);
        } else {
            table.update(claim);
        }
    }
    
    private void closeWindow() {
        getApplication().getMainWindow().removeWindow((Window)getParent().getParent());
    }
      
    public static Window newCreateClaimFormWindow() {
        return newClaimFormWindowWith("Create new claim", Claim.of(Type.LEGITIMATE, "", "", new BigDecimal("0.0")));
    }
    
    public static Window newEditClaimFormWindowFor(Claim claim) {
        return newClaimFormWindowWith("Edit claim", claim);
    }
    
    private static Window newClaimFormWindowWith(String title, Claim claim) {
        Window window = new Window();
        window.setModal(true);
        window.setResizable(false);
        window.setCaption(title);
        window.addComponent(new EditClaimForm(claim));
        VerticalLayout layout = (VerticalLayout)window.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeUndefined();
        return window;
    }
}

final class ClaimFormFieldFactory extends DefaultFieldFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {
        if ("type".equals(propertyId)) {
            NativeSelect select = new NativeSelect("Type", Type.collection());
            select.setNullSelectionAllowed(false);
            return select;
        }
        return super.createField(item, propertyId, uiContext);
    }
}
