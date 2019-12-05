package com.wzh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    private String id;
    private String phone;
    @JSONField(serialize=false)
    private String password;
    @JSONField(serialize=false)
    private String salt;
    private String status;
    private String photo;
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    private String sex;
    private String sign;
    private String location;
    @Column(name = "rigest_date")
    @JSONField(format = "yyyy-MM-dd")
    private Date rigestDate;
    @Column(name = "last_login")
    @JSONField(format = "yyyy-MM-dd")
    private Date lastLogin;

}
