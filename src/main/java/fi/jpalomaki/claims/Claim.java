package fi.jpalomaki.claims;

import java.util.*;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    public static List<Claim> findAll() {
        return entityManager().createQuery("FROM Claim", Claim.class).getResultList();
    }
    
    /**
     * Abstraction for claim type.
     */
    public static enum Type {
        
        FRAUD, LEGITIMATE;
        
        public static Collection<Type> collection() {
            return Arrays.asList(values());
        }
    }
}
