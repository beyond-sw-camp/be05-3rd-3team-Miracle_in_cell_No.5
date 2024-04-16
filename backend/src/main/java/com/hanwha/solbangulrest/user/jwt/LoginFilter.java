package com.hanwha.solbangulrest.user.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.user.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		String loginId = obtainUsername(request);
		String password = obtainPassword(request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();

		String loginId = userDetails.getUsername();
		String role = getRole(userDetails);

		String token = jwtUtil.createToken(loginId, role, 60 * 60 * 1000L);

		response.addHeader("Authorization", "Bearer " + token);
		Result<Void> result = new Result<>(true, "로그인 성공", null);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(result);

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		response.getWriter().write(json);
	}

	private static String getRole(CustomUserDetails userDetails) {
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		return auth.getAuthority();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		Result<Void> result = new Result<>(false, "로그인 실패", null);

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(result);

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		response.getWriter().write(json);
	}
}
