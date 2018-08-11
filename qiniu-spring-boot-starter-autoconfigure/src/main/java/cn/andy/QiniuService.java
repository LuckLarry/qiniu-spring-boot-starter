package cn.andy;


import java.io.InputStream;

public interface QiniuService {
    QiniuResponse uploadFile(InputStream inputStream,String mime);
    void deleteFile(String key);
}
