package smr.shop.product.service.messaging.listener;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
public class ShopDeleteMessageListener implements MessageListener<ShopMessageModel> {

    private final ProductService productService;

    public ShopDeleteMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void receive(ShopMessageModel message, String key, Integer partition, Long offset) {
        productService.deleteProductsByShop(message);
    }
}
