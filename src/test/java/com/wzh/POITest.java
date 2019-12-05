package com.wzh;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class POITest {

    @Test
    public void test1() {
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("表一");

        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);

        cell.setCellValue("数据");

        try {
            workbook.write(new FileOutputStream(new File("F://tst.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
