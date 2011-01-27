package fi.jpalomaki.vaadinspring.app;

import com.vaadin.ui.Table;
import java.math.BigDecimal;
import fi.jpalomaki.vaadinspring.app.Claim.Type;

/**
 * Table for listing {@link Claim}s.
 *
 * @author jpalomak
 */
@SuppressWarnings("serial")
public final class ClaimTable extends Table {
        
   public ClaimTable() {
       super();
       setPageLength(10);
       addContainerProperty("Type", Type.class, null);
       addContainerProperty("Summary", String.class, null);
       addContainerProperty("Description", String.class, null);
       addContainerProperty("Amount", BigDecimal.class, null);
       for (Claim claim : Claim.findAll()) {
           Object[] row = new Object[] {claim.getType(), claim.getSummary(), claim.getDescription(), claim.getAmount()};
           addItem(row, claim);
       }
       setVisibleColumns(new Object[] {"Type", "Summary", "Description", "Amount"});
   }
}
