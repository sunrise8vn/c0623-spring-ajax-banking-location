package com.cg.model.dto;

import com.cg.utils.AppUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class HistoryTransferResDTO {


//    @Autowired

    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createdAt;

    private String createdAt;
    private BigDecimal transferAmount;
    private Long fees;
    private BigDecimal feesAmount;

    public HistoryTransferResDTO(Long senderId, String senderName, Long recipientId, String recipientName, LocalDateTime createdAt, BigDecimal transferAmount, Long fees, BigDecimal feesAmount) {
        AppUtils appUtils = new AppUtils();

        this.senderId = senderId;
        this.senderName = senderName;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.createdAt = appUtils.convertLocalDateTimeToString(createdAt);
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
    }
}
