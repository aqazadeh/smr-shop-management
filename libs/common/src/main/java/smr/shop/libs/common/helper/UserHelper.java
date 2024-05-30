package smr.shop.libs.common.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.UUID;

public class UserHelper {
    public static UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return UUID.fromString(authentication.getName());
    }

    public static Boolean isSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(g -> g.getAuthority().equals("ROLE_SELLER"));

    }

    public static Boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));

    }
}
