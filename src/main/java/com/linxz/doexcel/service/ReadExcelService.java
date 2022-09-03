package com.linxz.doexcel.service;

import com.alibaba.excel.read.listener.ReadListener;

import java.util.Iterator;
import java.util.List;

public interface ReadExcelService {

    /**
     * 读取出Excel
     * @return
     */
    Iterator getDataByExcel(String pathName, Class head, ReadListener readListener);

}
