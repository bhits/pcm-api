package gov.samhsa.mhc.common.oauth2;

public class OAuth2ScopeUtils {
    private OAuth2ScopeUtils() {
    }

    public static final String hasScope(String scope) {
        return "#oauth2.hasScope('" + scope + "')";
    }
}
