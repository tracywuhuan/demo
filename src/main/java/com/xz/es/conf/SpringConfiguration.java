package com.xz.es.conf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfiguration {

	/**
     * Transport client transport client.
     * 如果配置X-PACK ,则需要在此处配置用户信息
     *
     * @return the transport client
     * @throws UnknownHostException the unknown host exception
     */
	@Value("${elasticsearch.address}")
	private String address;
	
	@Value("${elasticsearch.port}")
	private int port;
	
    @Bean
    public TransportClient transportClient() throws UnknownHostException {
    	//String address = "9.119.56.156";
        TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "my-application")
                .put("xpack.security.user", "elastic:123456")
                .put("xpack.security.transport.ssl.enabled",true)
                .put("client.transport.sniff", true)
                .put("xpack.security.transport.ssl.truststore.path", "certs/elastic-certificates.p12")
                .put("xpack.security.transport.ssl.keystore.path", "certs/elastic-certificates.p12")
                .put("xpack.security.transport.ssl.verification_mode", "certificate")
                .build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
        return client;
    }
}
