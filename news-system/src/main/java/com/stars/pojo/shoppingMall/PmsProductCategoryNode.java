package com.stars.pojo.shoppingMall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsProductCategoryNode extends PmsProductCategory {
    @ApiModelProperty(value = "子级")
    private List<PmsProductCategoryNode> children;

    public List<PmsProductCategoryNode> getChildren() {
        return children;
    }

    public void setChildren(List<PmsProductCategoryNode> children) {
        this.children = children;
    }
}
