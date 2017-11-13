package cn.blmdz.test;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.blmdz.common.util.JsonMapper;

public class Ptest {

    public static void main(String[] args) {
        String json = "[{\"OPEN_FORM_FIELD_BIRTHDAY_WITH_YEAR\":\"2017-09-13\"},{\"OPEN_FORM_FIELD_EMAIL\":\"1370200137@qq.com\"},{\"OPEN_FORM_FIELD_GENDER\":\"男\"},{\"OPEN_FORM_FIELD_MOBILE\":\"18224524752\"},{\"OPEN_FORM_FIELD_NAME\":\"我们\"}]";
        List<Map<String, String>> list = JsonMapper.nonDefaultMapper().fromJson(json, JsonMapper.nonDefaultMapper().createCollectionType(List.class, Map.class));
        Map<String, String> maps = Maps.newConcurrentMap();
        for (Map<String, String> map : list) {
            maps.putAll(map);
        }
        System.out.println(maps.toString());
        
        System.out.println(String.format("%06d", (int)(Math.random() * 1000000)));
    }
}
