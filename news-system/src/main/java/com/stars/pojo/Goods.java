package com.stars.pojo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/*
lombok之实体类：
@Data注解
自动给对象提供get, set, ToString, hashCode, equals等方法

原理：
java源文件---java语法树---根据lombok注解修改语法树---class字节码文件

只生成get, set, ToString
@Getter
@Setter
@ToString
*/
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor// 无参构造
@AllArgsConstructor// 全参构造
@Accessors(chain = true)// 开启链式调用 就可以写法user.setName(77).setAge(18)
@ApiModel("商品实体类")
public class Goods implements Serializable {
    private int id;
    private String name;
    private String img_url;
    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
