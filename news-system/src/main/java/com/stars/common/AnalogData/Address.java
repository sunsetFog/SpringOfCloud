package com.stars.common.AnalogData;

public class Address {
    private Integer id;
    private String desc;

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
