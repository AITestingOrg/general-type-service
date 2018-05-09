package org.aist.aide.generaltypeservice.service.controllers;

import org.aist.aide.generaltypeservice.domain.models.dtos.TypeDto;
import org.aist.aide.generaltypeservice.domain.strategies.TypeResolutionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeResolutionController {
    private TypeResolutionStrategy typeResolutionStrategy;

    public TypeResolutionController(@Autowired TypeResolutionStrategy typeResolutionStrategy) {
        this.typeResolutionStrategy = typeResolutionStrategy;
    }

    @GetMapping("/api/v1/type/{str}")
    public ResponseEntity<TypeDto> resolveType(@RequestParam String str) {
        var typeDto = new TypeDto(typeResolutionStrategy.getType(str));
        return new ResponseEntity<>(typeDto, HttpStatus.OK);
    }
}
