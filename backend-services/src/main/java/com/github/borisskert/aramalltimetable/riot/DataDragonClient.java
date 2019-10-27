package com.github.borisskert.aramalltimetable.riot;

import com.github.borisskert.aramalltimetable.ForbiddenException;
import com.github.borisskert.aramalltimetable.riot.model.ProfileIcon;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class DataDragonClient {
    private final String baseUrl;
    private final String version;
    private final RestTemplate restTemplate;

    public DataDragonClient(String baseUrl, String version, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.version = version;
        this.restTemplate = restTemplate;
    }

    public ProfileIcon loadProfileIcon(Integer id) throws ForbiddenException {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("profileIconId", id.toString());

        ResponseEntity<byte[]> response;
        try {
            response = restTemplate.getForEntity(
                    baseUrl + "/" + version + "/img/profileicon/{profileIconId}.png",
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
