package com.galdino.ufood.api.v2.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.KitchensXmlWrapper;
import com.galdino.ufood.api.v2.model.KitchenInputV2;
import com.galdino.ufood.api.v2.model.KitchenModelV2;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.service.KitchenRegisterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/kitchens")
public class KitchenControllerV2 {

    private KitchenRepository kitchenRepository;
    private KitchenRegisterService kitchenRegisterService;
    private GenericAssembler genericAssembler;

    public KitchenControllerV2(KitchenRepository kitchenRepository, KitchenRegisterService kitchenRegisterService,
                               GenericAssembler genericAssembler) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenRegisterService = kitchenRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public Page<KitchenModelV2> list(@PageableDefault(size = 5) Pageable pageable) {
        List<KitchenModelV2> kitchenModelList = genericAssembler.toCollection(kitchenRepository.findAll(pageable).getContent(), KitchenModelV2.class);

        return new PageImpl<>(kitchenModelList, pageable, kitchenModelList.size());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml() {
        return new KitchensXmlWrapper(kitchenRepository.findAll());
    }

//    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}")
    public KitchenModelV2 findById(@PathVariable Long id) {
        Kitchen kitchen = kitchenRegisterService.findOrThrow(id);
        return genericAssembler.toClass(kitchen, KitchenModelV2.class);
    }


    @PreAuthorize("hasAuthority('EDIT_KITCHEN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModelV2 add(@RequestBody @Valid KitchenInputV2 kitchenInputV2) {
        Kitchen kitchen = genericAssembler.toClass(kitchenInputV2, Kitchen.class);
        kitchen = kitchenRegisterService.add(kitchen);
        return genericAssembler.toClass(kitchen, KitchenModelV2.class);
    }

    @PreAuthorize("hasAuthority('EDIT_KITCHEN')")
    @PutMapping("/{id}")
    public KitchenModelV2 update(@PathVariable Long id, @RequestBody @Valid KitchenInputV2 kitchenInputV2) {
        Kitchen kitchenAux = kitchenRegisterService.findOrThrow(id);

        //BeanUtils.copyProperties(kitchen, kitchenAux, "id");

        genericAssembler.copyToObject(kitchenInputV2, kitchenAux);

        kitchenAux = kitchenRegisterService.add(kitchenAux);

        return genericAssembler.toClass(kitchenAux, KitchenModelV2.class);
    }

    @PreAuthorize("hasAuthority('EDIT_KITCHEN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        kitchenRegisterService.remove(id);
    }
}
