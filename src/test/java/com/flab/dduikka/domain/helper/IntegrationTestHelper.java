package com.flab.dduikka.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.dduikka.domain.login.api.LoginController;
import com.flab.dduikka.domain.login.application.LoginService;

@WebMvcTest(controllers = {
	LoginController.class
})
public abstract class IntegrationTestHelper {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected LoginService loginService;

	@Autowired
	protected ObjectMapper objectMapper;
}
