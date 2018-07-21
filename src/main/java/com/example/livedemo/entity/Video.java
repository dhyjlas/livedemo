package com.example.livedemo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "video")
public class Video implements Serializable{
    private long id;

    //视频所在文件夹
    private String path;

    //视频文件名
    private String name;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
