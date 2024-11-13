package com.galdino.ufood.domain.model.status;

import com.galdino.ufood.domain.event.UOrderCanceledEvent;
import com.galdino.ufood.domain.event.UOrderConfirmedEvent;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;

import java.time.OffsetDateTime;

public class CreatedSituation extends StatusSituation {
    @Override
    public void confirm(UOrder uorder, UOrderStatus status) {
        uorder.setStatus(status);
        uorder.setConfirmedDate(OffsetDateTime.now());
        uorder.registerEventUOrder(new UOrderConfirmedEvent(uorder));
    }

    @Override
    public void cancel(UOrder uorder, UOrderStatus status) {
        uorder.setStatus(status);
        uorder.setCanceledDate(OffsetDateTime.now());
        uorder.registerEventUOrder(new UOrderCanceledEvent(uorder));
    }
}
