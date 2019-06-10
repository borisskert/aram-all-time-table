package com.github.borisskert.aramalltimetable.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Optional;

@Service
public class StreamStore {

    private final Store store;
    private final FileStore fileStore;

    @Autowired
    public StreamStore(Store store, FileStore fileStore) {
        this.store = store;
        this.fileStore = fileStore;
    }

    public void create(final String store, final String id, final StreamDocument document) {
        String filename = store + "/" + id;
        String objectId = fileStore.create(document.getStream(), filename);

        StreamMetadata metadata = new StreamMetadata();

        metadata.id = id;
        metadata.objectId = objectId;
        metadata.contentLength = document.getContentLength();
        metadata.contentType = document.getContentType();

        this.store.create(store, id, metadata);
    }

    public void update(final String store, final String id, final StreamDocument document) {
        String newObjectId = fileStore.update(document.getStream(), id);

        StreamMetadata metadata = this.store.read(store, id, StreamMetadata.class);

        metadata.objectId = newObjectId;
        metadata.contentLength = document.getContentLength();
        metadata.contentType = document.getContentType();

        this.store.update(store, id, metadata);
    }

    public Optional<StreamDocument> find(final String store, final String id) {
        Optional<StreamMetadata> maybeMetadata = this.store.find(store, id, StreamMetadata.class);
        return maybeMetadata.flatMap(this::readProfileIcon);
    }

    private Optional<StreamDocument> readProfileIcon(StreamMetadata metadata) {
        return fileStore.find(metadata.getObjectId())
                .map(stream -> new StreamDocument(
                        stream,
                        metadata.getId(),
                        metadata.getContentType(),
                        metadata.getContentLength()
                ));
    }

    public static class StreamDocument {
        private InputStream stream;
        private String id;
        private String contentType;
        private int contentLength;

        public StreamDocument() {
        }

        public StreamDocument(InputStream stream, String id, String contentType, int contentLength) {
            this.stream = stream;
            this.id = id;
            this.contentType = contentType;
            this.contentLength = contentLength;
        }

        public InputStream getStream() {
            return stream;
        }

        public String getId() {
            return id;
        }

        public String getContentType() {
            return contentType;
        }

        public int getContentLength() {
            return contentLength;
        }
    }

    private static class StreamMetadata {
        private String id;
        private String objectId;
        private String contentType;
        private int contentLength;

        public String getId() {
            return id;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getContentType() {
            return contentType;
        }

        public int getContentLength() {
            return contentLength;
        }
    }
}
