package dev.beenary.adapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapElementException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import static dev.beenary.adapter.XmlAdapterUtils.*;

/**
 * Adapter for decrypting and encrypting String values
 */
@Slf4j
@Component
public class StringXmlAdapter extends XmlAdapter<String, String> {

    private static final SecretKeySpec secretKey = new
            SecretKeySpec(AES_KEY.getBytes(), AES);

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance(CIPHER_INSTANCE);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("ERROR: ", e);
        }
    }

    /**
     * Encrypts the value to be placed back in XML
     */
    @Override
    public String marshal(String plaintext) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(cipherText), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("ERROR: ", e);
            throw new SoapElementException("Encryption failed");
        }
    }

    /**
     * Decrypts the string value
     */
    @Override
    public String unmarshal(String encryptedText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = Base64.decodeBase64(encryptedText.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("ERROR: ", e);
            throw new SoapElementException("Decryption failed");
        }
    }

}
