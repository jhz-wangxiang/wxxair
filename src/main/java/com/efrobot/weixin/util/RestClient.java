package com.efrobot.weixin.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @Description RestClient客户端
 *
 * @author wurui. Email:wurui@ren001.com
 *
 * @date 2016年5月3日 下午1:44:19
 */
public class RestClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

	private static RestTemplate restTemplate;

	static {
		// 长连接保持30秒
		PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		// 总连接数
		pollingConnectionManager.setMaxTotal(1000);
		// 同路由的并发数
		pollingConnectionManager.setDefaultMaxPerRoute(1000);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		// 重试次数，默认是3次，没有开启

		// modify by wurui 
		//使用DefaultHttpRequestRetryHandler时InterruptedIOException,UnknownHostException,ConnectException,SSLException这几个异常以及他的子异常都不会重复执行.SocketTimeoutException继承自InterruptedIOException因此超时也不会重试
		// httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 3) {
					return false;
				}
				/*
				if (exception instanceof InterruptedIOException) {
					return false;
				}
				*/
				if (exception instanceof UnknownHostException) {
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					return false;
				}
				if (exception instanceof SSLException) {
					return false;
				}
				/*
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					return true;
				}
				*/
				return true;
			}

		};
		httpClientBuilder.setRetryHandler(myRetryHandler);

		// 保持长连接配置，需要在头添加Keep-Alive
		httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));

		httpClientBuilder.setDefaultHeaders(headers);

		HttpClient httpClient = httpClientBuilder.build();

		// httpClient连接配置，底层是配置RequestConfig
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		// 连接超时
		clientHttpRequestFactory.setConnectTimeout(10 * 1000);
		// 数据读取超时时间，即SocketTimeout
		clientHttpRequestFactory.setReadTimeout(20 * 1000);
		// 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
		clientHttpRequestFactory.setConnectionRequestTimeout(10 * 1000);
		// 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
		clientHttpRequestFactory.setBufferRequestBody(true);

		// 添加内容转换器
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate = new RestTemplate(messageConverters);
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

		LOGGER.info("RestClient初始化完成");
	}

	private RestClient() {

	}

	/**
	* 获取一个application/x-www-form-urlencoded头
	*
	* @return
	*/
	public static HttpHeaders buildBasicFORMHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	/**
	 * 获取一个application/json头
	 *
	 * @return
	 */
	public static HttpHeaders buildBasicJSONHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return headers;
	}

	/**
	 * 获取一个text/html头
	 *
	 * @return
	 */
	public static HttpHeaders buildBasicHTMLHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		return headers;
	}

	public static RestTemplate getInstance() {
		return restTemplate;
	}
}
