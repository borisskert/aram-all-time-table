package com.github.borisskert.aramalltimetable.riot;

import com.github.borisskert.aramalltimetable.ForbiddenException;
import com.github.borisskert.aramalltimetable.riot.model.ProfileIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class DataDragonClient {
    private final DataDragonProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public DataDragonClient(DataDragonProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    public ProfileIcon loadProfileIcon(Integer id) throws ForbiddenException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("profileIconId", id.toString());

        ResponseEntity<byte[]> response;
        try {
            response = restTemplate.getForEntity(
                    properties.getBaseUrl() + "/img/profileicon/{profileIconId}.png",
                    byte[].class,
                    uriVariables
            );
        } catch(HttpClientErrorException.Forbidden exception) {
            throw new ForbiddenException("Loading profile icon '" + id + "' is forbidden");
        }

        byte[] content = response.getBody();

        HttpHeaders responseHeaders = response.getHeaders();
        MediaType contentType = responseHeaders.getContentType();

        return new ProfileIcon(id, content, contentType.toString());
    }
}
