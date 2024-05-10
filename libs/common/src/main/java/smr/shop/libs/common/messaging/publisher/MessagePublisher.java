package smr.shop.libs.common.messaging.publisher;

import smr.shop.libs.common.messaging.BaseMessageModel;

public interface MessagePublisher<T extends BaseMessageModel> {
    void publish(T messageModel);
}
