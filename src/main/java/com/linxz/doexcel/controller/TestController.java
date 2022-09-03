package com.linxz.doexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.linxz.doexcel.entity.User;
import com.linxz.doexcel.entity.UserMoney;
import com.linxz.doexcel.service.ReadExcelService;
import com.linxz.doexcel.util.UserMoneyReadListener;
import com.linxz.doexcel.util.UserReadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/test")
public class TestController {

    //日志输出
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ReadExcelService readExcelService;

    @GetMapping("/home")
    public String home() {
        return "excel";
    }

    @GetMapping("/admin")
    public String admin() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        //批量导入列表
        List<User> userList = new ArrayList<User>();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "excel";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get("exceldir/" + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取文件，读取完之后会自动关闭
        /*
            pathName        文件路径；"d:\\模拟在线202003班学员信息.xls"
            head            每行数据对应的实体；Student.class
            readListener    读监听器，每读一样就会调用一次该监听器的invoke方法

            sheet方法参数： 工作表的顺序号（从0开始）或者工作表的名字，不传默认为0
        */
        // 封装工作簿对象
        ExcelReaderBuilder workBook = EasyExcel.read("exceldir/" + file.getOriginalFilename(), User.class, new UserReadListener());

        // 封装工作表
        ExcelReaderSheetBuilder sheet1 = workBook.sheet();
        // 读取
        sheet1.doRead();


        // 封装工作簿对象
        ExcelReaderBuilder workBook2 = EasyExcel.read("exceldir/" + file.getOriginalFilename(), UserMoney.class, new UserMoneyReadListener());

        // 封装工作表
        ExcelReaderSheetBuilder sheet2 = workBook2.sheet();
        // 读取
        sheet2.doRead();

        Iterator it = UserReadListener.userHashSet.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            User user = (User) next;
            user.setUser_money(
                UserMoneyReadListener.userMonies.get(user.getUser_name())
            );
            logger.info("批量录入：{}",next);
            userList.add((User) next);
        }

//        处理掉静态变量记录的list
        UserMoneyReadListener.userMonies = new HashMap<>();
        UserReadListener.userHashSet = new HashSet<User>();


        model.addAttribute("users", userList);


        return "excel";
    }

    //excel批量导入学生的模板下载
    @PostMapping("/download")
    public String  testDownload(HttpServletResponse response , Model model) {
        //待下载文件名
        String fileName = "信息录入.xlsx";

        File excelFile = new File("exceldir/demo/"+fileName);

        //设置格式
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        //加上设置大小下载下来的.xlsx文件打开时才不会报“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
        response.addHeader("Content-Length", String.valueOf(excelFile.length()));


        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(excelFile));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
            model.addAttribute("result","下载失败");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //成功后返回成功信息
        model.addAttribute("result","下载成功");
        return "admin/uploadStatus";
    }

}
