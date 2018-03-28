package list.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ObjectMetadata;
import list.dao.FileInfoRepository;
import list.dto.ResultEnum;
import list.entity.FileInfo;
import list.exception.AudioException;
import list.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @author ztang
 * @date 12:58 2018/3/28
 */
@Service
public class FileServiceImpl implements FileService {
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Autowired
    private FileInfoRepository fileInfoRepository;


    @Override
    public void Upload(MultipartFile file, String bookName) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        OSSClient ossClient = new OSSClient(endpoint, new DefaultCredentialProvider(accessKeyId, accessKeySecret), null);
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // meta.setContentLength(file.length());
        try {
            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(file.getBytes()));
            meta.setContentMD5(md5);
            meta.setContentType(contentType);
            ossClient.putObject("audiolist", fileName, new ByteArrayInputStream(file.getBytes()), meta);
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);// 生成URL
            URL url = ossClient.generatePresignedUrl("audiolist", fileName, expiration);
            FileInfo info = fileInfoRepository.findByBookName(bookName);
            if (info == null) {
                FileInfo fileInfo = new FileInfo();
                String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                fileInfo.setBookId(id);
                fileInfo.setBookName(bookName);
                fileInfo.setFileName(fileName);
                fileInfo.setFileUrl(url.toString());
                fileInfo.setCreateAt(new Date());
                fileInfoRepository.save(fileInfo);
            } else {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setBookId(info.getBookId());
                fileInfo.setBookName(info.getBookName());
                fileInfo.setFileName(fileName);
                fileInfo.setFileUrl(url.toString());
                fileInfo.setCreateAt(new Date());
            }
        } catch (IOException e) {
            throw new AudioException(ResultEnum.RC_0401001);
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public ArrayList<FileInfo> getAudioList(String bookId) {
        return fileInfoRepository.findByBookId(bookId);
    }
}
