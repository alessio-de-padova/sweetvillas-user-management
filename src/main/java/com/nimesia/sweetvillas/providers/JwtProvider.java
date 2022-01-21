package com.nimesia.sweetvillas.providers;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.nimesia.sweetvillas.config.SecurityConfig;
import org.joda.time.DateTime;

public class JwtProvider {

    private static final String issuer = "sweetvillas-service";

    /**
     * Create jwt string.
     *
     * @param subject the subject
     * @return the JWT string
     */
    public static String createJwt(String subject) {
        JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withIssuedAt(DateTime.now().toDate())
                .withExpiresAt(DateTime.now().plusMonths(1).toDate());

        return builder.sign(Algorithm.HMAC256(SecurityConfig.secret));
    }

    /**
     * Verify jwt decoded.
     *
     * @param jwt the JWT string
     * @return the decoded JWT
     */
    public static DecodedJWT verifyJwt(String jwt) {
        return JWT.require(Algorithm.HMAC256(SecurityConfig.secret))
                .build()
                .verify(jwt);
    }

}