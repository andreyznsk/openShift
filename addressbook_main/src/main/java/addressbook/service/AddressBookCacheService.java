package addressbook.service;

import addressbook.dto.AddressBookDto;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressBookCacheService implements InitializingBean {

    private final RestTemplate restTemplate;

    @Value("${app.cache.host}")
    private String host;

    @Value("${app.cache.port}")
    private Integer port;

    @Value("${app.cache.api}")
    private String api;

    private URI calcEndPoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        calcEndPoint = new URI(host + ":" + port + api);
        log.info("Created URL:{}", calcEndPoint);
    }

    public Optional<AddressBookDto> findById(Long id) {

            ResponseEntity<AddressBookDto> respEntity = restTemplate
                .getForEntity(calcEndPoint + "/addressbook/" + id, AddressBookDto.class);


        log.debug("GET findObjectsResponse response status \n\tcode:{};\n\tHeaders:{};\n\tBody:{}",
                respEntity.getStatusCode(),
                respEntity.getHeaders(),
                respEntity.getBody());

        if (respEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(respEntity.getBody());
        } else {
            log.error("Status is not OK!!!");
            throw new RuntimeException("error with server app_calc_func. : {} " + JsonUtil.toJson(respEntity.getBody()));
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<AddressBookDto> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        log.debug("post \n\tHeader: {} \n\tbody: {}\n\tUrl: {}", headers, null, calcEndPoint);
        ResponseEntity<List> respEntity = restTemplate
                .getForEntity(calcEndPoint + "/addressbooks/", List.class);

        log.debug("POST findObjectsResponse response status \n\tcode:{};\n\tHeaders:{};\n\tBody:{}",
                respEntity.getStatusCode(),
                respEntity.getHeaders(),
                respEntity.getBody());

        if (respEntity.getStatusCode().is2xxSuccessful()) {
            return (List<AddressBookDto>) respEntity.getBody();
        } else {
            log.error("Status is not OK!!!");
            throw new RuntimeException("error with server app_calc_func. : {} " + JsonUtil.toJson(respEntity.getBody()));
        }
    }

    public AddressBookDto save(AddressBookDto addressBook) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<AddressBookDto> entityReq = new HttpEntity<>(addressBook, headers);


        log.debug("post \n\tHeader: {} \n\tbody: {}\n\tUrl: {}", headers, null, calcEndPoint);
        ResponseEntity<AddressBookDto> respEntity = restTemplate
                .postForEntity(calcEndPoint, entityReq, AddressBookDto.class);

        log.debug("POST findObjectsResponse response status \n\tcode:{};\n\tHeaders:{};\n\tBody:{}",
                respEntity.getStatusCode(),
                respEntity.getHeaders(),
                respEntity.getBody());

        if (respEntity.getStatusCode().is2xxSuccessful()) {
            return respEntity.getBody();
        } else {
            log.error("Status is not OK!!!");
            throw new RuntimeException("error with server app_calc_func. : {} " + JsonUtil.toJson(respEntity.getBody()));
        }
    }

    public boolean delete(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Long> entityReq = new HttpEntity<>(id, headers);


        log.debug("post \n\tHeader: {} \n\tbody: {}\n\tUrl: {}", headers, null, calcEndPoint);
        ResponseEntity<Boolean> respEntity = restTemplate
                .postForEntity(calcEndPoint, entityReq, Boolean.class);

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
