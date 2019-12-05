package com.wzh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Guru {

    @Id
    private String id;
    private String name;
    private String photo;
    private String status;
    @Column(name = "nick_name")
    private String nickName;
}
