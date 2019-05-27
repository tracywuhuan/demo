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
	private String key;
	
	@NotNull
	private String crt;
	
	@NotNull
	private String ca;
	
	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

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
	
	
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCrt() {
		return crt;
	}

	public void setCrt(String crt) {
		this.crt = crt;
	}

	@Bean(destroyMethod = "close")
    public TransportClient transportClient() throws Exception {
    	//String address = "9.119.56.156";
        @SuppressWarnings("resource")
		TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "my-application")
                .put("xpack.security.user", "elastic:dev@123")
                .put("xpack.security.transport.ssl.enabled",true)
                //加上sniffer 阿里云的连不上了。。。
                //.put("client.transport.sniff", true)
                .put("xpack.security.transport.ssl.key", key)
                .put("xpack.security.transport.ssl.certificate", crt)
                .put("xpack.ssl.certificate_authorities",ca)
                .put("xpack.security.transport.ssl.verification_mode", "certificate")
                .build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName(address), port));
        return client;
    }
}
