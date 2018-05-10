package org.aist.aide.generaltypeservice.domain.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Pattern {
    @Id
    private UUID id;
    @NotNull
    @NotEmpty
    private String pattern;
    @NotNull
    @NotEmpty
    private String type;

    public Pattern(UUID id, String pattern, String type) {
        this.id = id;
        this.pattern = pattern;
        this.type = type;
    }

    public Pattern(String pattern, String type) {
        this.pattern = pattern;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getPattern() {
        return pattern;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
