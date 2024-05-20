package smr.shop.libs.common.dto.message;

import smr.shop.libs.common.messaging.BaseMessageModel;

import java.time.ZonedDateTime;
import java.util.UUID;

public class FlashMessageModel implements BaseMessageModel {
    private UUID id;

    private String title;

    private String imageId;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String slug;

}
