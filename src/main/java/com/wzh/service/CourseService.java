package com.wzh.service;

import com.wzh.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /**
     * 添加课程
     * @param course
     * @return
     */
    Map<String,Object> addCourse(Course course);

    /**
     * 删除课程
     * @param id
     */
    void removeCourse(String id);

    /**
     * 批量删除
     * @param id
     */
    void removeCourses(String[] id);

    /**
     * 更新课程
     * @param course
     */
    Map<String,Object> modifyCourse(Course course);

    /**
     * 根据id查找一个Course详情
     * @param id
     */
    Course findOneCourse(String id);

    /**
     * 查找用户所有课程
     * @return
     */
    List<Course> findAll(String uId);

    /**
     * Course数量
     * @return
     */
    Integer count();


}
