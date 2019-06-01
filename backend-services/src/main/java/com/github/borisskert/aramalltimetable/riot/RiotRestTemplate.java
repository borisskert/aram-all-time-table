package com.github.borisskert.aramalltimetable.riot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
        ResponseEntity<T> response = null;
        RequestThrottling throttling = new RequestThrottling();

        do {
            requestThrottling.recordRequestTime();
            throttleRequests();

            try {
                response = restTemplate.getForEntity(url, responseType, uriVariables);
            } catch (HttpServerErrorException e) {
                logger.debug("Server error? wait for {} milliseconds until retry", throttling.getWaitTime());
                throttling.waitUntilRetry();
            } catch (HttpClientErrorException.TooManyRequests e) {
                logger.debug("Too many requests, wait for {} milliseconds until retry", throttling.getWaitTime());
                throttling.waitUntilRetry();
            }
        } while(response == null);

        return response;
    }

    private void throttleRequests() {
        long waitTime = requestThrottling.calculateWaitTime();

        while (waitTime > 0L) {
            waitTime = ((waitTime / 1000) + 1) * 1000;
            waitTime(waitTime);
            waitTime = requestThrottling.calculateWaitTime();
        }
    }

    private void waitTime(long waitTime) {
        try {
            if (logger.isDebugEnabled()) logger.debug("Throttling: have to wait {} milliseconds", waitTime);

            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public class RequestThrottling {
        private Long waitTime = 1000L;

        public Long getWaitTime() {
            return waitTime;
        }

        public void waitUntilRetry() {
            waitTime(waitTime);
            waitTime = (long)(waitTime * 1.1);
        }
    }
}
