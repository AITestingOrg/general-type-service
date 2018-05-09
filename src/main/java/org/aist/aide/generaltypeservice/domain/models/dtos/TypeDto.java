package org.aist.aide.generaltypeservice.domain.models.dtos;

import org.aist.aide.generaltypeservice.domain.models.Types;

public class TypeDto {
    private Types type;
    public TypeDto(Types type) {
        this.type = type;
    }

    public TypeDto() {

    }

    public Types getType() {
        return type;
    }
}
