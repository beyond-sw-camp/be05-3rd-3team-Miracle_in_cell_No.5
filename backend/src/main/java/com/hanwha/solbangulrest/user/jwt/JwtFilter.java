package com.hanwha.solbangulrest.user.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hanwha.solbangulrest.user.domain.Role;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.CustomUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.info("토큰이 없습니다.");
			filterChain.doFilter(request, response);

			return;
		}

		String token = authorization.split(" ")[1];

		// if () {
		// 	log.info("토큰이 만료되었습니다.");
		// 	filterChain.doFilter(request, response);
		//
		// 	return;
		// }

		try {
			jwtUtil.isExpired(token);
		} catch (ExpiredJwtException e) {
			log.info("토큰이 만료되었습니다.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			filterChain.doFilter(request, response);

			return;
		}

		String loginId = jwtUtil.getLoginId(token);
		String role = jwtUtil.getRole(token);

		User user = User.builder()
			.loginId(loginId)
			.password("tokenPassword")
			.role(Role.valueOf(role))
			.build();

		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
