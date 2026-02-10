package uk.co.sangharsh.service.calendar;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AESEncryptionServiceTest {

    @Test
    void testFileEncryptionDecryption() throws Exception {
        // 1. Generate Key and IV
        SecretKey key = AESEncryptionService.generateKey();
        IvParameterSpec iv = AESEncryptionService.generateIv();
        assertNotNull(key);
        assertNotNull(iv);

        // 2. Load the payload.xml file from resources
        URL resource = getClass().getClassLoader().getResource("payload.xml");
        assertNotNull(resource, "payload.xml not found in resources");
        File originalFile = new File(resource.getFile());

        File encryptedFile = File.createTempFile("encrypted_aes", ".enc");
        File decryptedFile = File.createTempFile("decrypted_aes", ".xml");

        try {
            // 3. Encrypt the file
            AESEncryptionService.encrypt(originalFile, encryptedFile, key, iv);

            // 4. Decrypt the file
            AESEncryptionService.decrypt(encryptedFile, decryptedFile, key, iv);

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
