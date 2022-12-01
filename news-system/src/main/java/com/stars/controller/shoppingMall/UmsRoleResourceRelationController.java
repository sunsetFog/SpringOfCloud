package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsRoleResourceRelationMapper;
import com.stars.pojo.shoppingMall.UmsResource;
import com.stars.pojo.shoppingMall.UmsRoleResourceRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "UmsRoleResourceRelationController", description = "角色与资源")
@RequestMapping("/role_resource")
@CrossOrigin
public class UmsRoleResourceRelationController {
    @Autowired
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;
    /*
        传参：
            {
                "roleId": 1,
                "resourceIds": "1,2,3,4,5,6,23,24"
            }
    */
    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/deleteAdd", method = RequestMethod.GET)
    public ResponseData roleResourceDeleteAdd(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        // 删除roleId的数据
        umsRoleResourceRelationMapper.deleteWay(roleId);
        // 批量新增：一个角色多个菜单
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation umsRoleResourceRelation = new UmsRoleResourceRelation();
            umsRoleResourceRelation.setRoleId(roleId);
            umsRoleResourceRelation.setResourceId(resourceId);
            umsRoleResourceRelationMapper.insertList(umsRoleResourceRelation);
        }

        int count = resourceIds == null ? 0 : resourceIds.size();
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    public ResponseData listResource(@PathVariable Long roleId) {
        List<UmsResource> resourceListByRoleId = umsRoleResourceRelationMapper.getResourceListByRoleId(roleId);
        return ResponseDataUtil.buildSuccess(resourceListByRoleId);
    }
}
