package com.gestion.equiposfutbol.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gestion.equiposfutbol.dto.response.LoggedUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenService {

    private final String ttl = "40000000";
    private static final String SEED = "seedTokenGenerator";
    private static final String USER_CLAIM = "user";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public String getJwtToken(LoggedUser user) {

        String jwtToken = null;
        try {
            jwtToken = createJWTAndSign(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jwtToken;
    }

    public LoggedUser getUserFromToken(String token) throws Exception {
        LoggedUser user = null;
        try {
            verifyToken(token);

            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(SEED))
                    .parseClaimsJws(token)
                    .getBody();
            final ObjectMapper mapper = new ObjectMapper();
            user = mapper.convertValue(claims.get(USER_CLAIM), LoggedUser.class);

        } catch (ExpiredJwtException e) {
            throw new Exception("Token expired");
        } catch (Exception e) {
            throw new Exception("Could not get all claims Token from passed token");
        }

        return user;
    }

    private String createJWTAndSign(LoggedUser user) throws Exception {
        JwtBuilder builder = Jwts.builder()
                .claim(USER_CLAIM, user)
                .signWith(SIGNATURE_ALGORITHM, Base64.getDecoder().decode(SEED))
                .setExpiration(getExpiration());

        return builder.compact();
    }

    private Date getExpiration() {
        long ttlMillis = Long.parseLong(ttl);
        long nowMillis = System.currentTimeMillis();

        long expMillis = nowMillis + ttlMillis;
        return new Date(expMillis);
    }

    private void verifyToken(String token) throws Exception {
        String[] chunks = token.split("\\.");

        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(SEED), SIGNATURE_ALGORITHM.getJcaName());
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SIGNATURE_ALGORITHM, secretKeySpec);

        if (!validator.isValid(tokenWithoutSignature, signature))
            throw new Exception("Could not verify JWT token integrity!");
    }

}
