package cn.andy;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

public class QiniuServiceImpl implements QiniuService {

    @Autowired
    private QiniuProperties qiniuProperties;

    private UploadManager uploadManager;

    private Auth auth;

    private Configuration configuration;

    QiniuServiceImpl(){
        Configuration configuration = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(configuration);
        this.configuration = configuration;
        this.uploadManager=uploadManager;
        this.auth = Auth.create(qiniuProperties.getAccesskey(),qiniuProperties.getSecretkey());
    }


    /**
     * 通过流上传文件
     * @param inputStream 文件流
     * @param mime 文件类型
     * @return
     */
    @Override
    public QiniuResponse uploadFile(InputStream inputStream,String mime) {
        try {
            if(inputStream != null){
                ByteArrayOutputStream byteOutputStream = FileUtils.copyInputStream(inputStream);
                InputStream md5InputSteam = new ByteArrayInputStream(byteOutputStream.toByteArray());
                InputStream fileInputSteam = new ByteArrayInputStream(byteOutputStream.toByteArray());
                String md5Hex = DigestUtils.md5Hex(md5InputSteam);
                Response response = this.uploadManager.put(fileInputSteam,
                        qiniuProperties.getFolder()+"/"+md5Hex,auth.uploadToken(qiniuProperties.getBucket()),null,mime);
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return QiniuResponse.build(putRet.key,qiniuProperties.getHost()+"/"+putRet.key);
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用key删除文件
     * @param key
     */
    @Override
    public void deleteFile(String key){
        BucketManager bucketManager = new BucketManager(auth,configuration);
        try {
            bucketManager.delete(qiniuProperties.getBucket(),key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
