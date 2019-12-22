package com.example.yallp_android.models;

public class Annotation {
    private Creator creator;
    private String created;
    private String bodyValue;
    private String modified;
    private String id;
    private String type;
    private String context;
    private Target target;

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBodyValue() {
        return bodyValue;
    }

    public void setBodyValue(String bodyValue) {
        this.bodyValue = bodyValue;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Annotation(Creator creator, String created, String bodyValue, String modified, String id, String type, String context, Target target) {
        this.creator = creator;
        this.created = created;
        this.bodyValue = bodyValue;
        this.modified = modified;
        this.id = id;
        this.type = type;
        this.context = context;
        this.target = target;
    }
}
