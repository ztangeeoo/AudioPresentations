package list.service;

import list.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * @author ztang
 * @date 12:56 2018/3/28
 */
public interface FileService {
    /**
     * 文件上传
     */
    String Upload(MultipartFile file, String bookName);

    ArrayList<FileInfo> getAudioList(String bookId);


}
