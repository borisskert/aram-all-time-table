package com.github.borisskert.aramalltimetable.riot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RiotRestTemplate {

    private static final Logger logger = LoggerFactory.getLogger(RiotRestTemplate.class);

    private final RestTemplate restTemplate;
    private final RiotRequestThrottling requestThrottling;

    @Autowired
    public RiotRestTemplate(RestTemplate restTemplate, RiotRequestThrottling requestThrottling) {
        this.restTemplate = restTemplate;
        this.requestThrottling = requestThrottling;
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        throttleRequests();

        ResponseEntity<T> forEntity;
        try {
            forEntity = restTemplate.getForEntity(url, responseType, uriVariables);
        } catch (HttpServerErrorException.ServiceUnavailable e) {
            recordRequestTime();

            logger.debug("Retry request...");
            return this.getForEntity(url, responseType, uriVariables);
        }

        recordRequestTime();

        return forEntity;
    }

    private void recordRequestTime() {
        requestThrottling.recordRequestTime();
    }

    private void throttleRequests() {
        long waitTime = requestThrottling.calculateWaitTime();

        while (waitTime > 0L) {
            try {
                if (logger.isDebugEnabled()) logger.debug("Throttling: have to wait {} milliseconds", waitTime);

                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            waitTime = requestThrottling.calculateWaitTime();
        }
    }
}
