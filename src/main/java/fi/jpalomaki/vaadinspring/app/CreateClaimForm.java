package fi.jpalomaki.vaadinspring.app;

import java.math.BigDecimal;
import java.util.Arrays;
import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;
import fi.jpalomaki.vaadinspring.app.Claim.Type;

/**
 * CreateClaimForm.
 *
 * @author jpalomaki
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class CreateClaimForm extends BeanValidationForm<Claim> implements Component {
    
    private final Claim claim;
    private final ClaimTable table;
    
    public CreateClaimForm(ClaimTable table) {
        super(Claim.class);
        setImmediate(true);
        this.claim = Claim.of(Type.LEGITIMATE, "", "", new BigDecimal("0"));
        this.table = table;
        setItemDataSource(new BeanItem<Claim>(claim));
        setFormFieldFactory(new ClaimFieldFactory());
        setVisibleItemProperties(Arrays.asList(new String[] {"type", "summary", "description", "amount"}));
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
                    CreateClaimForm.this.table.add(claim);
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
