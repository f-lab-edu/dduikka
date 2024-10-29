package com.flab.dduikka.domain.helper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.flab.dduikka.common.config.DatabaseClearExtension;

@ExtendWith(DatabaseClearExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestHelper {

}
