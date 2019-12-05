package com.wzh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "loginfo")
public class LogInfo {
    @Id
    private String id;
    private String admin;
    private String action;
    private String status;
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
}
