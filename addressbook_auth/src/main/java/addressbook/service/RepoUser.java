package addressbook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RepoUser implements InitializingBean {

    @Value("${user.localDir}")
    private String localFolder;

    private Set<String> users = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        File queueJson = new File(localFolder);
        if (!queueJson.exists()) {
            log.error("File users not found by path: {}", queueJson.getAbsolutePath());
            throw new RuntimeException("File users not found by path: " + queueJson.getAbsolutePath());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<?> list = objectMapper.readValue(queueJson, List.class);
            for (Object o : list) {
                users.add(o.toString());
            }
        } catch (IOException e) {
            log.error("some problem:", e);
            throw new RuntimeException("Error reading file by path: " + queueJson.getAbsolutePath());
        }
    }


    public Boolean checkByLogin(String login) {
        return users.contains(login);
    }
}
