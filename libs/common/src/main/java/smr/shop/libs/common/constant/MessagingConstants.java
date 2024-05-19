package smr.shop.libs.common.constant;

public final class MessagingConstants {

    // Topics
    public static final String IMAGE_DELETE_TOPIC = "image-delete-topic";
    public static final String CATEGORY_DELETE_TOPIC = "category-delete-topic";
    public static final String BRAND_DELETE_TOPIC = "brand-delete-topic";


    public static final String COUPON_USAGE_TOPIC = "coupon-usage-topic";


    public static final String PRODUCT_DELETE_TOPIC = "product-delete-topic";
    public static final String PRODUCT_STOCK_DELETE_TOPIC = "product-stock-delete-topic";
    public static final String SHOP_STATUS_CHANGE_TOPIC = "shop-status-change-topic";
    public static final String ORDER_APPROVE_TOPIC = "order-approve-topic";
    public static final String COUPON_DELETE_TOPIC = "coupon-delete-topic";
    public static final String ORDER_CANCEL_TOPIC = "order-cancel-topic";


    // Groups
    public static final String CART_COUPON_DELETE_GROUP = "cart-coupon-delete-group";
    public static final String CART_PRODUCT_DELETE_GROUP = "cart-product-delete-group";

    public static final String PRODUCT_BRAND_DELETE_GROUP = "product-brand-delete-group";
    public static final String PRODUCT_CATEGORY_DELETE_GROUP = "product-category-delete-group";
    public static final String PRODUCT_PRODUCT_STOCK_DELETE_GROUP = "product-product-stock-delete-group";

    public static final String WISHLIST_PRODUCT_DELETE_GROUP = "wishlist-product-delete-group";

    public static final String IMAGE_DELETE_GROUP = "brand-image-delete-group";
    public static final String ORDER_PRODUCT_STOCK_GROUP = "order-product-stock-delete-group";
    public static final String COUPON_SERVICE_COUPON_USAGE_GROUP = "coupon-service-coupon-usage-group";

    private MessagingConstants() {}
}
