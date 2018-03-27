package list.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;

/**
 * @author ztang
 * @date 11:14 2018/3/27
 */
@RestController
public class UploadController {
    //跳转到上传文件的页面
    @RequestMapping(value = "/gouploadimg", method = RequestMethod.GET)
    public ModelAndView goUploadImg() {
        ModelAndView mav = new ModelAndView("test/uploadimg");
        System.out.println(mav.getModel());
        //跳转到 templates 目录下的 uploadimg.html
        return mav;
    }

    //处理文件上传
    @RequestMapping(value = "/testuploadimg", method = RequestMethod.POST)
    public @ResponseBody
    String uploadImg(@RequestParam("file") MultipartFile multfile,
                     HttpServletRequest request) {
       /* String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        *//*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*//*
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }*/
        String fileName = multfile.getOriginalFilename();

        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "<LTAIKXdZgQKa0RT9>";
        String accessKeySecret = "<>";
// 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
// 上传文件
        ossClient.putObject("<audiolist>", "<" + fileName + ">", new File(""));
// 关闭client
        ossClient.shutdown();

        //返回json
        return "uploadimg success";
    }

    @GetMapping("play")
    public void play(HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        Runtime rn = Runtime.getRuntime();
        Process p = null;
        try {
            p = rn.exec("\"C:\\Program Files\\Microsoft Office\\Office15/GROOVE.EXE\" " + filePath + "/周杰伦 - 告白气球.mp3");
        } catch (Exception e) {
            System.out.println("Error exec!");
        }
    }

    public static void main(String[] args) {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAIKXdZgQKa0RT9";
        String accessKeySecret = "";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传文件
        //ossClient.putObject("audiolist", "<告白气球.mp3>", new File("G:\\PlayAudio/周杰伦 - 告白气球.mp3"));
        OSSObject object = ossClient.getObject("audiolist", "<告白气球.mp3>");
        InputStream objectContent = object.getObjectContent();
        // 关闭client
        ossClient.shutdown();
    }

}
