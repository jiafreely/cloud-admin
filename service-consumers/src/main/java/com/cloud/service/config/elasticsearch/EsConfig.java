package com.cloud.service.config.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author xjh
 * @version 1.0
 * @ClassName: EsConfig
 * @description: elasticsearch配置类
 * @date 2021/12/28 9:21
 */
@Slf4j
//@Configuration
public class EsConfig {
    @Value("${elasticsearch.host}")
    public String host;
    /**
     * 之前使用transport的接口的时候是9300端口，现在使用HighLevelClient则是9200端口
     */
    @Value("${elasticsearch.port}")
    public int port;
    public static final String SCHEME = "http";
    @Value("${elasticsearch.username}")
    public String username;
    @Value("${elasticsearch.authenticationPassword}")
    public String authenticationPassword;

    @Bean(name = "remoteHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,
                authenticationPassword));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, SCHEME)).
                setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
    }
}
