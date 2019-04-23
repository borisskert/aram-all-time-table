package com.github.borisskert.aramalltimetable.riot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiotRequestThrottling {
    private static final Logger logger = LoggerFactory.getLogger(RiotRequestThrottling.class);

    private final RiotProperties properties;

    private MultiValueMap<RiotProperties.RequestLimit, LocalDateTime> requestTimestamps = new LinkedMultiValueMap<>();

    @Autowired
    public RiotRequestThrottling(RiotProperties properties) {
        this.properties = properties;
    }

    public void recordRequestTime() {
        LocalDateTime now = LocalDateTime.now();

        for (RiotProperties.RequestLimit requestLimit : properties.getRequestLimits()) {
            requestTimestamps.add(requestLimit, now);
        }
    }

    public long calculateWaitTime() {
        LocalDateTime now = LocalDateTime.now();

        List<RiotProperties.RequestLimit> requestLimits = properties.getRequestLimits();

        for (RiotProperties.RequestLimit limit : requestLimits) {
            long waitTime = waitTime(now, limit);

            if (waitTime > 0L) {
                return waitTime;
            }
        }

        return 0L;
    }

    private long waitTime(LocalDateTime now, RiotProperties.RequestLimit limit) {
        Long waitTime;

        LocalDateTime ago = now.minus(limit.getUnitCount(), limit.getUnit());
        List<LocalDateTime> filteredRequests = requestTimestamps.getOrDefault(limit, new ArrayList<>()).stream()
                .filter(timestamp -> timestamp.isAfter(ago))
                .collect(Collectors.toList());

        if (logger.isDebugEnabled())
            logger.debug("Requests between last {} {}(s): {}", limit.getUnitCount(), limit.getUnit(), filteredRequests.size());

        if (filteredRequests.size() > limit.getRequestCount() - 1) {
            waitTime = filteredRequests.stream()
                    .sorted()
                    .findFirst().map(t -> ChronoUnit.MILLIS.between(ago, t))
                    .orElse(0L);

            if (logger.isDebugEnabled()) logger.debug("Calculated wait time: {}", waitTime);
        } else {
            waitTime = 0L;
        }

        requestTimestamps.replace(limit, filteredRequests);

        return waitTime;
    }
}
