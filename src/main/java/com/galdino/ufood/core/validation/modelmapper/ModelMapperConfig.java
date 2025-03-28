package com.galdino.ufood.core.validation.modelmapper;

import com.galdino.ufood.api.v1.model.AddressModel;
import com.galdino.ufood.api.v2.model.CityInputV2;
import com.galdino.ufood.domain.model.Address;
import com.galdino.ufood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CityInputV2.class, City.class)
                   .addMappings(mapper -> mapper.skip(City::setId));

        var addressToAddressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);

        addressToAddressModelTypeMap.addMapping(
                addressSrc -> addressSrc.getCity().getState().getName(),
                (addressModelDest, value) -> addressModelDest.getCity().setState((String) value));

        return modelMapper;
    }

}
