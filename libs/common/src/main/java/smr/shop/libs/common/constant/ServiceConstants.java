package smr.shop.libs.common.constant;

import java.time.ZoneId;

public final class ServiceConstants {

    public static final Integer pageSize = 10;
    public static final String UTC = "UTC";
    public static final ZoneId ZONE_ID =ZoneId.of(UTC);

    private ServiceConstants() {
    }
}
