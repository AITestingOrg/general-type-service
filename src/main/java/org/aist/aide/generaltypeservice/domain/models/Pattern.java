package org.aist.aide.generaltypeservice.domain.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Pattern {
    @Id
    private UUID id;
    @NotNull
    @NotEmpty
    private String pattern;
    @NotNull
    @NotEmpty
    private Types type;

    public Pattern(UUID id, String pattern, Types type) {
        this.id = id;
        this.pattern = pattern;
        this.type = type;
    }

    public Pattern(String pattern, Types type) {
        this.pattern = pattern;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getPattern() {
        return pattern;
    }

    public Types getType() {
        return type;
    }
}
