package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsRoleMenuRelationMapper;
import com.stars.pojo.shoppingMall.UmsMenu;
import com.stars.pojo.shoppingMall.UmsRoleMenuRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "UmsRoleMenuRelationController", description = "角色与菜单")
@RequestMapping("/role_menu")
@CrossOrigin
public class UmsRoleMenuRelationController {
    @Autowired
    private UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;

    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    public ResponseData listMenu(@PathVariable Long roleId) {
        List<UmsMenu> umsMenus = umsRoleMenuRelationMapper.selectWay(roleId);
        return ResponseDataUtil.buildSuccess(umsMenus);
    }
    /*
        传参：
            {
                "roleId": 1,
                "menuIds": "2,5"
            }
    */
    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/deleteAdd", method = RequestMethod.GET)
    public ResponseData roleMenuDeleteAdd(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        // 删除roleId的数据
        umsRoleMenuRelationMapper.deleteWay(roleId);
        // 批量新增：一个角色多个菜单
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            umsRoleMenuRelationMapper.insertList(relation);
        }

        int count = menuIds == null ? 0 : menuIds.size();
        return ResponseDataUtil.countJudge(count);
    }
}
