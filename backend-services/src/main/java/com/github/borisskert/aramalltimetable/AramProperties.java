package com.github.borisskert.aramalltimetable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "aram")
@Validated
public class AramProperties {

    @NotNull
    @Min(2)
    private Integer consideredMatches;

    public Integer getConsideredMatches() {
        return consideredMatches;
    }

    public void setConsideredMatches(Integer consideredMatches) {
        this.consideredMatches = consideredMatches;
    }
}
