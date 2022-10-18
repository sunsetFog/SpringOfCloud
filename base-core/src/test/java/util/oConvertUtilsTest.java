package util;

import com.base.common.util.oConvertUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class oConvertUtilsTest {
    @Autowired
    private oConvertUtils oConvertUtils;
    @Test
    public void contextLoads() {
        boolean aNull = oConvertUtils.isNotEmpty("null");
        System.out.println("是否空值："+aNull);
    }
}
