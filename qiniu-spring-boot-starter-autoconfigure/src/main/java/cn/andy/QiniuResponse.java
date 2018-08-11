package cn.andy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class QiniuResponse {
    private String key;
    private String url;

    public static QiniuResponse build(String key,String url){
        QiniuResponse qiniuResponse = new QiniuResponse(key,url);
        return qiniuResponse;
    }
}
