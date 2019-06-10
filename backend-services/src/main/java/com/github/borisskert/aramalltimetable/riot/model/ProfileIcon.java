package com.github.borisskert.aramalltimetable.riot.model;

public class ProfileIcon {

    private Integer id;
    private byte[] content;
    private String contentType;

    public ProfileIcon(Integer id, byte[] content, String contentType) {
        this.id = id;
        this.content = content;
        this.contentType = contentType;
    }

    public Integer getId() {
        return id;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
