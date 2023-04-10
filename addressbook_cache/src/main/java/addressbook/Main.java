package addressbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@SpringBootApplication(exclude = HazelcastAutoConfiguration.class)
public class Main {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Main.class)
				.listeners((ContextRefreshedEvent event) -> log.info("====================== S E R V E R   S T A R T E D ======================"))
				.listeners((ContextClosedEvent event) -> log.info("======================= S E R V E R   S T O P E D ======================="))
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

}
