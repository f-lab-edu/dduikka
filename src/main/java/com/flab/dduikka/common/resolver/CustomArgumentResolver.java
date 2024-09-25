package com.flab.dduikka.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.flab.dduikka.common.annotation.Member;
import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.login.exception.LoginException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Member.class);
		boolean hasType = SessionMember.class.isAssignableFrom(parameter.getParameterType());
		return hasParameterAnnotation && hasType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = httpServletRequest.getSession(false);
		if (session == null) {
			throw new LoginException.UnauthorizedAccessException("허용할 수 없는 접근입니다.");
		}
		return session.getAttribute(SessionKey.LOGIN_USER.name());
	}
}
