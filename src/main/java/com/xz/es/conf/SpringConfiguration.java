package com.xz.es.conf;

import java.net.InetAddress;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Validated
public class SpringConfiguration {

	/**
     * Transport client transport client.
     * 如果配置X-PACK ,则需要在此处配置用户信息,否则直接在application.properties里面直接设置
     *
     * @return the transport client
     * @throws UnknownHostException the unknown host exception
     */
	@NotNull
	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
	private String address;

	@NotNull
	private int port;
	
	@NotNull
	private String keyStorePath;
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	
    @Bean(destroyMethod = "close")
    public TransportClient transportClient() throws Exception {
    	//String address = "9.119.56.156";
        @SuppressWarnings("resource")
		TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "my-application")
                .put("xpack.security.user", "wuhuan:123456")
                .put("xpack.security.transport.ssl.enabled",true)
                .put("client.transport.sniff", true)
                //.put("xpack.security.transport.ssl.truststore.path", "certs/elastic-certificates.p12")
                .put("xpack.security.transport.ssl.keystore.path", keyStorePath)
                .put("xpack.security.transport.ssl.verification_mode", "certificate")
                .build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
        return client;
    }
}
