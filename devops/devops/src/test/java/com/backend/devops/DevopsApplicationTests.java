package com.backend.devops;

import com.backend.devops.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class DevopsApplicationTests {

	@MockBean
	private UserRepository userRepository;  // if your service uses it

//	@MockBean
//	private KafkaProducer kafkaProducer;    // if your service uses it

	@Test
	void contextLoads() {
	}
}

