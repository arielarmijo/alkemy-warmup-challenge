package tk.monkeycode.warmup.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tk.monkeycode.warmup.exception.WarmUpException;

@Service
public class JWTService {
	
	private KeyStore keyStore;
	private static final Long EXPIRATION_TIME = 86400000L;
	
	@PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/warmup.jks");
            keyStore.load(resourceAsStream, "warmup".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new WarmUpException("Error al cargar el keystore", e);
        }

    }
	
	public String generate(String username) {
		Instant now = Instant.now();
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(Date.from(now))
				   .setExpiration(Date.from(now.plusMillis(EXPIRATION_TIME)))
				   .signWith(getPrivateKey(), SignatureAlgorithm.RS512)
				   .compact();
	}
	
	public boolean validate(String jwt) {
		try {
			Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
			return true;			
		} catch (JwtException e) {
			throw new WarmUpException("Error al validar token", e);
		}
	}
	
	public String getUsername(String jwt) {
		try {
			Jws<Claims>jws = Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
			return jws.getBody().getSubject();			
		} catch (JwtException e) {
			throw new WarmUpException("Error al obtener nombre de usuario - ", e);
		}
	}
	
	private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("warmup", "warmup".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new WarmUpException("Exception occured while retrieving public key from keystore - ", e);
        }
    }
	
	private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("warmup").getPublicKey();
        } catch (KeyStoreException e) {
            throw new WarmUpException("Exception occured while retrieving public key from keystore - ", e);
        }
    }

}
