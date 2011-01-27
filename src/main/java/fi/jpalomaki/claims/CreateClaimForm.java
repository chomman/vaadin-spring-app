package fi.jpalomaki.claims;

import com.vaadin.ui.*;
import java.util.Arrays;
import java.math.BigDecimal;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.addon.beanvalidation.BeanValidationForm;
import fi.jpalomaki.claims.Claim.Type;

/**
 * Form for {@link Claim} creation.
 *
 * @author jpalomaki
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class CreateClaimForm extends BeanValidationForm<Claim> {
    
    private final Claim claim;
    
    public CreateClaimForm() {
        super(Claim.class);
        this.claim = Claim.of(Type.LEGITIMATE, "", "", new BigDecimal("0.0"));
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
        HorizontalLayout buttons = new HorizontalLayout();
        Button save = new Button("Save", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    commit();
                    claim.persist();
                    ((ClaimsApp)getApplication()).getTable().add(claim);
                    getApplication().getMainWindow().removeWindow((Window)getParent().getParent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        buttons.addComponent(save);
        getFooter().addComponent(buttons);
    }
}

final class ClaimFormFieldFactory extends DefaultFieldFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {
        if ("type".equals(propertyId)) {
            ComboBox types = new ComboBox("Type", Type.collection());
            types.setNullSelectionAllowed(false);
            return types;
        }
        return super.createField(item, propertyId, uiContext);
    }
}
