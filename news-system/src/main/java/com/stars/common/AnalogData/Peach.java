package com.stars.common.AnalogData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
yaml属性注入：
@ConfigurationProperties注解 批量注入 常用于bean和yaml配置文件的绑定
prefix可以指定配置文件中某一个节点，该节点中的子节点将自动和属性进行绑定
可以松散绑定：
username可以写成
user-name
user_name
userName
USERNAME

生成构造器或get set或toString：鼠标右击---Generate

@PropertySource注解   批量注入  加载指定的properties配置文件
idea防止文件乱码设置：File 》Settings 》Editor 》File Encodings 》Properties Files选UTF-8和打勾
*/

@Component// 注册bean，使扫描识别到
@ConfigurationProperties(prefix = "blossom")
@Validated//约束注解 JSR-303数据校验（百度搜索看常用） 要用javax里的 要和@ConfigurationProperties一起用的
@PropertySource(value = "classpath:cat.properties")
public class Peach {
    /*
        @Value注解要一个个指定，不方便
        $是spel表达式
        松散绑定支持有限
    */
    @Value("${blossom.username}")
    private String username;
    private Integer age;
//    @NotNull(message="是否非空")
    private Date birthday;
    private List<String> hobbies;
    private Map<Integer, String> girlFriend;
    private Address address;

    @Override
    public String toString() {
        return "Peach{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", hobbies=" + hobbies +
                ", girlFriend=" + girlFriend +
                ", address=" + address +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<Integer, String> getGirlFriend() {
        return girlFriend;
    }

    public void setGirlFriend(Map<Integer, String> girlFriend) {
        this.girlFriend = girlFriend;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
