package com.stars.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 状态约束校验器,即是true或false的约束
 * 调用@FlagInterface(value = {"0","1"},message = "状态只能为0或1")
 * Created by macro on 2018/4/26.
 */
public class FlagValidator implements ConstraintValidator<FlagInterface,Integer> {
    private String[] values;
    @Override
    public void initialize(FlagInterface flagInterface) {
        System.out.println("状态约束校验器-1-"+flagInterface);
        // 设置values数组 {"0","1"}
        this.values = flagInterface.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("状态约束校验器-2-"+value);
        boolean isValid = false;
        if(value==null){
            //当状态为空时使用默认值
            return true;
        }
        /*
            value是传参值
            values是设置数据
            两者作比较，是否包含
        */
        for(int i=0;i<values.length;i++){
            if(values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
