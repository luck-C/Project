package com.luck.controller;

import com.luck.entity.User;
import com.luck.entity.UserFiles;
import com.luck.service.UserFilesService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    private UserFilesService userFilesService;
    /**
     * 返回用户的所有文件列表---JSON格式
     * 用于实时更新下载次数
     * */
    @GetMapping("findAllJSON")
    @ResponseBody
    public List<UserFiles> findAllJSON(){
        Session session1 = SecurityUtils.getSubject().getSession();
        User user = (User) session1.getAttribute("user");
        List<UserFiles> files = userFilesService.findAllByUserId(user.getId());
        return files;
    }

    /**
     * 删除文件信息
     * */
    @GetMapping("delete")
    public String delete(String id) throws FileNotFoundException {
        //根据id查询文件信息
        UserFiles file = userFilesService.finById(id);
        //删除文件
        //路径拼接
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + file.getPath();
        File exitFile = new File(realPath,file.getNewFileName());
        if(exitFile.exists()) exitFile.delete(); //立刻删除
        //删除数据库中的记录
        userFilesService.delete(id);
        return "redirect:/file/findAll";
    }
    /**
     * 文件下载  在线查看
     * */
    @GetMapping("download")
    public void download(String openStyle,String id, HttpServletResponse response) throws IOException{
        //获取打开方式
        openStyle = openStyle == null?"attachment":openStyle;
        //获取文件信息
       UserFiles file = userFilesService.finById(id);
       //更新下载次数
        if("attachment".equals(openStyle)) {
            //如果是下载则更新下载次数
            file.setDownCount(file.getDownCount() + 1);
            userFilesService.update(file);
        }
       //根据文件信息中文件名字 和 文件存储路径获取文件输入流
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + file.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realPath,file.getNewFileName()));
        //附件下载
        response.setHeader("content-disposition",openStyle + ";fileName="+ URLEncoder.encode(file.getOldFileName(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("content-type", "text/html;charset=UTF-8");
        //文件拷贝
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    /**
     * 上传文件处理，并保存文件信息到数据库中
     * */
    @PostMapping("upload")
    public String upload(MultipartFile aaa) throws IOException {
        //获取上传用户id
        Session session1 = SecurityUtils.getSubject().getSession();
        User user = (User) session1.getAttribute("user");
        //获取文件原始名称
        String oldFileName = aaa.getOriginalFilename();
        //获取文件后缀
        String extention = "."+ FilenameUtils.getExtension(aaa.getOriginalFilename());
        //生成新的文件名称 时间戳+UUID
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extention;
        //文件大小
        long size = aaa.getSize();
        //文件类型
        String type = aaa.getContentType();
        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = realPath + "/" + dateFormat;
        File dateDir = new File(dateDirPath);
        if (!dateDir.exists()) dateDir.mkdirs();
        //处理文件上传
        aaa.transferTo(new File(dateDir,newFileName));
        //将文件放入数据库中
        UserFiles userFiles = new UserFiles();
        userFiles.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extention).setSize(String.valueOf(size)).setType(type)
                .setPath("/files/" + dateFormat).setUserId(user.getId());
        userFilesService.save(userFiles);
        return "redirect:/file/findAll";
    }

    /**
     *
     * 展示所有文件信息
     * */
    @GetMapping("findAll")
    public String findAll( Model model){
        Session session1 = SecurityUtils.getSubject().getSession();
        User user = (User) session1.getAttribute("user");


        List<UserFiles> files = userFilesService.findAllByUserId(user.getId());
        model.addAttribute("files",files);
        return "showAll";
    }
}
