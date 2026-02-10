package uk.co.sangharsh.service.calendar;

import org.apache.commons.compress.utils.IOUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    @Test
    void testFileEncryptionDecryption() throws Exception {
        // 1. Generate and Save AES Key
        SecretKey secretKey = AESEncryptionService.generateKey();
        Path keyPath = Paths.get("myKey.key");
        saveSecretKey(secretKey, keyPath);
        SecretKey loadedSecretKey = loadSecretKey(keyPath);

        IvParameterSpec iv = AESEncryptionService.generateIv();
        assertNotNull(iv);

        URL resource = getClass().getClassLoader().getResource("payload.xml");
        assertNotNull(resource, "payload.xml not found in resources");
        File originalFile = new File(resource.getFile());

        File aesEncryptedPayload = File.createTempFile("encrypted_aes", ".enc");
        File rsaEncryptedAESKey = File.createTempFile("encrypted_rsa", ".enc");

        try {
            // 2. Encrypt Payload with AES
            aesEncryptPayload(originalFile, aesEncryptedPayload, loadedSecretKey, iv);

            // 3. Encrypt AES Key with RSA
            KeyPair keyPair = RSAEncryptionService.generateKeyPair();
            rsaEncryptedAESKey(keyPath.toFile(), rsaEncryptedAESKey, keyPair.getPublic());

            File rsaDecryptedAESKey = File.createTempFile("decrypted_rsa", ".dec");
            File aesDecryptedPayload = File.createTempFile("decrypted_aes", ".dec");

            try {
                // 4. Decrypt AES Key with RSA
                rsaDecryptAESKey(rsaEncryptedAESKey, rsaDecryptedAESKey, keyPair.getPrivate());

                // 5. Load Decrypted AES Key
                SecretKey decryptedSecretKey = loadSecretKey(rsaDecryptedAESKey.toPath());

                // 6. Decrypt Payload with Decrypted AES Key
                aesDecryptPayload(aesEncryptedPayload, aesDecryptedPayload, decryptedSecretKey, iv);

                // 7. Verify Decryption
                assertTrue(aesDecryptedPayload.length() > 0);

                // 5. Verify content

                byte[] originalBytes = Files.readAllBytes(originalFile.toPath());

                byte[] decryptedBytes = Files.readAllBytes(aesDecryptedPayload.toPath());

                assertArrayEquals(originalBytes, decryptedBytes);


            } finally {
                if (rsaDecryptedAESKey.exists()) rsaDecryptedAESKey.delete();
                if (aesDecryptedPayload.exists()) aesDecryptedPayload.delete();
            }

        } finally {
            // Clean up
            if (aesEncryptedPayload.exists()) aesEncryptedPayload.delete();
            if (rsaEncryptedAESKey.exists()) rsaEncryptedAESKey.delete();
            if (keyPath.toFile().exists()) keyPath.toFile().delete();
        }
    }

    private void aesDecryptPayload(File encryptedFile, File decryptedFile, SecretKey key, IvParameterSpec iv) throws Exception {
        AESEncryptionService.decrypt(encryptedFile, decryptedFile, key, iv);
    }

    private void rsaDecryptAESKey(File encryptedFile, File decryptedFile, PrivateKey privateKey) throws Exception {
        RSAEncryptionService.decrypt(encryptedFile, decryptedFile, privateKey);
    }

    private void rsaEncryptedAESKey(File originalFile, File encryptedFile, PublicKey publicKey) throws Exception {
        RSAEncryptionService.encrypt(originalFile, encryptedFile, publicKey);
    }

    private void aesEncryptPayload(File originalFile, File encryptedFile, SecretKey key, IvParameterSpec iv) throws Exception {
        AESEncryptionService.encrypt(originalFile, encryptedFile, key, iv);
    }

    private SecretKey loadSecretKey(Path filePath) throws IOException {
        byte[] importedKeyBytes = Files.readAllBytes(filePath);
        return new SecretKeySpec(importedKeyBytes, "AES");
    }

    private static void saveSecretKey(SecretKey secretKey, Path filePath) throws IOException {
        byte[] keyBytes = secretKey.getEncoded();
        Files.write(filePath, keyBytes);
    }
}
