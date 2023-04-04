package addressbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
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

    private URI calcEndPoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        calcEndPoint = new URI(host + ":" + port + api);
        log.info("Created URL:{}", calcEndPoint);
    }


    public boolean getUserByUserName(String username) {
        log.info("Auth serv");
        return true;
    }
}
