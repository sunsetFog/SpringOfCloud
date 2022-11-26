package com.core.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.common.api.CommonAPI;
import com.core.mapper.GoodsMapper;
import com.core.pojo.Goods;
import com.core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.security.Permission;
import java.util.*;
import java.util.stream.Collectors;

//@Controller
@RestController
public class GoodsController {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CommonAPI commonAPI;
    // 使用xml来查询单条
    @RequestMapping(value = "/select/row", method = RequestMethod.GET)
    public Goods rowWay() {
        Goods goods = this.goodsService.fishWay(2);
        Goods goods1 = commonAPI.goodsList(19);
        return goods1;
    }
    // 查询所有
    @GetMapping("/shop/queryAll")
    public List<Goods> shopQueryAll() {
        // 查询所有
        List<Goods> goodsList = goodsMapper.selectList(null);
        /*
            stream只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器那样，想要再次遍历必须重新生成
            map():用于映射每个元素到对应的结果。
            filter():filter 方法用于通过设置的条件过滤出元素。
            Collectors.toList() 用来结束Stream流
        */
//        List<Long> ids= goodsList.stream().map(Goods::getId).collect(Collectors.toList());
        List<Long> ids= goodsList.stream().map(item->item.getId()).collect(Collectors.toList());
        List<Goods> permissions = goodsList.stream().filter(item -> item.getId().equals(1L)).collect(Collectors.toList());
        System.out.println("map匹配id数组："+ids);// [1, 2, 3, 4]
        System.out.println("filter匹配："+permissions);

        String shipIds = "1,3,5,6";
        List<Long> shipIdList = Arrays.stream(shipIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
        System.out.println("方式1-字符串转list: "+shipIdList);// [1, 3, 5, 6]
        String join = CollectionUtil.join(shipIdList, ",");
        System.out.println("list转逗号分隔的字符串: "+join);// 1,3,5,6
        List<String> strings = Arrays.asList(join.split(","));
        System.out.println("方式2-字符串转list: "+strings);


        // 根据name去重
        List<Goods> unique01 = goodsList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Goods::getName))), ArrayList::new)
        );
        // 根据name,sex两个属性去重
        List<Goods> unique02 = goodsList.stream().collect(
                Collectors. collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getName()))), ArrayList::new)
        );
        System.out.println("list根据某个对象属性去重: "+unique02);

        goodsList.forEach(System.out::println);
        return goodsList;
    }
    // 添加
    @GetMapping("/shop/add")
    public Integer shopAdd() {
        // id自动回填
        Goods goods = new Goods();
        goods.setName("儿哈");
        goods.setCreateTime(new Date());
        int insert = goodsMapper.insert(goods);
        return insert;
    }
    // 修改
    @GetMapping("/shop/update")
    public Integer shopUpdate() {
        Goods goods = new Goods();
        goods.setId(136L);
        goods.setName("2jjj");
        int count = goodsMapper.updateById(goods);
        return count;
    }
    // 测试乐观锁失败！多线程下
    @GetMapping("/shop/yeguan")
    public void testLocker2() {
        // 线程1
        Goods goods01 = goodsMapper.selectById(137);
        goods01.setName("九九九");
        // 模拟另一个线程执行插队操作
        Goods goods02 = goodsMapper.selectById(137);
        goods02.setName("999");
        int count02 = goodsMapper.updateById(goods02);

        int count01 = goodsMapper.updateById(goods01);// 结果999  没有覆盖  如果没有乐观锁就会覆盖插队线程的值
    }
    // 批量查询
    @GetMapping("/shop/piliang")
    public void piliang() {
        // goodsMapper.selectById(8)   根据id查询一条
        List<Integer> integers = Arrays.asList(1, 2, 4);
        List<Goods> goods = goodsMapper.selectBatchIds(integers);
    }
    // 按条件查询  使用Map操作，不合适
    @GetMapping("/shop/tiaojian")
    public void tiaojian() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "999");
        List<Goods> goods = goodsMapper.selectByMap(map);
    }
    // 按条件查询  使用条件构造器
    @GetMapping("/shop/wrapper")
    public void wrapper01() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();

        // 查询name不为空，id大于等于22
//        wrapper.isNotNull("name").ge("id", 22);
//        List<Goods> goods = goodsMapper.selectList(wrapper);// 列表用selectList、selectMaps都行

        // 查询名字等于**的
//        wrapper.eq("name", "荔枝");
//        goodsMapper.selectOne(wrapper);// 一条用selectOne

        // 查询id 1~3区间有多少条？
//        wrapper.between("id", 1, 5);
//        goodsMapper.selectCount(wrapper);

        // 模糊查询  左 %***   右 ***%  左右 %***%
//        wrapper.notLike("name", "芒").likeRight("img_url", "/img/");
//        goodsMapper.selectMaps(wrapper);

        // 子查询
//        wrapper.inSql("id", "select id from goods where id < 3");
//        goodsMapper.selectObjs(wrapper);

        // id排序    降序 升序换个方法就行
        wrapper.orderByDesc("id");
        goodsMapper.selectList(wrapper);
    }
    // 分页查询
    @GetMapping("/shop/page")
    public void shopPage() {
        Page<Goods> page = new Page<>(1, 5);// 参数1：当前页   参数2：一页多少条
        IPage<Goods> goodsIPage = goodsMapper.selectPage(page, null);
        System.out.println("总页数"+goodsIPage.getTotal());
    }
    /*
        物理删除：数据库中直接移除
        逻辑删除：数据库中没有被移除   delete = 0 变 delete = 1    防止数据的丢失，类似回收站
            配置好后，还是用一样的方法删除，比如deleteById   实体类没用@TableLogic注解，就是物理删除
            查询就有 WHERE deleted=0
    */
    // 批量删除
    @GetMapping("/shop/deleteBatch")
    public void shopDeleteBatch() {
        // int count = goodsMapper.deleteById(138); 根据id删除一条
        List<Integer> integers = Arrays.asList(137, 138);
        int count = goodsMapper.deleteBatchIds(integers);
    }
    // 按条件删除
    @GetMapping("/shop/deleteCondition")
    public void deleteCondition() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "2jjj");
        int count = goodsMapper.deleteByMap(map);
    }




}
