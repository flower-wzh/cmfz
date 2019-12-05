package com.wzh.controller;

import com.wzh.entity.Course;
import com.wzh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/show/{id}")
    public Map<String, Object> show(@PathVariable("id")String id) {
        Map<String,Object> map = new HashMap<>();
        try {
            List<Course> all = courseService.findAll(id);
            map.put("status",200);
            map.put("courses",all);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","加载失败！");
        }
        return map;
    }

    @PostMapping("/add")
    public Map<String, Object> add(Course course,String uId) {
        Map<String,Object> map = new HashMap<>();
        try {
            course.setUserId(uId);
            map = courseService.addCourse(course);
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
            courseService.removeCourse(id);
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

}
