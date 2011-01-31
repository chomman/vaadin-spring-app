package fi.jpalomaki.claims;

import java.math.BigDecimal;

/**
 * Getters and setters for {@link Claim}.
 *
 * @author jpalomaki
 */
privileged aspect ClaimBean {
    
    public Type Claim.getType() {
        return type;
    }

    public void Claim.setType(Type type) {
        this.type = type;
    }

    public String Claim.getSummary() {
        return summary;
    }

    public void Claim.setSummary(String summary) {
        this.summary = summary;
    }

    public String Claim.getDescription() {
        return description;
    }

    public void Claim.setDescription(String description) {
        this.description = description;
    }

    public BigDecimal Claim.getAmount() {
        return amount;
    }

    public void Claim.setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
