package com.zuoban.toy.vpstools.supports;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.zuoban.toy.vpstools.entity.File;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class RestResponseTest {

    @Test
    public void success() {
        String s = "a.jpg";
        System.out.println(s.substring(s.lastIndexOf(".")));

    }

    @Test
    public void fail() {
        File file = new File();
        BeanUtil.fillBeanWithMap(null, file,true);
        System.out.println("file = " + file);
        HashMap<String, Object> kvHashMap = MapUtil.newHashMap();
        kvHashMap.put("name", 123);
        File newFile = BeanUtil.fillBeanWithMap(kvHashMap, file, true);
        System.out.println("file = " + file);
        System.out.println("newFile = " + newFile);
        System.out.println(file.equals(newFile ));
    }
}
