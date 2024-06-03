package smr.shop.libs.common.constant;

public final class MessagingConstants {

    // Topics
    public static final String CATEGORY_DELETE_TOPIC = "category-delete-topic";
    public static final String BRAND_DELETE_TOPIC = "brand-delete-topic";

    public static final String COUPON_USAGE_TOPIC = "coupon-usage-topic";
    public static final String COUPON_DELETE_TOPIC = "coupon-delete-topic";

    public static final String DISCOUNT_DELETE_TOPIC = "discount-delete-topic";

    public static final String ORDER_APPROVE_TOPIC = "order-approve-topic";
    public static final String ORDER_CANCEL_TOPIC = "order-cancel-topic";

    public static final String PRODUCT_REVIEW_UPDATE_TOPIC = "product-review-update-topic";
    public static final String PRODUCT_REVIEW_CHANGE_TOPIC = "product-review-change-topic";

    public static final String PRODUCT_DELETE_TOPIC = "product-delete-topic";

    public static final String CREATE_STOCK_TOPIC = "create-stock-topic";

    public static final String PRODUCT_STOCK_DELETE_TOPIC = "product-stock-delete-topic";

    public static final String SHOP_STATUS_CHANGE_TOPIC = "shop-status-change-topic";

    public static final String IMAGE_DELETE_TOPIC = "image-delete-topic";



    // Groups Cart Service
    public static final String CART_SERVICE_COUPON_DELETE_GROUP = "cart-service-coupon-delete-group";
    public static final String CART_SERVICE_PRODUCT_DELETE_GROUP = "cart-service-product-delete-group";
    public static final String CART_SERVICE_PRODUCT_STOCK_DELETE_GROUP = "cart-service-product-stock-delete-group";

    // Groups Coupon Service
    public static final String COUPON_SERVICE_SHOP_STATUS_CHANGE_GROUP = "shop-service-shop-status-change-group";
    public static final String COUPON_SERVICE_COUPON_USAGE_GROUP = "coupon-service-coupon-usage-group";

    public static final String FLASH_SERVICE_PRODUCT_DELETE_GROUP = "flash-service-product-delete-group";

    public static final String ORDER_PRODUCT_STOCK_GROUP = "order-product-stock-delete-group";

    public static final String PRODUCT_REVIEW_SERVICE_UPDATE_REVIEW_GROUP = "product-review-service-update-review-group";

    public static final String PRODUCT_SERVICE_CATEGORY_DELETE_GROUP = "product-service-category-delete-group"; ;
    public static final String PRODUCT_SERVICE_BRAND_DELETE_GROUP = "product-service-brand-delete-group";
    public static final String PRODUCT_SERVICE_DISCOUNT_DELETE_GROUP = "product-service-discount-delete-group";

    public static final String PRODUCT_PRODUCT_STOCK_DELETE_GROUP = "product-product-stock-delete-group";
    public static final String WISHLIST_PRODUCT_DELETE_GROUP = "wishlist-product-delete-group";


    public static final String UPLOAD_SERVICE_IMAGE_DELETE_GROUP = "upload-service-brand-image-delete-group";

    private MessagingConstants() {}
}
