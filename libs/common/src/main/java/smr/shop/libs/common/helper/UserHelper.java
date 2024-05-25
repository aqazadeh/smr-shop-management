package smr.shop.libs.common.helper;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class UserHelper {
      public static UUID getUserId() {
          Authentication authentication = SecurityContextHolder.getContext()
                  .getAuthentication();
          return UUID.fromString(authentication.getName());
      }
}
