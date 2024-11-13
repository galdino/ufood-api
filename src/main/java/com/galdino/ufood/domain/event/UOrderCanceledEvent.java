package com.galdino.ufood.domain.event;

import com.galdino.ufood.domain.model.UOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UOrderCanceledEvent {

    private UOrder uOrder;

}
