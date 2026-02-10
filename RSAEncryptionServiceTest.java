package uk.co.sangharsh.service.calendar;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RSAEncryptionServiceTest {

    @Test
    void testFileEncryptionDecryption() throws Exception {
        // 1. Generate Key Pair
        KeyPair keyPair = RSAEncryptionService.generateKeyPair();
        assertNotNull(keyPair);

        // 2. Load the payload.xml file from resources
        URL resource = getClass().getClassLoader().getResource("payload.xml");
        assertNotNull(resource, "payload.xml not found in resources");
        File originalFile = new File(resource.getFile());

        File encryptedFile = File.createTempFile("encrypted", ".enc");
        File decryptedFile = File.createTempFile("decrypted", ".xml");

        try {
            // 3. Encrypt the file
            RSAEncryptionService.encrypt(originalFile, encryptedFile, keyPair.getPublic());

            // 4. Decrypt the file
            RSAEncryptionService.decrypt(encryptedFile, decryptedFile, keyPair.getPrivate());

            // 5. Verify content
            byte[] originalBytes = new byte[(int) originalFile.length()];
            try (FileInputStream fis = new FileInputStream(originalFile)) {
                fis.read(originalBytes);
            }

            byte[] decryptedBytes = new byte[(int) decryptedFile.length()];
            try (FileInputStream fis = new FileInputStream(decryptedFile)) {
                fis.read(decryptedBytes);
            }

            assertArrayEquals(originalBytes, decryptedBytes);

        } finally {
            // Clean up
            if (encryptedFile.exists()) encryptedFile.delete();
            if (decryptedFile.exists()) decryptedFile.delete();
        }
    }
}
