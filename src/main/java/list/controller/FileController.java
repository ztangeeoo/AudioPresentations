package list.controller;

import com.aliyun.oss.OSSClient;
import list.dto.ResultUtil;
import list.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * @author ztang
 * @date 11:14 2018/3/27
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/gouploadimg")
    public ModelAndView goUploadImg() {
        return new ModelAndView("test/uploadimg");
    }


    @PostMapping(value = "/uploadimg")
    public @ResponseBody
    Object uploadImg(@RequestParam("file") MultipartFile file, String bookName) {
        fileService.Upload(file, bookName);
        return ResultUtil.success();
    }

    @GetMapping(value = "getAudioList/{bookId}")
    public ModelAndView getAudioList(@PathVariable String bookId, Model model) {
        model.addAttribute("list",fileService.getAudioList(bookId));
        return new ModelAndView("index");
    }

    public static void main(String[] args) {
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAIKXdZgQKa0RT9";
        String accessKeySecret = "Ohci0Ycs3NnXJGCRRPhcGFkXaDnIZ9";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传文件
        ossClient.putObject("audiolist", "1.jpg", new File("G:\\PlayAudio/1.jpg"));
        //  OSSObject object = ossClient.getObject("audiolist", "<告白气球.mp3>");
//        InputStream objectContent = object.getObjectContent();
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);// 生成URL
        URL url = ossClient.generatePresignedUrl("audiolist", "1.jpg", expiration);
        System.out.println(url);
        // 关闭client
        ossClient.shutdown();
    }

}
