package com.github.borisskert.aramalltimetable.riot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@ConfigurationProperties(prefix = "data-dragon")
@Validated
public class DataDragonProperties {

    @NotNull
    @Size(min = 1)
    private String baseUrl;


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
