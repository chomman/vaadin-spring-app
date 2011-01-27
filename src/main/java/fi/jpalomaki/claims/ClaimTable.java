package fi.jpalomaki.claims;

import com.vaadin.ui.Table;
import java.math.BigDecimal;
import com.vaadin.event.Action;
import fi.jpalomaki.claims.Claim.Type;

/**
 * Table for listing {@link Claim}s.
 * 
 * @author jpalomaki
 */
public final class ClaimTable extends Table {
    
    private static final long serialVersionUID = 1L;
    private static final Action DELETE = new Action("Delete");
    
    public ClaimTable() {
        super();
        setupTable();
        addContextMenu();
        populateWithData();  
    }

    private void setupTable() {
        setPageLength(10);
        addContainerProperty("Type", Type.class, null);
        addContainerProperty("Summary", String.class, null);
        addContainerProperty("Description", String.class, null);
        addContainerProperty("Amount", BigDecimal.class, null);
        setVisibleColumns(new Object[] { "Type", "Summary", "Description", "Amount" });
    }
    
    @SuppressWarnings("serial")
    private void addContextMenu() {
        addActionHandler(new Action.Handler() {
            
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[] {DELETE};
            }
            
            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (action == DELETE) {
                    ((Claim)target).remove();
                    removeItem(target);
                }
            }
        });
    }
    
    private void populateWithData() {
        for (Claim claim : Claim.findAll()) {
            add(claim);
        }
    }
    
    void add(Claim claim) {
        Object[] row = new Object[] {claim.getType(), claim.getSummary(), claim.getDescription(), claim.getAmount()};
        addItem(row, claim);
    }
}
