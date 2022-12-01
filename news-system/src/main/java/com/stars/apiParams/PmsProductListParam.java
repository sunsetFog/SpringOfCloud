package com.stars.apiParams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 商品查询参数
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductListParam {
    @ApiModelProperty("上架状态")
    @NotEmpty
    private Integer publishStatus;
    @ApiModelProperty("审核状态")
    @NotEmpty
    private Integer verifyStatus;
    @ApiModelProperty("商品名称模糊关键字")
    @NotEmpty
    private String keyword;// name
    @ApiModelProperty("商品货号")
    @NotEmpty
    private String productSn;
    @ApiModelProperty("商品分类编号")
    @NotEmpty
    private Long productCategoryId;
    @ApiModelProperty("商品品牌编号")
    @NotEmpty
    private Long brandId;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
