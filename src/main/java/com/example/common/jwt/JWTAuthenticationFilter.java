package com.example.common.jwt;

import com.example.common.model.User;
import com.example.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tran Minh Truyen
 */

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	private UserServices userServices;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				int userId = jwtTokenProvider.getUserIdFromJWT(jwt);

				UserDetails userDetails = userServices.loadUserById(userId);
				if(userDetails != null) {
					UsernamePasswordAuthenticationToken
							authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			log.error("failed on set user authentication", ex);
		}
		filterChain.doFilter(request, response);
	}

	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
