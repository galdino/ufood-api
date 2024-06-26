package com.galdino.ufood.domain.model.status;

import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;

import java.time.OffsetDateTime;

public class CreatedSituation extends StatusSituation {
    @Override
    public void confirm(UOrder uorder, UOrderStatus status) {
        uorder.setStatus(status);
        uorder.setConfirmedDate(OffsetDateTime.now());
    }

    @Override
    public void cancel(UOrder uorder, UOrderStatus status) {
        uorder.setStatus(status);
        uorder.setCanceledDate(OffsetDateTime.now());
    }
}
