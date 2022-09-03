package com.linxz.doexcel.service.Impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.linxz.doexcel.entity.User;
import com.linxz.doexcel.service.ReadExcelService;
import com.linxz.doexcel.util.UserReadListener;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class ReadExcelServiceImpl implements ReadExcelService {

    @Override
    public Iterator getDataByExcel(String pathName, Class head, ReadListener readListener) {

        // 读取文件，读取完之后会自动关闭
        /*
            pathName        文件路径；"d:\\xx.xls"
            head            每行数据对应的实体；Student.class
            readListener    读监听器，每读一样就会调用一次该监听器的invoke方法

            sheet方法参数： 工作表的顺序号（从0开始）或者工作表的名字，不传默认为0
        */
        // 封装工作簿对象
        ExcelReaderBuilder workBook = EasyExcel.read(pathName, head, readListener);

        // 封装工作表
        ExcelReaderSheetBuilder sheet1 = workBook.sheet();
        // 读取
        sheet1.doRead();

        return UserReadListener.userHashSet.iterator();
    }
}
