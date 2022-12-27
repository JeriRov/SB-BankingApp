package com.ipz.mba.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransferRequestData {
    private String senderCardNumber;
    private String receiverCardNumber;
    private String receiverName;
    private Long sum;
    private String purpose;
    private String category;

    public static boolean isValid(TransferRequestData data) {
        return !(data.getSenderCardNumber() == null || data.getSenderCardNumber().isBlank() ||
                data.getReceiverCardNumber() == null || data.getReceiverCardNumber().isBlank() ||
                data.getSenderCardNumber().equals(data.getReceiverCardNumber()) ||
                data.getReceiverName() == null || data.getReceiverName().isBlank() ||
                data.getSum() == null || data.getSum() <= 0 || data.getPurpose() == null );
    }
}