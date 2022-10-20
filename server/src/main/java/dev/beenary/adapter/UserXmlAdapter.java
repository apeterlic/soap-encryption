package dev.beenary.adapter;

import generated.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.ws.soap.SoapElementException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static dev.beenary.adapter.XmlAdapterUtils.*;

/**
 * Adapter for decrypting and encrypting User values
 * NOTE: currently not in use
 */
@Slf4j
@Service
public class UserXmlAdapter extends XmlAdapter<String, User> {

    //Secret Key values set from properties files
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
    public String marshal(User response) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(Objects.requireNonNull(SerializationUtils.serialize(response)));
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
    public User unmarshal(String encryptedText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = Base64.decodeBase64(encryptedText.getBytes(StandardCharsets.UTF_8));
            return (User) SerializationUtils.deserialize(cipher.doFinal(cipherText));
        } catch (Exception e) {
            log.error("ERROR: ", e);
            throw new SoapElementException("Decryption failed");
        }
    }
}