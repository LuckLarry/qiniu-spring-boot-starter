package cn.andy;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuAutoConfiguration {

    @Bean
    public QiniuService qiniuService(){
        return new QiniuServiceImpl();
    }
}