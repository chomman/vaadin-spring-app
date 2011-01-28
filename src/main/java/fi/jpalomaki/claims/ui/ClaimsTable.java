package fi.jpalomaki.claims.ui;

import java.util.*;
import com.vaadin.ui.Table;
import java.math.BigDecimal;
import com.vaadin.event.Action;
import fi.jpalomaki.claims.Claim;
import fi.jpalomaki.claims.Claim.Type;
import fi.jpalomaki.claims.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Table for listing {@link Claim}s.
 * 
 * @author jpalomaki
 */
@Configurable
public final class ClaimsTable extends Table {
    
    private static final long serialVersionUID = 1L;
    
    private static final Action EDIT = new Action("Edit");
    private static final Action MERGE = new Action("Merge");
    private static final Action DELETE = new Action("Delete");
    
    @Autowired
    private ClaimService service;
    
    public ClaimsTable() {
        super();
        setupTable();
        addContextMenu();
        populateWithData();  
    }

    private void setupTable() {
        setPageLength(10);
        setSelectable(true);
        setMultiSelect(true);
        setWidth("75%");
        addContainerProperty("Id", Long.class, null);
        addContainerProperty("Version", Long.class, null);
        addContainerProperty("Type", Type.class, null);
        addContainerProperty("Summary", String.class, null);
        addContainerProperty("Description", String.class, null);
        addContainerProperty("Amount", BigDecimal.class, null);
        addContainerProperty("CreationDate", Date.class, null);
        addContainerProperty("CreatedBy", String.class, null);
        addContainerProperty("ModificationDate", Date.class, null);
        addContainerProperty("ModifiedBy", String.class, null);
        setVisibleColumns(new Object[] {"Id", "Version", "Type", "Summary", "Description", "Amount", "CreationDate", 
                "CreatedBy", "ModificationDate", "ModifiedBy"});
    }
    
    @SuppressWarnings("serial")
    private void addContextMenu() {
        addActionHandler(new Action.Handler() {
            
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[] {EDIT, MERGE, DELETE};
            }
            
            
            @Override
            @SuppressWarnings("unchecked")
            public void handleAction(Action action, Object sender, Object target) {
                if (action == EDIT) {
                    getApplication().getMainWindow().addWindow(EditClaimForm.newEditClaimFormWindowFor((Claim)target));
                }
                if (action == MERGE) {
                    System.out.println(getValue().getClass());
                    Set<Claim> selectedClaims = (Set<Claim>)getValue();
                    if (selectedClaims.size() > 1) {
                        Claim merged = service.merge(selectedClaims);
                        for (Claim selected : selectedClaims) {
                            removeItem(selected);
                        }
                        add(merged);
                    }
                }
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
        Object[] row = new Object[] {claim.getId(), claim.getVersion(), claim.getType(), claim.getSummary(), 
                claim.getDescription(), claim.getAmount(), claim.getCreationDate(), claim.getCreatedBy(),
                claim.getModificationDate(), claim.getModifiedBy()};
        addItem(row, claim);
    }
    
    public void update(Claim claim) {
        removeItem(claim);
        add(claim);
    }
}
