package com.sinothk.cloud.file.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// https://www.cnblogs.com/wangkaihua/p/10165290.html
@Data
@ToString
@Component("serverConfig")
public class ServerConfig {

    @Value("${config.virtualPath}")
    private String virtualPath;

    @Value("${config.isDebug}")
    private boolean isDebug;

    @Value("${config.fileMaxSize}")
    private String fileMaxSize;
}