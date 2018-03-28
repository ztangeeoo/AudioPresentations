package list.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author ztang
 * @date 13:00 2018/3/28
 */
@Document(collection = "file_info")
public class FileInfo {
    //书名 文件名 文件url  创建时间
    @Field("book_id")
    private String bookId;
    @Field("booK_name")
    private String bookName;
    @Field("file_name")
    private String fileName;
    @Field("file_url")
    private String fileUrl;
    @Field("create_at")
    private Date createAt;

    public FileInfo() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
