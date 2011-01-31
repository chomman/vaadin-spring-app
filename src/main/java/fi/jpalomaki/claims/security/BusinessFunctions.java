package fi.jpalomaki.claims.security;

/**
 * Available business functions.
 *
 * @author jpalomaki
 */
public final class BusinessFunctions {
    
    private BusinessFunctions() {
        // Non-instantiable
    }
    
    /** Prefix for business function authorities */
    public static final String BF_AUTHORITY_PREFIX = "BF_";
    
    /** Permission to read claim data */
    public static final String BF_CLAIM_READ = "BF_CLAIM_READ";
    
    /** Permission to create a claim */
    public static final String BF_CLAIM_CREATE = "BF_CLAIM_CREATE";
    
    /** Permission to update a claim */
    public static final String BF_CLAIM_UPDATE = "BF_CLAIM_UPDATE";
    
    /** Permission to delete a claim */
    public static final String BF_CLAIM_DELETE = "BF_CLAIM_DELETE";   
}
