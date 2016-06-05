package com.test.grid.encryption;

import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

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
public class Encrypter {

    private static final String PREFIX="adyenan";
    private static final String VERSION="0_1_1";
    private static final String SEPARATOR="$";

    private PublicKey pubKey;
    private Cipher aesCipher;
    private Cipher rsaCipher;
    private SecureRandom srandom;

    public Encrypter (String publicKeyString) throws EncrypterException {
        srandom = new SecureRandom();
        String[] keyComponents = publicKeyString.split("\\|");

        // The bytes can be converted back to a public key object
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
                new BigInteger(keyComponents[1].toLowerCase(), 16),
                new BigInteger(keyComponents[0].toLowerCase(), 16));

        try {
            pubKey = keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new EncrypterException("Problem reading public key: " + publicKeyString, e);
        }

        try {
            aesCipher  = Cipher.getInstance("AES");///CCM/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            throw new EncrypterException("Problem instantiation AES Cipher Algorithm", e);
        } catch (NoSuchPaddingException e) {
            throw new EncrypterException("Problem instantiation AES Cipher Padding", e);
        }

        try {
            rsaCipher = Cipher.getInstance("RSA");///None/PKCS1Padding");
            rsaCipher.init(Cipher.ENCRYPT_MODE, pubKey);

        } catch (NoSuchAlgorithmException e) {
            throw new EncrypterException("Problem instantiation RSA Cipher Algorithm", e);
        } catch (NoSuchPaddingException e) {
            throw new EncrypterException("Problem instantiation RSA Cipher Padding", e);
        } catch (InvalidKeyException e) {
            throw new EncrypterException("Invalid public key: " + publicKeyString, e);
        }

    }

    public String encrypt(String plainText) throws EncrypterException {
        String strDataToEncrypt = new String();
        String strCipherText = new String();
        String strDecryptedText = new String();
        SecretKey aesKey = generateAESKey(128);
        //System.out.println("aesKey: "+aesKey.toString());
//        byte[] iv = generateIV(12);
        byte[] encrypted;
        final int AES_KEYLENGTH = 128;    // change this as desired for the security level you want
        byte[] iv = new byte[AES_KEYLENGTH / 8];    // Save the IV bytes or send it in plaintext with the encrypted data so you can decrypt the data later
        srandom.nextBytes(iv);
        try {
            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
            strDataToEncrypt = plainText; //"Hello World of Encryption using AES ";
            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            encrypted = aesCipherForEncryption.doFinal(byteDataToEncrypt);
            // b64 is done differently on Android
            strCipherText = new BASE64Encoder().encode(encrypted);
//            encrypted = aesCipher.doFinal(plainText.getBytes());
            System.out.println("strCipherText: "+strCipherText);

            // Decryption
            Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!

            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
            byte[] byteDecryptedText = aesCipherForDecryption.doFinal(encrypted);
            strDecryptedText = new String(byteDecryptedText);
            System.out.println(" Decrypted Text message is " + strDecryptedText);
            //
        } catch (NoSuchAlgorithmException e) {
            throw new EncrypterException("No Such Algorithm", e);
        } catch (NoSuchPaddingException e) {
            throw new EncrypterException("No Such Padding", e);
        } catch (IllegalBlockSizeException e) {
            throw new EncrypterException("Incorrect AES Block Size", e);
        } catch (BadPaddingException e) {
            throw new EncrypterException("Incorrect AES Padding", e);
        } catch (InvalidKeyException e) {
            throw new EncrypterException("Invalid AES Key", e);
        } catch(InvalidAlgorithmParameterException e) {
            throw new EncrypterException("Invalid AES Parameters", e);
        }
        byte[] result = new byte[iv.length + encrypted.length];
        // copy IV to result
        System.arraycopy(iv, 0, result, 0, iv.length);
        // copy encrypted to result
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        byte[] encryptedAESKey;
        try {
            encryptedAESKey = rsaCipher.doFinal(aesKey.getEncoded());
            //return PREFIX + VERSION + SEPARATOR + Base64.encodeToString(encryptedAESKey, Base64.NO_WRAP) + SEPARATOR + Base64.encodeToString(result, Base64.NO_WRAP);
            return PREFIX + VERSION + SEPARATOR + new BASE64Encoder().encode(encryptedAESKey) + SEPARATOR + new BASE64Encoder().encode(result);



        } catch (IllegalBlockSizeException e) {
            throw new EncrypterException("Incorrect RSA Block Size", e);
        } catch (BadPaddingException e) {
            throw new EncrypterException("Incorrect RSA Padding", e);
        }
    }


    private SecretKey generateAESKey(int keySize) throws EncrypterException {
        KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new EncrypterException("Unable to get AES algorithm", e);
        }
        kgen.init(keySize);
        return kgen.generateKey();
    }

    /* *
     * Generate a random Initialization Vector (IV)
     *
     * @param pubKey the SecretKey
     * @return the IV bytes
     */
    private synchronized byte[] generateIV(int ivSize) {

        byte[] iv = new byte[ivSize];//generate random IV AES is always 16bytes, but in CCM mode this represents the NONCE
        srandom.nextBytes(iv);
        return iv;
    }

}
