package addressbook.service;

import addressbook.dto.RequestLog;
import addressbook.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements InitializingBean {

    private final RestTemplate restTemplate;

    @Value("${app.auth.host}")
    private String host;

    @Value("${app.auth.port}")
    private Integer port;

    @Value("${app.auth.api}")
    private String api;

    private URI authEndPoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        authEndPoint = new URI(host + ":" + port + api);
        log.info("Created auth URL:{}", authEndPoint);
    }


    public boolean isUserAuthByUserName(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestLog> entityReq = new HttpEntity<>(new RequestLog(username), headers);

        log.debug("post \n\tHeader: {} \n\tbody: {}\n\tUrl: {}", headers, JsonUtil.toJson(username), authEndPoint);
        ResponseEntity<Boolean> respEntity = restTemplate
                .postForEntity(authEndPoint, entityReq, Boolean.class);
        Boolean body = respEntity.getBody();
        log.debug("POST findObjectsResponse response status \n\tcode:{};\n\tHeaders:{};\n\tBody:{}",
                respEntity.getStatusCode(),
                respEntity.getHeaders(),
                body);

        if (respEntity.getStatusCode().is2xxSuccessful() && body != null) {
            log.info("user with login authed: {}", body);
            return body;
        } else {
            log.error("Status is not OK!!!");
            throw new RuntimeException("error with server app_calc_func. : {} " + JsonUtil.toJson(body));
        }
    }


}
