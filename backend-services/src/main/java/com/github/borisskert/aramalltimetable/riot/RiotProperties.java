package com.github.borisskert.aramalltimetable.riot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "riot")
@Validated
public class RiotProperties {

    @NotNull
    @Size(min = 1)
    private String apiKey;

    @NotNull
    @Size(min = 1)
    private String baseUrl;

    @Valid
    private List<RequestLimit> requestLimits = new ArrayList<>();

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<RequestLimit> getRequestLimits() {
        return requestLimits;
    }

    public void setRequestLimits(List<RequestLimit> requestLimits) {
        this.requestLimits = requestLimits;
    }

    @Validated
    public static class RequestLimit {

        @NotNull
        @Min(1)
        private Integer requestCount;

        @NotNull
        @Min(1)
        private Integer unitCount;

        @NotNull
        private ChronoUnit unit;

        public Integer getRequestCount() {
            return requestCount;
        }

        public void setRequestCount(Integer requestCount) {
            this.requestCount = requestCount;
        }

        public Integer getUnitCount() {
            return unitCount;
        }

        public void setUnitCount(Integer unitCount) {
            this.unitCount = unitCount;
        }

        public ChronoUnit getUnit() {
            return unit;
        }

        public void setUnit(ChronoUnit unit) {
            this.unit = unit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestLimit that = (RequestLimit) o;
            return requestCount.equals(that.requestCount) &&
                    unitCount.equals(that.unitCount) &&
                    unit == that.unit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(requestCount, unitCount, unit);
        }
    }
}
