package com.wzh.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @ExcelProperty(value = "ID")
    private String id;
    @ExcelProperty(value = "标题")
    private String title;
    @ExcelProperty(value = "图片"/*,converter = StringImageConverter.class*/)
    private String url;
    @ExcelProperty(value = "链接")
    private String href;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    @ExcelProperty(value = "创建日期")
    private Date createDate;
    @ExcelProperty(value = "描述")
    private String description;
    @ExcelProperty(value = "状态")
    private String status;

}
