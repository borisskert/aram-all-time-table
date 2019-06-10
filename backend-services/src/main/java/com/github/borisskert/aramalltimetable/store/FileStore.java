package com.github.borisskert.aramalltimetable.store;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FileStore {
    private final GridFsTemplate gridFsTemplate;

    public FileStore(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public String create(InputStream inputStream, String filename) {
        ObjectId id = gridFsTemplate.store(inputStream, filename);
        return id.toHexString();
    }

    public String create(InputStream inputStream, Document metadata) {
        ObjectId id = gridFsTemplate.store(inputStream, metadata);
        return id.toHexString();
    }

    public String update(InputStream inputStream, String id) {
        GridFSFile gridFsFile = gridFsTemplate.findOne(idQuery(id));

        if(gridFsFile != null) {
            String filename = gridFsFile.getFilename();
            gridFsTemplate.delete(idQuery(id));
            return create(inputStream, filename);
        }

        throw new RuntimeException("Cant update non-existing file");
    }

    public Optional<InputStream> find(String id) {
        Optional<InputStream> maybeStream = Optional.empty();

        GridFSFile gridFsFile = gridFsTemplate.findOne(idQuery(id));
        if(gridFsFile != null) {
            maybeStream = findInputStream(gridFsFile);
        }

        return maybeStream;
    }

    private Optional<InputStream> findInputStream(GridFSFile gridFsFile) {
        Optional<InputStream> maybeStream = Optional.empty();
        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);

        if(resource.exists()) {
            try {
                maybeStream = Optional.of(resource.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return maybeStream;
    }

    private Query idQuery(String id) {
        return new Query(Criteria.where("_id").is(id));
    }
}
