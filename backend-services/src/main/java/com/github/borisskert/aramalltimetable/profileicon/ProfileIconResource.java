package com.github.borisskert.aramalltimetable.profileicon;

import com.github.borisskert.aramalltimetable.riot.model.ProfileIcon;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/lol")
public class ProfileIconResource {

    private final ProfileIconService profileIconService;

    @Autowired
    public ProfileIconResource(ProfileIconService profileIconService) {
        this.profileIconService = profileIconService;
    }

    @GetMapping(value = "/profileIcon/{id}")
    public ResponseEntity<byte[]> getProfileIcon(@PathVariable Integer id) {
        ProfileIcon profileIcon = profileIconService.loadProfileIcon(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType(profileIcon.getContentType()));
        responseHeaders.setContentLength(profileIcon.getContent().length);

        return new ResponseEntity<>(profileIcon.getContent(), responseHeaders, HttpStatus.OK);
    }
}
