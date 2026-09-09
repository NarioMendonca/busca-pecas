package com.buscapecas.app.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACOES = 120_000;
    private static final int TAMANHO_CHAVE_BITS = 256;
    private static final int TAMANHO_SALT_BYTES = 16;

    public String gerarHash(String senhaEmTexto) {
        byte[] salt = gerarSalt();
        byte[] hash = calcularHash(senhaEmTexto.toCharArray(), salt);

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    public boolean verificar(String senhaEmTexto, String hashArmazenado) {
        if (senhaEmTexto == null || hashArmazenado == null) {
            return false;
        }

        String[] partes = hashArmazenado.split(":");
        if (partes.length != 2) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] hashEsperado = Base64.getDecoder().decode(partes[1]);
        byte[] hashCalculado = calcularHash(senhaEmTexto.toCharArray(), salt);

        return MessageDigest.isEqual(hashEsperado, hashCalculado);
    }

    private byte[] gerarSalt() {
        byte[] salt = new byte[TAMANHO_SALT_BYTES];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private byte[] calcularHash(char[] senha, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(senha, salt, ITERACOES, TAMANHO_CHAVE_BITS);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Erro ao gerar hash de senha.", e);
        }
    }
}
