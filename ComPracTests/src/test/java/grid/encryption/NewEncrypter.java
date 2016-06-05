package com.test.grid.encryption;

import sun.misc.BASE64Encoder;

import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by my computers on 5/24/2016.
 */
public class NewEncrypter {

    private PublicKey pubKey;
    SecureRandom prng;

    public NewEncrypter (String publicKeyString) throws EncrypterException {
        prng = new SecureRandom();
    }

    public String encrypt(String plainText) throws EncrypterException {
        String strDataToEncrypt = new String();
        String strCipherText = new String();

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            final int AES_KEYLENGTH = 128;    // change this as desired for the security level you want
            byte[] iv = new byte[AES_KEYLENGTH / 8];    // Save the IV bytes or send it in plaintext with the encrypted data so you can decrypt the data later
            prng.nextBytes(iv);
            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); // AES/CBC/NoPadding Must specify the mode explicitly as most JCE providers default to ECB mode!!
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            strDataToEncrypt = plainText; //"Hello World of Encryption using AES ";
            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
            // b64 is done differently on Android
            strCipherText = new BASE64Encoder().encode(byteCipherText);
//            System.out.println("Cipher Text generated using AES is " + strCipherText);
        } catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        } catch (NoSuchPaddingException noSuchPad) {
            System.out.println(" No Such Padding exists " + noSuchPad);
        } catch (InvalidKeyException invalidKey) {
            System.out.println(" Invalid Key " + invalidKey);
        } catch (BadPaddingException badPadding) {
            System.out.println(" Bad Padding " + badPadding);
        } catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println(" Illegal Block Size " + illegalBlockSize);
        } catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println(" Invalid Parameter " + invalidParam);
        }
        return strCipherText;
    }
}