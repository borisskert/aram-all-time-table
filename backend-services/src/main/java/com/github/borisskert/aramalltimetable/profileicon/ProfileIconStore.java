package com.github.borisskert.aramalltimetable.profileicon;

import com.github.borisskert.aramalltimetable.riot.model.ProfileIcon;
import com.github.borisskert.aramalltimetable.store.StreamStore;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProfileIconStore {
    private static final String PROFILE_ICON_STORE_NAME = "profileIcon";
    private final StreamStore streamStore;

    @Autowired
    public ProfileIconStore(StreamStore streamStore) {
        this.streamStore = streamStore;
    }

    public Optional<ProfileIcon> find(final Integer id) {
        Optional<StreamStore.StreamDocument> maybeDocument = streamStore.find(PROFILE_ICON_STORE_NAME, id.toString());
        return maybeDocument.map(this::readToProfileIcon);
    }

    public void create(ProfileIcon profileIcon) {
        StreamStore.StreamDocument document = new StreamStore.StreamDocument(
                new ByteArrayInputStream(profileIcon.getContent()),
                profileIcon.getId().toString(),
                profileIcon.getContentType(),
                profileIcon.getContent().length
        );

        streamStore.create(PROFILE_ICON_STORE_NAME, profileIcon.getId().toString(), document);
    }

    public void update(ProfileIcon profileIcon) {
        StreamStore.StreamDocument document = new StreamStore.StreamDocument(
                new ByteArrayInputStream(profileIcon.getContent()),
                profileIcon.getId().toString(),
                profileIcon.getContentType(),
                profileIcon.getContent().length
        );

        streamStore.update(PROFILE_ICON_STORE_NAME, profileIcon.getId().toString(), document);
    }

    private ProfileIcon readToProfileIcon(StreamStore.StreamDocument streamDocument) {
        try {
            return new ProfileIcon(
                    Integer.parseInt(streamDocument.getId()),
                    IOUtils.readFully(streamDocument.getStream(), streamDocument.getContentLength()),
                    streamDocument.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
