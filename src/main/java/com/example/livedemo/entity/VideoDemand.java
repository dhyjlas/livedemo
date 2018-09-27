package com.example.livedemo.entity;

import javax.persistence.*;

/**
 * 点播视频
 */
@Entity
@Table(name = "video_demand")
public class VideoDemand {
    //ID
    private long id;

    //视频名称
    private String name;

    //视频地址
    private String path;

    //视频截图地址
    private String image_path;

    private String type;

    private String viewpoint;

    private String context;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewpoint() {
        return viewpoint;
    }

    public void setViewpoint(String viewpoint) {
        this.viewpoint = viewpoint;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
