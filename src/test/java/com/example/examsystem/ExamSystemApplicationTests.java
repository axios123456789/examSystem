package com.example.examsystem;

import io.lettuce.core.output.SocketAddressOutput;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class ExamSystemApplicationTests {

    @Test
    void contextLoads() {
        /*Map<String,String> map = new HashMap<>();
        map.put("1","ni");
        map.put("2","wwd");
        System.out.println(map);
        Map<String,String> map1 = new HashMap<>();
        map1.put("1","ni");
        map1.put("4","weq");

        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map1);
        list.stream().distinct();
        System.out.println(list);*/
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List<Map> maps = list.stream().map(id->{
            Map<String, String> map = new HashMap<>();
            map.put("id",id);
            map.put("pid","sq"+id);
            return map;
        }).collect(Collectors.toList());
        System.out.println(maps);
    }

}
