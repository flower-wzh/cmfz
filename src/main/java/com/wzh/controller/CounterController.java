package com.wzh.controller;

import com.wzh.entity.Counter;
import com.wzh.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @RequestMapping("/show")
    public Map<String,Object> showCounter(String cId) {
        Map<String,Object> map = new HashMap<>();
        try {
            List<Counter> counterList = counterService.findAll(cId);
            map.put("status",200);
            map.put("counter",counterList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","加载失败！");
        }
        return map;
    }

    @PostMapping("/add")
    public Map<String, Object> add(Counter counter, String uId,String cId) {
        Map<String,Object> map = new HashMap<>();
        try {
            counter.setUserId(uId);
            counter.setCourseId(cId);
            map = counterService.addCounter(counter);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","添加失败！");
        }
        return map;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable("id") String id) {
        Map<String,Object> map = new HashMap<>();
        try {
            counterService.removeCounter(id);
            map.put("id",id);
            map.put("status",200);
            map.put("message","删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","删除失败！");
        }
        return map;
    }


    @PostMapping("/modify")
    public Map<String, Object> add(Counter counter) {
        Map<String,Object> map = new HashMap<>();
        try {
            map = counterService.modifyCounter(counter);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","更新数量失败！");
        }
        return map;
    }
}
