package com.galdino.ufood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericInputDisassembler {

    private ModelMapper modelMapper;

    public GenericInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T toDomainObject(Object input, Class<T> type) {
        return modelMapper.map(input, type);
    }
}
