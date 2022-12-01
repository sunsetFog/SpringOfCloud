package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsAdminRoleRelationMapper;
import com.stars.pojo.shoppingMall.UmsAdminRoleRelation;
import com.stars.pojo.shoppingMall.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "UmsAdminRoleRelationController", description = "用户与角色表")
@RequestMapping("/admin_role")
@CrossOrigin
public class UmsAdminRoleRelationController {
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
    /*
        传参：
            {
                "adminId": 1,
                "roleIds": "2,5"
            }
    */
    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/deleteAdd", method = RequestMethod.GET)
    public ResponseData adminRoleDeleteAdd(@RequestParam("adminId") Long adminId,
                                        @RequestParam("roleIds") List<Long> roleIds) {
        // 删除adminId的数据
        umsAdminRoleRelationMapper.deleteWay(adminId);
        // 批量新增：一个用户多个角色
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsAdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            umsAdminRoleRelationMapper.insertList(list);
        }

        int count = roleIds == null ? 0 : roleIds.size();
        return ResponseDataUtil.countJudge(count);
    }
    @ApiOperation("查找用户的所有角色")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData adminRoleList(@RequestParam("adminId") Long adminId) {
        List<UmsRole> roleList = umsAdminRoleRelationMapper.selectWay(adminId);
        return ResponseDataUtil.buildSuccess(roleList);
    }
}
