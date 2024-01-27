package com.example.smartc;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Signature {

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
            KeyPair keyPair = generateKeyPair();

            byte[] document = "Электронная цифровая подпись".getBytes(StandardCharsets.UTF_8);
            byte[] signature = signDocument(document, keyPair.getPrivate());

            boolean verified = verifySignature(document, signature, keyPair.getPublic());
            System.out.println("Подпись проверена: " + verified);

    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] signDocument(byte[] document, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signature = java.security.Signature.getInstance("SHA256withRSA");
        // подписание приватным ключом
        signature.initSign(privateKey);
        signature.update(document);
        return signature.sign();
    }

    private static boolean verifySignature(byte[] document, byte[] signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature verifier = java.security.Signature.getInstance("SHA256withRSA");
        // верификация публичным ключом
        verifier.initVerify(publicKey);
        verifier.update(document);
        return verifier.verify(signature);
    }
}
