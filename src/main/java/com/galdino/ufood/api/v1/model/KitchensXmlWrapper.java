package com.galdino.ufood.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.galdino.ufood.domain.model.Kitchen;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@JsonRootName("cuisines")
@Data
public class KitchensXmlWrapper {

    @JsonProperty("cuisine")
    @JacksonXmlElementWrapper(useWrapping = false)
    @NonNull
    private List<Kitchen> kitchens;
}
