package com.tapestry5book.tlog.core.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Skin {

    @Column(name = "skinName")
    private String name;

    @Column(name = "skinVersion")
    private String version;

    protected Skin(){
        super();
    }

    public Skin(String name, String version) {
        this.name = name;
        this.version = version;
    }


    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skin skin = (Skin) o;

        if (!name.equals(skin.name)) return false;
        if (!version.equals(skin.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }
}
