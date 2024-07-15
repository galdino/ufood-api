package com.galdino.ufood.domain.model.status;

import com.galdino.ufood.domain.exception.BusinessException;
import com.galdino.ufood.domain.model.UOrder;
import com.galdino.ufood.domain.model.UOrderStatus;

public abstract class StatusSituation {

    public void confirm(UOrder uorder, UOrderStatus status) {
        throwBE(uorder, status);
    }

    public void deliver(UOrder uorder, UOrderStatus status) {
        throwBE(uorder, status);
    }

    public void cancel(UOrder uorder, UOrderStatus status) {
        throwBE(uorder, status);
    }

    private void throwBE(UOrder uorder, UOrderStatus status) {
        throw new BusinessException(String.format("Status of the uorder with code %s cannot be changed from %s to %s",
                uorder.getCode(), uorder.getStatus().getDescription(), status.getDescription()));
    }

}
