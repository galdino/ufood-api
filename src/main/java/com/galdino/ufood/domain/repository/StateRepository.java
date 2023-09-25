package com.galdino.ufood.domain.repository;


import com.galdino.ufood.domain.model.State;

import java.util.List;

public interface StateRepository {
    List<State> list();
    State findById(Long id);
    State add(State state);
    void delete(Long id);
}
