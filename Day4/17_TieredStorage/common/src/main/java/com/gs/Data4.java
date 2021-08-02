package com.gs;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Data4")
public class Data4 implements java.io.Serializable {

    Integer id;
    String info;
    String type;

    public Data4() {
    }

    @Override
    public String toString() {
        return "Data3{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Data4(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    @Id
    @SpaceRouting
    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
