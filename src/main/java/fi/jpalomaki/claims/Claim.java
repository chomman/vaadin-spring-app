package fi.jpalomaki.claims;

import java.util.*;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.springframework.security.access.annotation.Secured;
import static fi.jpalomaki.claims.security.BusinessFunctions.*;
import fi.jpalomaki.claims.persistence.DomainObject;

/**
 * Abstraction for a generic claim.
 *
 * @author jpalomaki
 */
@Entity
public final class Claim extends DomainObject {
    
    @NotNull
    @Enumerated
    private Type type;
    
    @NotNull
    private String summary;
    
    @NotNull
    private String description;
    
    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;
    
    @Override
    @Secured(BF_CLAIM_CREATE)
    public void persist() {
        super.persist();
    }
    
    @Override
    @Secured(BF_CLAIM_UPDATE)
    public void update() {
        super.update();
    }
    
    @Override
    @Secured(BF_CLAIM_DELETE)
    public void remove() {
        super.remove();
    }
    
    /**
     * Create a new {@link Claim} with the given information.
     */
    public static Claim of(Type type, String summary, String description, BigDecimal amount) {
        Claim claim = new Claim();
        claim.type = type;
        claim.summary = summary;
        claim.description = description;
        claim.amount = amount;
        return claim;
    }
    
    /**
     * Find all {@link Claim}s.
     */
    @Secured("BF_CLAIM_READ")
    public static List<Claim> findAll() {
        return entityManager().createQuery("FROM Claim", Claim.class).getResultList();
    }
    
    /**
     * Abstraction for claim type.
     */
    public static enum Type {
        
        FRAUD, LEGITIMATE;
        
        /**
         * Return {@link #values()} as a {@link Collection}.
         */
        public static Collection<Type> collection() {
            return Arrays.asList(values());
        }
    }
}
