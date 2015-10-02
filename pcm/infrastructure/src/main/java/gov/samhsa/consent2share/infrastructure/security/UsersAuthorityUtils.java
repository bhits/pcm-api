package gov.samhsa.consent2share.infrastructure.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UsersAuthorityUtils extends AuthorityUtils{
	public static Set<GrantedAuthority> createAuthoritySet(String... roles) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
