package com.crsm.maker.socketService.socks5ProxyService;

import lombok.Data;

/**
 * creat by Ccr on 2019/4/10
 **/
@Data
public class ProxyConfig {

    private String ServerAddress;

    private int ServerPort;

    private String Password;

    private int TimeOut;

    private String Method;


}
