package fi.jpalomaki.claims;

import com.vaadin.ui.*;
import java.util.Arrays;
import java.math.BigDecimal;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;
import fi.jpalomaki.claims.Claim.Type;
import com.vaadin.addon.beanvalidation.BeanValidationForm;

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
        setupTable();
        addButtons();
    }
    
    private void setupTable() {
        setImmediate(true);
        setItemDataSource(new BeanItem<Claim>(claim));
        setFormFieldFactory(new ClaimFieldFactory());
        setVisibleItemProperties(Arrays.asList(new String[] {"type", "summary", "description", "amount"}));
    }
    
    private void addButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button reset = new Button("Reset", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                discard();
            }
        });
        reset.setStyleName(BaseTheme.BUTTON_LINK);
        Button save = new Button("Save", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    commit();
                    claim.persist();
                    ((ClaimsApp)getApplication()).getTable().add(claim);
                    Window main = getApplication().getMainWindow();
                    main.removeWindow((Window)getParent().getParent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        buttons.addComponent(save);
        buttons.addComponent(new Label("or"));
        buttons.addComponent(reset);
        getFooter().addComponent(buttons);
    }
}

class ClaimFieldFactory extends DefaultFieldFactory {

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
