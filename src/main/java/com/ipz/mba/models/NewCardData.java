package com.ipz.mba.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCardData {
    private boolean isVisa;
    private long ownerId;
    private long typeId;
    private String currency, pin;
}
