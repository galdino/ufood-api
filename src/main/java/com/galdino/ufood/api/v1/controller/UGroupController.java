package com.galdino.ufood.api.v1.controller;

import com.galdino.ufood.api.v1.assembler.GenericAssembler;
import com.galdino.ufood.api.v1.model.UGroupInput;
import com.galdino.ufood.api.v1.model.UGroupModel;
import com.galdino.ufood.api.v1.openapi.controller.UGroupControllerOpenApi;
import com.galdino.ufood.domain.model.UGroup;
import com.galdino.ufood.domain.repository.UGroupRepository;
import com.galdino.ufood.domain.service.UGroupRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/ugroups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UGroupController implements UGroupControllerOpenApi {

    private UGroupRepository uGroupRepository;
    private UGroupRegisterService uGroupRegisterService;
    private GenericAssembler genericAssembler;

    public UGroupController(UGroupRepository uGroupRepository,
                            UGroupRegisterService uGroupRegisterService,
                            GenericAssembler genericAssembler) {
        this.uGroupRepository = uGroupRepository;
        this.uGroupRegisterService = uGroupRegisterService;
        this.genericAssembler = genericAssembler;
    }

    @GetMapping
    public List<UGroupModel> list() {
        return genericAssembler.toCollection(uGroupRepository.findAll(), UGroupModel.class);
    }

    @GetMapping("/{id}")
    public UGroupModel findById(@PathVariable Long id) {
        UGroup uGroup = uGroupRegisterService.findOrThrow(id);
        return genericAssembler.toClass(uGroup, UGroupModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UGroupModel add(@RequestBody @Valid UGroupInput uGroupInput) {
        UGroup uGroup = genericAssembler.toClass(uGroupInput, UGroup.class);
        uGroup = uGroupRegisterService.add(uGroup);
        return genericAssembler.toClass(uGroup, UGroupModel.class);
    }

    @PutMapping("/{id}")
    public UGroupModel update(@PathVariable Long id, @RequestBody @Valid UGroupInput uGroupInput) {
        UGroup uGroup = uGroupRegisterService.findOrThrow(id);

        genericAssembler.copyToObject(uGroupInput, uGroup);

        uGroup = uGroupRegisterService.add(uGroup);

        return genericAssembler.toClass(uGroup, UGroupModel.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        uGroupRegisterService.remove(id);
    }

}
