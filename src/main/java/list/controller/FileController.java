package list.controller;

import list.dto.ResultUtil;
import list.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

/**
 * @author ztang
 * @date 11:14 2018/3/27
 */
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/goupload")
    public ModelAndView goUploadImg() {
        return new ModelAndView("upload");
    }


    @PostMapping(value = "/upload")
    public @ResponseBody
    Object uploadImg(@RequestParam("file") MultipartFile file, String bookName) {
        return ResultUtil.success("你的书名编号是:" + fileService.Upload(file, bookName));
    }

    @GetMapping(value = "getAudioList/{bookId}")
    public ModelAndView getAudioList(@PathVariable String bookId, Model model) {
        model.addAttribute("list", fileService.getAudioList(bookId));
        return new ModelAndView("list");
    }

    @PostMapping(value = "getQR")
    public void getQR() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E7%BE%8E%E5%A5%B3&step_word=&hs=0&pn=2&spn=0&di=83662111520&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3878847766%2C3988120331&os=1532124407%2C2118980604&simid=0%2C0&adpicid=0&lpn=0&ln=3942&fr=&fmq=1522832111213_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=girl&bdtype=13&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%3Dpixel_huitu%2C0%2C0%2C294%2C40%2Fsign%3Decfe83b9042442a7ba03f5e5b83bc827%2F728da9773912b31bc2fe74138d18367adab4e17e.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bi7tp7_z%26e3Bv54AzdH3Fri5p5AzdH3Ffi5oAzdH3Fda8ma0d9AzdH3F8cda9c9l0caa_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=",
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);

            byte[] result = response.getBody();

            inputStream = new ByteArrayInputStream(result);

            File file = new File("D://test01.jpeg");
            if (!file.exists()) {
                file.createNewFile();
            }

            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


}
