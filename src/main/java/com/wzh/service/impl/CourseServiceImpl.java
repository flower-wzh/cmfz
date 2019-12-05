package com.wzh.service.impl;

import com.wzh.dao.CourseDao;
import com.wzh.entity.Course;
import com.wzh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;
    @Override
    public Map<String, Object> addCourse(Course course) {
        Map<String,Object> map = new HashMap<>();
        course.setId(UUID.randomUUID().toString().replace("-",""));
        course.setCreateDate(new Date());
        courseDao.insertSelective(course);
        map.put("course",course);
        map.put("status",200);
        return map;
    }

    @Override
    public void removeCourse(String id) {
        courseDao.deleteByPrimaryKey(id);
    }

    @Override
    public void removeCourses(String[] id) {

    }

    @Override
    public Map<String, Object> modifyCourse(Course course) {
        Map<String,Object> map = new HashMap<>();
        courseDao.updateByPrimaryKeySelective(course);
        map.put("status",200);
        map.put("course",course);
        return map;
    }

    @Override
    public Course findOneCourse(String id) {
        return courseDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Course> findAll(String uId) {
        Example example = new Example(Course.class);
        example.createCriteria().andEqualTo("userId",uId);
        return courseDao.selectByExample(example);
    }

    @Override
    public Integer count() {
        return courseDao.selectCount(new Course());
    }
}
