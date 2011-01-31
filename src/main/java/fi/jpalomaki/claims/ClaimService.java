package fi.jpalomaki.claims;

import java.math.BigDecimal;
import java.util.Collection;
import fi.jpalomaki.claims.Claim.Type;
import org.springframework.stereotype.Service;
import org.springframework.security.access.annotation.Secured;
import static fi.jpalomaki.claims.security.BusinessFunctions.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for working with claims.
 *
 * @author jpalomaki
 */
@Service
public final class ClaimService {
    
    /**
     * Merge the given claims into one and return the result.
     */
    @Transactional
    @Secured(BF_CLAIM_UPDATE)
    public Claim merge(Collection<Claim> claims) {
        BigDecimal amount = BigDecimal.ZERO;
        for (Claim claim : claims) {
            amount = amount.add(claim.getAmount());
            claim.remove();
        }
        Claim merged = Claim.of(Type.LEGITIMATE, "Merged claim", "Merged claim", amount);
        merged.persist();
        return merged;
    }
}
