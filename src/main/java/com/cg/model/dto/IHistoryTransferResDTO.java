package com.cg.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public interface IHistoryTransferResDTO {

    Long getSenderId();
    String getSenderName();
    Long getRecipientId();
    String getRecipientName();

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    Date getCreatedAt();
    BigDecimal getTransferAmount();
    Long getFees();
    BigDecimal getFeesAmount();
}
