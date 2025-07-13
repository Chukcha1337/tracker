package com.chuckcha.tt.core.auth;

import com.chuckcha.tt.core.exception.KeyLoadException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@UtilityClass
public class JwtUtils {

    public RSAPublicKey loadPublicKey(String path) {
        System.out.println("Loading private key from: " + path);
        try (InputStream is = JwtUtils.class.getClassLoader().getResourceAsStream(path)) {
            System.out.println("IS NULL? " + (is == null));
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);

        } catch (Exception e) {
            throw new KeyLoadException("Could not load public key");
        }
    }

    public RSAPrivateKey loadPrivateKey(String path) {
        try (InputStream is = JwtUtils.class.getClassLoader().getResourceAsStream(path)) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);

        } catch (Exception e) {
            throw new KeyLoadException("Could not load private key");
        }
    }

    public Jws<Claims> parse(String token, RSAPublicKey publicKey) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token);
    }

    public boolean isInvalid(String token, RSAPublicKey publicKey) {
        try {
            return !parse(token, publicKey).getPayload().getExpiration().after(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String getId(String token, RSAPublicKey publicKey) {
        return parse(token, publicKey).getPayload().get("id", String.class);
    }

    public String getSubject(String token, RSAPublicKey publicKey) {
        return parse(token, publicKey).getPayload().getSubject();
    }

    public String getRole(String token, RSAPublicKey publicKey) {
        return parse(token, publicKey).getPayload().get("role", String.class);
    }

    public long getExpirationMillis(String token, RSAPublicKey publicKey) {
        return parse(token, publicKey).getPayload().getExpiration().getTime() - System.currentTimeMillis();
    }
}

