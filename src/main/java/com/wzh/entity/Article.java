package com.wzh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "article",type = "content")
public class Article {
    @Id
    @org.springframework.data.annotation.Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    @Transient
    private String img;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @Field(type = FieldType.Date,analyzer = "ik_max_word")
    private Date createDate;

    @Column(name = "publish_date")
    @JSONField(format = "yyyy-MM-dd")
    @Transient
    private Date publishDate;

    @Transient
    private String status;

    @Transient
    private String guruId;

    private Guru guru;
}
