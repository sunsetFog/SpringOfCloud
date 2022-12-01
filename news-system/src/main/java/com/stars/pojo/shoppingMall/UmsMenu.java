package com.stars.pojo.shoppingMall;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UmsMenu {
    private Long id;

    @ApiModelProperty(value = "菜单父级ID")
    private Long menuParentId;

    @ApiModelProperty(value = "路由父级ID")
    private Long routerParentId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "按钮编码")
    private String perms;

    @ApiModelProperty(value = "菜单级数")
    private Integer menuLevel;

    @ApiModelProperty(value = "路由级数")
    private Integer routerLevel;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @ApiModelProperty(value = "路由名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "按钮禁用")
    private Integer disabled;

    @ApiModelProperty(value = "组件路径")
    private String path;

    @ApiModelProperty(value = "是否缓存")
    private Integer cache;

    @ApiModelProperty(value = "主题项目")
    private String subject;

    private static final long serialVersionUID = 1L;

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
    }

    public Long getRouterParentId() {
        return routerParentId;
    }

    public void setRouterParentId(Long routerParentId) {
        this.routerParentId = routerParentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Integer getRouterLevel() {
        return routerLevel;
    }

    public void setRouterLevel(Integer routerLevel) {
        this.routerLevel = routerLevel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCache() {
        return cache;
    }

    public void setCache(Integer cache) {
        this.cache = cache;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "UmsMenu{" +
                "id=" + id +
                ", menuParentId=" + menuParentId +
                ", routerParentId=" + routerParentId +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", menuLevel=" + menuLevel +
                ", routerLevel=" + routerLevel +
                ", sort=" + sort +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", hidden=" + hidden +
                ", path='" + path + '\'' +
                ", cache=" + cache +
                ", subject='" + subject + '\'' +
                '}';
    }
}
