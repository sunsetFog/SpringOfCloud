package com.core.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

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
//    @TableId(type = IdType.ID_WORKER)
    private Long id;
    @Excel(name = "商品名字", width = 25)
    private String name;
    @Excel(name = "商品图片", width = 25)
    private String imgUrl;
    /*
        创建时间和更新时间
            方法1  数据库：
                create_time在插入数据中new Date()
                update_time在表中更新勾选上
            方法2  代码 自动填充：
                update_time在表中可以去掉更新勾选了
    */
    @Excel(name = "创建日期", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Excel(name = "更新时间", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Version// 乐观锁注解
    private Integer version;
//    @Excel(name = "逻辑删除", width = 25)
    @TableLogic// study: 逻辑删除注解
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
