package com.segbaus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.embedded.netty.SslServerCustomizer;
import org.springframework.boot.web.server.Http2;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLServerSocket;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class NettyConfiguration implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Value("${PORT}")
    private Integer port;

    @Value("${KEYSTORE}")
    private String keyStore;

    @Value("${KEYSTORE_PASSWORD}")
    private String keyStorePassword;

    @Value("${KEY_PASSWORD}")
    private String keyPassword;

    @Override
    public void customize(NettyReactiveWebServerFactory webServerFactory) {
        Ssl ssl = new Ssl();
        ssl.setKeyStore(keyStore);
        ssl.setKeyStorePassword(keyStorePassword);
        ssl.setKeyPassword(keyPassword);

        webServerFactory.setPort(port);
        webServerFactory.setSsl(ssl);
    }
}
