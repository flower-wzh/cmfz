package com.wzh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Course {

    @Id
    private String id;
    private String title;
    @Column(name = "user_id")
    private String userId;
    private String type;
    @Column(name = "create_date")
    private Date createDate;
}
