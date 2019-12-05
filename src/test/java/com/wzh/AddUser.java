package com.wzh;

import com.wzh.dao.UserDao;
import com.wzh.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddUser {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertUserTest() {

        List<User> list = new ArrayList<>();
        int i = 0;
        while (true) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName(getName());
            user.setNickName(getName());
            user.setPassword("123456");
            user.setPhone(getMobile());
            user.setSex(getSex());
            user.setSalt("1");
            user.setStatus("1");
            user.setRigestDate(getTime());
            user.setLastLogin(new Date());
            user.setLocation(getProCity());
            list.add(user);
            i++;
            if(i == 3000) {
                userDao.insertList(list);
                list.clear();
                i=0;
            }
        }
    }


    // 随机生成一个姓名
    public static String getName() {
        String[] firstNameArray = {"李", "王", "张", "刘", "陈", "杨", "赵", "黄", "钟", "周", "吴", "徐", "孙", "胡", "朱", "高",
                "欧阳", "太史", "端木", "上官", "司马"};// 20个姓，其中5个复姓
        String[] lastNameArray = {"伟", "勇", "军", "磊", "涛", "斌", "强", "鹏", "杰", "峰", "超", "波", "辉", "刚", "健", "明", "亮",
                "俊", "飞", "凯", "浩", "华", "平", "鑫", "毅", "林", "洋", "宇", "敏", "宁", "建", "兵", "旭", "雷", "锋", "彬", "龙", "翔",
                "阳", "剑", "静", "敏", "燕", "艳", "丽", "娟", "莉", "芳", "萍", "玲", "娜", "丹", "洁", "红", "颖", "琳", "霞", "婷", "顺",
                "慧", "莹", "晶", "华", "倩", "英", "佳", "梅", "雪", "蕾", "琴", "璐", "伟", "云", "蓉", "青", "薇", "欣", "琼", "宁", "平",
                "媛"};// 80个常用于名字的单字
        Random rd = new Random();
        int firstPos = rd.nextInt(21);
        StringBuilder name = new StringBuilder(firstNameArray[firstPos]);
        int lastLen = rd.nextInt(2) + 1;
        for (int i = 0; i < lastLen; i++) {
            int lastPos = rd.nextInt(81);
            name.append(lastNameArray[lastPos]);
        }
        return name.toString();
    }

    public static String getMobile() {
        StringBuffer m = new StringBuffer();
        Random rd = new Random();
        for (int i = 0; i < 11; i++) {
            m.append(rd.nextInt(10));
        }
        return m.toString();
    }


    public static String getProCity() {

        String[] province = {"河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川",
                "贵州", "云南", "陕西", "甘肃", "青海", "台湾"};
        Random random = new Random();
        int randomProvinceNum = random.nextInt(province.length);
        return province[randomProvinceNum];
    }

    public static String getSex() {

        String[] province = {"男", "女"};
        Random random = new Random();
        int randomProvinceNum = random.nextInt(province.length);
        return province[randomProvinceNum];
    }


    public Date getTime() {
        Date date = randomDate("2018-12-03", "2019-12-03");

        return date;
    }

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }return rtn;
    }

}