package smr.shop.libs.common.messaging.listener;

import smr.shop.libs.common.messaging.BaseMessageModel;

public interface MessageListener<T extends BaseMessageModel> {
    void receive(T message, String key, Integer partition, Long offset);
//    void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
