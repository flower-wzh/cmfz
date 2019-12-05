package com.wzh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album {
    @Id
    private String id;
    private String title;
    private String cover;
    private Integer score;
    private String author;
    private String broadcast;
    private Integer count;
    private String description;
    private String status;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;

    @Column(name = "publish_date")
    @JSONField(format = "yyyy-MM-dd")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date publishDate;
}
