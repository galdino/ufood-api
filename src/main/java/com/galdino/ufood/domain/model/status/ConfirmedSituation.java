package com.galdino.ufood.domain.model.status;

import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;

import java.time.OffsetDateTime;

public class ConfirmedSituation extends StatusSituation {

    @Override
    public void deliver(UOrder uorder, UOrderStatus status) {
        uorder.setStatus(status);
        uorder.setDeliveredDate(OffsetDateTime.now());
    }

}
