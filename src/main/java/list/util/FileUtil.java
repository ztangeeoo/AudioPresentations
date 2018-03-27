package list.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author ztang
 * @date 11:14 2018/3/27
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filePath + fileName)) {
            out.write(file);
            out.flush();
            out.close();
        }
    }
}
