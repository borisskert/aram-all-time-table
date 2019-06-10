package com.github.borisskert.aramalltimetable.profileicon;

import com.github.borisskert.aramalltimetable.riot.DataDragonClient;
import com.github.borisskert.aramalltimetable.riot.model.ProfileIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileIconService {

    private final DataDragonClient dataDragonClient;
    private final ProfileIconStore profileIconStore;

    @Autowired
    public ProfileIconService(DataDragonClient dataDragonClient, ProfileIconStore profileIconStore) {
        this.dataDragonClient = dataDragonClient;
        this.profileIconStore = profileIconStore;
    }

    public ProfileIcon loadProfileIcon(Integer id) {
        Optional<ProfileIcon> maybeProfileIcon = profileIconStore.find(id);

        if(maybeProfileIcon.isPresent()) {
            return maybeProfileIcon.get();
        } else {
            ProfileIcon loadedProfileIcon = dataDragonClient.loadProfileIcon(id);
            profileIconStore.create(loadedProfileIcon);

            return loadedProfileIcon;
        }
    }
}
