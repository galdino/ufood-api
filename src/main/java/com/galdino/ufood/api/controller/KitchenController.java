package com.galdino.ufood.api.controller;

import com.galdino.ufood.api.assembler.GenericAssembler;
import com.galdino.ufood.api.model.KitchenInput;
import com.galdino.ufood.api.model.KitchenModel;
import com.galdino.ufood.api.model.KitchensXmlWrapper;
import com.galdino.ufood.api.openapi.controller.KitchenControllerOpenApi;
import com.galdino.ufood.domain.model.Kitchen;
import com.galdino.ufood.domain.repository.KitchenRepository;
import com.galdino.ufood.domain.service.KitchenRegisterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/kitchens")
public class KitchenController implements KitchenControllerOpenApi {

    private KitchenRepository kitchenRepository;
    private KitchenRegisterService kitchenRegisterService;
    private GenericAssembler genericAssembler;

    public KitchenController(KitchenRepository kitchenRepository, KitchenRegisterService kitchenRegisterService,
                             GenericAssembler genericAssembler) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenRegisterService = kitchenRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public Page<KitchenModel> list(@PageableDefault(size = 5) Pageable pageable) {
        List<KitchenModel> kitchenModelList = genericAssembler.toCollection(kitchenRepository.findAll(pageable).getContent(), KitchenModel.class);

        return new PageImpl<>(kitchenModelList, pageable, kitchenModelList.size());
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml() {
        return new KitchensXmlWrapper(kitchenRepository.findAll());
    }

//    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public KitchenModel findById(@PathVariable Long id) {
        Kitchen kitchen = kitchenRegisterService.findOrThrow(id);
        return genericAssembler.toClass(kitchen, KitchenModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel add(@RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchen = genericAssembler.toClass(kitchenInput, Kitchen.class);
        kitchen = kitchenRegisterService.add(kitchen);
        return genericAssembler.toClass(kitchen, KitchenModel.class);
    }

    @PutMapping("/{id}")
    public KitchenModel update(@PathVariable Long id, @RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchenAux = kitchenRegisterService.findOrThrow(id);

        //BeanUtils.copyProperties(kitchen, kitchenAux, "id");

        genericAssembler.copyToObject(kitchenInput, kitchenAux);

        kitchenAux = kitchenRegisterService.add(kitchenAux);

        return genericAssembler.toClass(kitchenAux, KitchenModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        kitchenRegisterService.remove(id);
    }
}
