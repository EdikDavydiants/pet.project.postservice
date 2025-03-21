package pet.project.postservice.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pet.project.postservice.exception.AuthHeaderIsMissingException;
import pet.project.postservice.exception.WrongTokenTypeException;

import javax.crypto.SecretKey;

import static pet.project.postservice.constant.ErrorMessagesUtil.AUTH_HEADER_IS_MISSING;
import static pet.project.postservice.constant.ErrorMessagesUtil.WRONG_TOKEN_TYPE;

public class JwtUtils {

    public static final String SECRET_KEY = "это секретный ключ, который хранится в коде";

    public static Long extractIdFromJwt(String jwt) {

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();

        String idStr = jwtParser
                .parseSignedClaims(jwt)
                .getPayload()
                .getId();

        return Long.parseLong(idStr);
    }

    public static SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String extractTokenFromHeader(String authHeader) {
        if(authHeader == null) {
            throw new AuthHeaderIsMissingException(AUTH_HEADER_IS_MISSING);
        }
        if(!authHeader.startsWith("Bearer ")) {
            throw new WrongTokenTypeException(WRONG_TOKEN_TYPE);
        }
        return authHeader.substring(7);
    }
}
