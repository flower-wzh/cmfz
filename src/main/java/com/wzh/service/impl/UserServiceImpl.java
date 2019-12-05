package com.wzh.service.impl;

import com.wzh.annotation.Regist;
import com.wzh.annotation.Urband;
import com.wzh.dao.UserDao;
import com.wzh.entity.User;
import com.wzh.service.UserService;
import com.wzh.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllUser(Integer rows, Integer page) {
        Integer offset = (page-1)*rows;
        return userDao.selectByRowBounds(new User(),new RowBounds(offset,rows));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        return userDao.selectCount(new User());
    }

    @Override
    @Urband
    @Regist
    public void changeStatus(User user) {
        if ("0".equals(user.getStatus())) {
            user.setStatus("1");
        } else {
            user.setStatus("0");
        }
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public Map<String, Object> registDistribution() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        //过去1天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 1);
        Date d = c.getTime();
        String day = format.format(d);

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date w = c.getTime();
        String week = format.format(w);

        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);

        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        Map<String,Object> map = new HashMap<>();
        List<Integer> man = new ArrayList<>();
        List<Integer> woman = new ArrayList<>();
        /**
         * 最近一天的男用户
         */
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("sex", "男").andGreaterThanOrEqualTo("rigestDate", day);
        int i = userDao.selectCountByExample(example);
        man.add(i);
        /**
         * 最近一周的男用户
         */
        Example example1 = new Example(User.class);
        example1.createCriteria().andEqualTo("sex", "男").andGreaterThanOrEqualTo("rigestDate", week);
        int i1 = userDao.selectCountByExample(example1);
        man.add(i1);
        /**
         * 最近一年的男用户
         */
        Example example2 = new Example(User.class);
        example2.createCriteria().andEqualTo("sex", "男").andGreaterThanOrEqualTo("rigestDate", mon);
        int i2 = userDao.selectCountByExample(example2);
        man.add(i2);
        /**
         * 最近一年注册的男用户
         */
        Example example3 = new Example(User.class);
        example3.createCriteria().andEqualTo("sex", "男").andLessThanOrEqualTo("rigestDate", new Date()).andGreaterThanOrEqualTo("rigestDate", year);
        int i3 = userDao.selectCountByExample(example3);
        man.add(i3);

        /**
         * 最近一天的女用户
         */
        Example example4 = new Example(User.class);
        example4.createCriteria().andEqualTo("sex", "女").andGreaterThanOrEqualTo("rigestDate", day);
        int i4 = userDao.selectCountByExample(example4);
        woman.add(i4);
        /**
         * 最近一周的女用户
         */
        Example example5 = new Example(User.class);
        example5.createCriteria().andEqualTo("sex", "女").andGreaterThanOrEqualTo("rigestDate", week);
        int i5 = userDao.selectCountByExample(example5);
        woman.add(i5);
        /**
         * 最近一年的女用户
         */
        Example example6 = new Example(User.class);
        example6.createCriteria().andEqualTo("sex", "女").andGreaterThanOrEqualTo("rigestDate", mon);
        int i6 = userDao.selectCountByExample(example6);
        woman.add(i6);
        /**
         * 最近一年注册的女用户
         */
        Example example7 = new Example(User.class);
        example7.createCriteria().andEqualTo("sex", "女").andLessThanOrEqualTo("rigestDate", new Date()).andGreaterThanOrEqualTo("rigestDate", year);
        int i7 = userDao.selectCountByExample(example7);
        woman.add(i7);
        map.put("man",man);
        map.put("woman",woman);
        return map;
    }

    @Override
    public List<String> focusGuru(String uId) {
        Set members = redisTemplate.opsForSet().members(uId);
        List<String> guruList = new ArrayList<>();
        members.forEach(member->{
            guruList.add((String) member);
        });
        return guruList;
    }

    @Override
    public Map<String, Object> login(User user) {
        Map<String ,Object> map = new HashMap<>();
        Example example = new Example(User.class);
        log.info(user.getPhone());
        example.createCriteria().andEqualTo("phone",user.getPhone());
        User one = userDao.selectOneByExample(example);
        if(one == null) {
            map.put("status",-200);
            map.put("message","用户名不存在！");
            return null;
        }
        if(!one.getPassword().equals(MD5Util.getMd5Code(one.getSalt()+user.getPassword()))){
            map.put("status",-200);
            map.put("message","用户名或密码错误！");
            return null;
        }
        map.put("status",200);
        map.put("message","登录成功！");
        map.put("user",one);
        return map;
    }

    @Override
    public User loginByPhone(String phone) {
        Example example = new Example(User.class);
        log.info(phone);
        example.createCriteria().andEqualTo("phone",phone);
        return userDao.selectOneByExample(example);
    }

    @Override
    public Map<String, Object> regist(User user) {
        Map<String,Object> map = new HashMap<>();
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        if(StringUtil.isNotEmpty(user.getPassword())) {
            //生成密码md5
            String salt = MD5Util.getSalt(8);
            String md5Code = MD5Util.getMd5Code(salt + user.getPassword());
            user.setSalt(salt);
            user.setPassword(md5Code);
        }
        userDao.insertSelective(user);
        map.put("status",200);
        map.put("message","注册成功！");
        map.put("user",user);
        return map;
    }

    @Override
    public Map<String, Object> modifyUser(User user) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtil.isNotEmpty(user.getPassword())){
            //生成密码md5
            String salt = MD5Util.getSalt(8);
            String md5Code = MD5Util.getMd5Code(salt + user.getPassword());
            user.setSalt(salt);
            user.setPassword(md5Code);
        }
        userDao.updateByPrimaryKeySelective(user);
        map.put("status",200);
        map.put("message","修改成功！");
        map.put("user",user);
        return map;
    }

    @Override
    public User findOneById(String id) {
        return userDao.selectByPrimaryKey(id);
    }
}
