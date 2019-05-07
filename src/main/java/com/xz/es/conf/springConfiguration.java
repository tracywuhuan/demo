package com.xz.es.conf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class springConfiguration {

	/**
     * Transport client transport client.
     * 如果配置X-PACK ,则需要在此处配置用户信息
     *
     * @return the transport client
     * @throws UnknownHostException the unknown host exception
     */
    @Bean
    public TransportClient transportClient() throws UnknownHostException {
        TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "my-application")
                .put("xpack.security.user", "elastic:123456")
                .put("xpack.security.transport.ssl.enabled",true)
                .put("xpack.security.transport.ssl.truststore.path", "certs/elastic-certificates.p12")
                .put("xpack.security.transport.ssl.keystore.path", "certs/elastic-certificates.p12")
                .put("xpack.security.transport.ssl.verification_mode", "certificate")
                .build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.0.0.7"), 9300));
        return client;
    }
}
