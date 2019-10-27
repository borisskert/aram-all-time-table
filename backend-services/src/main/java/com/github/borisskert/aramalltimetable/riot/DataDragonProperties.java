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

    public static final String LATEST_DATA_DRAGON_VERSION = "latest";

    @NotNull
    @Size(min = 1)
    private String baseUrl;

    private String version = LATEST_DATA_DRAGON_VERSION;


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
