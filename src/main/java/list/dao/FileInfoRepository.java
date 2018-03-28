package list.dao;

import list.entity.FileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


public interface FileInfoRepository extends MongoRepository<FileInfo, String> {

    FileInfo findByBookName(String bookName);

    ArrayList<FileInfo> findByBookId(String bookId);

}
