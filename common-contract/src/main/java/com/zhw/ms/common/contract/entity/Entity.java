package com.zhw.ms.common.contract.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实例类.
 * Created by hongweizou on 16/8/30.
 */
public class Entity implements Serializable {
    public Long id;
    public Integer version;
    public Date created;
    public Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
