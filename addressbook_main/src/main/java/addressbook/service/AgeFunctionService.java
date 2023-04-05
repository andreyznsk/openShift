package addressbook.service;

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
import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgeFunctionService implements InitializingBean {
    private final RestTemplate restTemplate;

    @Value("${app.ageCalcFunc.host}")
    private String host;

    @Value("${app.ageCalcFunc.port}")
    private Integer port;

    @Value("${app.ageCalcFunc.api}")
    private String api;

    private URI calcEndPoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        calcEndPoint = new URI(host + ":" + port + api);
        log.info("Created URL:{}", calcEndPoint);
    }

    public int calcAge(Timestamp birthday) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Timestamp> entityReq = new HttpEntity<>(birthday, headers);


        log.info("post \n\tHeader: {} \n\tbody: {}\n\tUrl: {}", headers, JsonUtil.toJson(birthday), calcEndPoint);
        ResponseEntity<Integer> respEntity = restTemplate
                .postForEntity(calcEndPoint, entityReq, Integer.class);
        log.debug("POST findObjectsResponse response status \n\tcode:{};\n\tHeaders:{};\n\tBody:{}",
                respEntity.getStatusCode(),
                respEntity.getHeaders(),
                respEntity.getBody());

        if (respEntity.getStatusCode().is2xxSuccessful() && respEntity.getBody() != null) {
            return respEntity.getBody();
        } else {
            log.error("Status is not OK!!!");
            throw new RuntimeException("error with server app_calc_func. : {} " + JsonUtil.toJson(respEntity.getBody()));
        }
    }
}
