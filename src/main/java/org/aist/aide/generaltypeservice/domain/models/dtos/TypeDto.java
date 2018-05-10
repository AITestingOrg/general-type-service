package org.aist.aide.generaltypeservice.domain.models.dtos;


public class TypeDto {
    private String type;
    public TypeDto(String type) {
        this.type = type;
    }

    public TypeDto() {

    }

    public String getType() {
        return type;
    }
}
