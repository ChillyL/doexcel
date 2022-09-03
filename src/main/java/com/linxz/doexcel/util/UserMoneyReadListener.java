package com.linxz.doexcel.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.linxz.doexcel.entity.User;
import com.linxz.doexcel.entity.UserMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @auther ChillyLin
 * @date 2022/7/28
 */
public class UserMoneyReadListener extends AnalysisEventListener<UserMoney> {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //确保不重复添加
    public static HashMap<String, Long> userMonies = new HashMap<>();

    // 每读一样，会调用该invoke方法一次
    @Override
    public void invoke(UserMoney data, AnalysisContext context) {
        logger.info("userMonies data = " + data);
        if(userMonies.containsKey(data.getUser_name())){
            userMonies.put(data.getUser_name(), userMonies.get(data.getUser_name()) + data.getUser_money());
            logger.info("userMonies 累计预收款： " + userMonies.get(data.getUser_name()));
        }else {
            userMonies.put(data.getUser_name(), data.getUser_money());
            logger.info("userMonies 初始预收款： " + userMonies.get(data.getUser_name()));
        }

    }

    // 全部读完之后，会调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // TODO......
    }
}