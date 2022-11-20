package com.core.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

/*
study: jeecgboot导出
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
public class Goods extends Model<Goods> {
    // 对应数据库中的主键（uuid、自增id、雪花算法、redis、zookeeper）
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    @Excel(name = "商品名字", width = 25)
    private String name;
    @Excel(name = "商品图片", width = 25)
    private String img_url;
    /*
        创建时间和更新时间
            方法1  数据库：
                create_time在插入数据中new Date()
                update_time在表中更新勾选上
            方法2  代码 自动填充：
                update_time在表中可以去掉更新勾选了
    */
    @Excel(name = "创建日期", width = 25)
    @TableField(fill = FieldFill.INSERT)
    private Date create_time;
    @Excel(name = "更新时间", width = 25)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date update_time;
    @Version// 乐观锁注解
    private Integer version;
    @Excel(name = "逻辑删除", width = 25)
    @TableLogic// 逻辑删除注解
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
