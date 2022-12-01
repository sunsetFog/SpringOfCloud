package com.stars.controller.shoppingMall;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import com.stars.mapper.shoppingMall.UmsMenuMapper;
import com.stars.pojo.shoppingMall.UmsMenu;
import com.stars.pojo.shoppingMall.UmsMenuNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RequestMapping("/menu")
@CrossOrigin
public class UmsMenuController {
    @Autowired
    private UmsMenuMapper umsMenuMapper;

    @ApiOperation("根据用户查询用户权限")
    @RequestMapping(value = "/auths", method = RequestMethod.GET)
    public ResponseData menuAuths(@RequestParam String username) {
        Set<String> list01 = umsMenuMapper.queryUserAuths(username);
        return ResponseDataUtil.buildSuccess(list01);
    }

    @ApiOperation("按钮权限列表")
    @RequestMapping(value = "/btnList", method = RequestMethod.GET)
    public ResponseData btnList() {
        List<UmsMenu> umsMenus = umsMenuMapper.btnWay();
        return ResponseDataUtil.buildSuccess(umsMenus);
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeMenu", method = RequestMethod.GET)
    public ResponseData treeMenu(@RequestParam String subject) {
        List<UmsMenu> menuList = umsMenuMapper.selectWay(subject);
        System.out.println("--menuList--"+menuList.size());
        /*
            list.stream.collect(Collectors.toList())去重
            L表示long, 0L就是表示long类型的值是0
            过滤获取到第一级id是0的数据，-1后面匹配不上了
        */
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getMenuParentId().equals(0L))
                .map(menu -> covertMenuNode1(menu, menuList)).collect(Collectors.toList());
        System.out.println("--result--"+result.size());
        return ResponseDataUtil.buildSuccess(result);
    }
    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode1(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getMenuParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode1(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
    //    ------------------------------------------------------------------------------------
    @ApiOperation("树形结构返回所有路由列表")
    @RequestMapping(value = "/treeRouter", method = RequestMethod.GET)
    public ResponseData treeRouter(@RequestParam String subject) {
        List<UmsMenu> menuList = umsMenuMapper.selectWay(subject);
        System.out.println("--menuList--"+menuList.size());
        /*
            list.stream.collect(Collectors.toList())去重
            L表示long, 0L就是表示long类型的值是0
            过滤获取到第一级id是0的数据，-1后面匹配不上了
        */
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getRouterParentId().equals(0L))
                .map(menu -> covertMenuNode2(menu, menuList)).collect(Collectors.toList());
        System.out.println("--result--"+result.size());
        return ResponseDataUtil.buildSuccess(result);
    }
    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode2(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getRouterParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode2(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
    //    ------------------------------------------------------------------------------------
    /*
        分页查询,pageSize传9999查所有
        menuParentId条件筛选
    */
    @ApiOperation("分页查询后台菜单")
    @PostMapping("/routerList")
    public ResponseData menuList(@RequestParam String subject) {
        // SQL查询
        List<UmsMenu> umsMenus = umsMenuMapper.selectWay(subject);
        return ResponseDataUtil.buildSuccess(umsMenus);
    }
    /*
        传参：
            {
                "title": "呃呃呃",
                "menuParentId": 7,
                "name": "嗡嗡嗡",
                "icon": "我11",
                "hidden": 0,
                "sort": 0
            }
    */
    @ApiOperation("添加后台菜单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData menuAdd(@RequestBody UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        // SQL插入
        int count = umsMenuMapper.insertWay(umsMenu);
        return ResponseDataUtil.countJudge(count);
    }

    @ApiOperation("修改后台菜单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData menuUpdate(@RequestBody UmsMenu umsMenu) {
        updateLevel(umsMenu);
        // SQL修改
        int count = umsMenuMapper.updateWay(umsMenu);
        return ResponseDataUtil.countJudge(count);
    }
    /**
     * setMenuLevel算法
     */
    private void updateLevel(UmsMenu umsMenu) {
        System.out.println("--updateLevel--"+umsMenu);
        if(umsMenu.getPerms() != "" || umsMenu.getPerms() != "null") {
            umsMenu.setMenuParentId(umsMenu.getRouterParentId());
            umsMenu.setMenuLevel(-1);
        } else if (umsMenu.getMenuParentId() == 0 || umsMenu.getMenuParentId() == -1) {
            //没有父菜单时为一级菜单
            umsMenu.setMenuLevel(0);
        } else {
            //有父菜单时,根据menuParentId进行SQL查询，得出一条数据，获得level值
            UmsMenu parentMenu1 = umsMenuMapper.rowWay(umsMenu.getMenuParentId());
            umsMenu.setMenuLevel(parentMenu1.getMenuLevel() + 1);
        }
        if (umsMenu.getRouterParentId() == 0 || umsMenu.getRouterParentId() == -1) {
            //没有父菜单时为一级菜单
            umsMenu.setRouterLevel(0);
        } else {
            //有父菜单时,根据routerParentId进行SQL查询，得出一条数据，获得level值
            UmsMenu parentMenu2 = umsMenuMapper.rowWay(umsMenu.getRouterParentId());
            umsMenu.setRouterLevel(parentMenu2.getRouterLevel() + 1);
        }
    }
    @ApiOperation("根据ID批量删除后台菜单")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseData menuDelete(@RequestParam List<Long> ids) {
        int count = umsMenuMapper.deleteWay(ids);
        return ResponseDataUtil.countJudge(count);
    }
}
