package com.github.borisskert.aramalltimetable.riot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RiotConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RiotConfiguration.class);

    @Bean
    DataDragonClient dataDragonClient(DataDragonProperties properties) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        String version = properties.getVersion();
        if (DataDragonProperties.LATEST_DATA_DRAGON_VERSION.equals(version)) {
            DataDragonVersionClient versionClient = new DataDragonVersionClient(restTemplate);
            version = versionClient.getLatestVersion();
        }

        logger.info("Using DataDragon API version {}", version);

        return new DataDragonClient(
                properties.getBaseUrl(),
                version,
                restTemplate
        );
    }
}
