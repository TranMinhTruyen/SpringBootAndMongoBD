package com.example.common.jwt;

import com.example.common.model.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Tran Minh Truyen
 */

@Component
@Slf4j
public class JWTTokenProvider {
	static final long EXPIRATIONTIME = 100000000;
	static final String SECRET = "UnlimitedBladeWork";

	public String generateToken(CustomUserDetail userDetail) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATIONTIME);
		Claims claims = Jwts.claims().setSubject(Integer.toString(userDetail.getUser().getId()));
		boolean admin = userDetail.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
		boolean employee = userDetail.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("EMP"));
		if (admin)
			claims.put("role", "ADMIN");
		else
		{
			if (employee)
				claims.put("role", "EMP");
			else claims.put("role", "USER");
		}
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public int getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody();
		return Integer.parseInt(claims.getSubject());
	}

	public String getRoleFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody();
		return (String) claims.get("role");
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
