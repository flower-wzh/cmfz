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
public class Chapter {
    @Id
    private String id;
    private String title;
    private String url;
    private String size;
    private String time;
    @Column(name = "create_time")
    private Date createDate;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "old_name")
    private String oldName;
    @Column(name = "album_id")
    private String albumId;
}
