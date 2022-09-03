package com.linxz.doexcel.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.linxz.doexcel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * @auther ChillyLin
 * @date 2020/10/2
 */
public class UserReadListener extends AnalysisEventListener<User> {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //确保不重复添加
    public static HashSet<User> userHashSet = new HashSet<User>();

    // 每读一样，会调用该invoke方法一次
    @Override
    public void invoke(User data, AnalysisContext context) {
        if (data.getUser_name() == null){
            logger.info("this is null,pass");
        }else {
            if (userHashSet.add(data)){
                logger.info("User data = " + data);
            };
        }

    }

    // 全部读完之后，会调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // TODO......
    }
}