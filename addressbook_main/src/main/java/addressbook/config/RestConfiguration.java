package addressbook.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RestConfiguration {

    @Value("${conf.rest.request.connect-timeout:60000}")
    long connectTimeout;
    @Value("${conf.rest.request.read-timeout:60000}")
    long readTimeout;

    @Bean
    public RestTemplate restTemplateEkd(RestTemplateBuilder builder) throws Exception {
        log.info("Start Rest configuration with connection TimeOut: {} min, and Read TimeOut: {}, min...",
                connectTimeout/60000, readTimeout/60000);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);


        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        List<HttpMessageConverter<?>> httpMessageConverterList = Collections.singletonList(jacksonConverter);
        return builder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .requestFactory(() -> requestFactory)
                .messageConverters(httpMessageConverterList)
                .build();
    }

   }
