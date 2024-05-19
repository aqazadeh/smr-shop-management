package smr.shop.cart.service.helper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.helper.Money;
import smr.shop.libs.common.model.valueobject.CouponDiscountType;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.product.ProductGrpcResponse;

import java.math.BigDecimal;

@Component
public class CartServiceHelper {

    public Money calculateCouponDiscountPrice(ProductGrpcResponse product, CouponGrpcResponse couponDetail) {
        Money money = Money.valueOf(product.getPrice());

        if (CouponDiscountType.valueOf(couponDetail.getDiscountType()) == CouponDiscountType.AMOUNT) {
            Money couponDiscountAmount = Money.valueOf(couponDetail.getAmount());
            money = money.subtract(couponDiscountAmount);

        } else {
            Money amount = money.multiply(couponDetail.getPercentage()).divide(100);
            Money maxDiscountAmount = new Money(BigDecimal.valueOf(couponDetail.getMaxDiscountPrice()));
            if (amount.getAmount().compareTo(maxDiscountAmount.getAmount()) < 0 ) {
                money = money.subtract(amount);
            } else {
                money = money.subtract(maxDiscountAmount);
            }
        }
        return money;
    }


}
