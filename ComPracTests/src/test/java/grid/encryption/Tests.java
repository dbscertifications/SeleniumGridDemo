package com.test.grid.encryption;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by my computers on 5/24/2016.
 */
public class Tests {

//    private static String _baseURI = System.getProperty("baseuri");
//    private static String _version = System.getProperty("apiVersion");
//    private static String _apiAccount = System.getProperty("apiAccount");

//    private String username;
//    String baseDir = "/your/project/basedir/";
//    File file = new File(System.getProperty("apitestdatafile"));
//    ReadWriteJSON rwJSON = new ReadWriteJSON(file.getAbsolutePath());

    private String ACCESS_TOKEN;
    private String ENCRYPTION_KEY;
    private String RESULT_CODE;
    private String ENCRYPTION_STRING;
    private String CARDNUMBER = "";
    private String CVCNUMBER = "";
    private String EXPIRYMONTH = "";
    private String EXPIRYYEAR = "";
    private String CARDHOLDERNAME = "";

    private String pubKey = "10001|D438E7F62AFAD3EF662AC4C04AD11E099B72539919ECF776786AC46A1496B3D11E16C768552BC18D46520A"
            + "DA271A5E500D7BFC573B59F81FC29D06346596FD037795D83F88F48CC9AC6C3EB67CF2893C5D8BDE74A14A9B7B"
            + "25ECF07EE00E59F05351B52530016E2201C2E07DD8BC3FD557C65B6E1E7AB99A817C447693D915A1EA63F1A36A"
            + "618AEDB4D3305BDD0B10883A6D88B93C02C31A72647DDD484F966AFF5653E626CD56CD8D9C7D7E2D9400293287"
            + "0BE9987ECD8A6AB3231448120BE88C9F98B1837FBA9B318FC7FF07E7831518B0DB6053B6DE5FB071FC30AE06A3"
            + "22C22D39F567EDA33149C11696A2FAFDCCEF197F979426D6E077568EB57F3EDED5";

    @Test(priority = 1)    // POST /payment/deposit/card - Creates a new customer based on the required parameters.
    public void PaymentAPI_POSTUKCustomer_CardDeposit() {
//        try {
//            ACCESS_TOKEN = "";//rwJSON.getContents("UK-ACCESS_TOKEN");
//        } catch (IOException e) {
//        }
        // method call here with credit card parameters and receive encrypted CC details in return
//        Map<String, String> parameters1 = new HashMap<String, String>();
//        parameters1.put("access_token", ACCESS_TOKEN);
//        Response receivedResponse1 = RedBookAPI().Payment_API(_baseURI, _version, _apiAccount).SendRESTRequestPOST(parameters1, "/payment/encryption_key");
//        log("Response: " + receivedResponse1.asString());
//        ENCRYPTION_KEY = getKeyValueFromResponseJSON("encryption_key",receivedResponse1);
        CARDNUMBER = "4444333322221111";
        CVCNUMBER = "737";
        EXPIRYMONTH = "06";
        EXPIRYYEAR = "2018";
        CARDHOLDERNAME = "GARY MILBURN";
        ENCRYPTION_STRING = testEncrypt(CARDNUMBER, CVCNUMBER, EXPIRYMONTH, EXPIRYYEAR, CARDHOLDERNAME);
        System.out.println("Encryption String: " + ENCRYPTION_STRING);
        ////////////////////////////////////////
        // Provide all Parameters below for REST POST
//        Map<String, String> parameters = new HashMap<String, String>();
//        parameters.put("access_token", ACCESS_TOKEN);
//        parameters.put("card_encrypted", ENCRYPTION_STRING);
//        parameters.put("payment_method", "visa");
//        parameters.put("amount", "200.00");
//        parameters.put("deposit_reference", "1234567890");
//        parameters.put("force_return", "0");
//        Response receivedResponse = RedBookAPI().Payment_API(_baseURI, _version, _apiAccount).SendRESTRequestPOST(parameters, "/payment/deposit/card");
//        log("Response: " + receivedResponse.asString());
//        RESULT_CODE = getKeyValueFromResponseJSON("resultCode",receivedResponse);
//        Verify.verifyText(RESULT_CODE, "Authorised", "Result Code did not match");
    }

    @Test(priority = 2)    // POST /payment/deposit/card - Creates a new customer based on the required parameters.
    public void PaymentAPI_POSTIECustomer_CardDeposit() {
////        try {
//            ACCESS_TOKEN = "";  //rwJSON.getContents("IE-ACCESS_TOKEN");
//        } catch (IOException e) {
//        }
        // method call here with credit card parameters and receive encrypted CC details in return
//        Map<String, String> parameters1 = new HashMap<String, String>();
//        parameters1.put("access_token", ACCESS_TOKEN);
//        Response receivedResponse1 = RedBookAPI().Payment_API(_baseURI, _version, _apiAccount).SendRESTRequestPOST(parameters1, "/payment/encryption_key");
//        log("Response: " + receivedResponse1.asString());
//        ENCRYPTION_KEY = getKeyValueFromResponseJSON("encryption_key",receivedResponse1);
        CARDNUMBER = "5555444433331111";
        CVCNUMBER = "737";
        EXPIRYMONTH = "08";
        EXPIRYYEAR = "2017";
        CARDHOLDERNAME = "HARRY SIMPSON";
        ENCRYPTION_STRING = testEncrypt(CARDNUMBER, CVCNUMBER, EXPIRYMONTH, EXPIRYYEAR, CARDHOLDERNAME);
        System.out.println("Encryption String: " + ENCRYPTION_STRING);
        ////////////////////////////////////////
        // Provide all Parameters below for REST POST
//        Map<String, String> parameters = new HashMap<String, String>();
//        parameters.put("access_token", ACCESS_TOKEN);
//        parameters.put("card_encrypted", ENCRYPTION_STRING);
//        parameters.put("payment_method", "mc");
//        parameters.put("amount", "300.00");
//        parameters.put("deposit_reference", "0987654321");
//        parameters.put("force_return", "0");
//        Response receivedResponse = RedBookAPI().Payment_API(_baseURI, _version, _apiAccount).SendRESTRequestPOST(parameters, "/payment/deposit/card");
//        log("Response: " + receivedResponse.asString());
//        RESULT_CODE = getKeyValueFromResponseJSON("resultCode",receivedResponse);
//        Verify.verifyText(RESULT_CODE, "Authorised", "Result Code did not match");
    }

    public String testEncrypt(String cardnumber, String cvcnumber, String expirymonth, String expiryyear, String cardholdername) {
//        NewEncrypter enc;
        Encrypter enc;
        String ENCRYPTEDKEYVALUE = "";
        Card card = new Card.Builder(new Date())
                .number(cardnumber)
                .cvc(cvcnumber)
                .expiryMonth(expirymonth)
                .expiryYear(expiryyear)
                .holderName(cardholdername)
                .build();
        try {
//            enc = new NewEncrypter(pubKey);
            enc = new Encrypter(pubKey);
            ENCRYPTEDKEYVALUE = enc.encrypt(card.toString());
//            System.out.println("encrypted key value in testencrypt method: "+ENCRYPTEDKEYVALUE);
        } catch (EncrypterException s) {
            System.err.print(s);
        }
        return ENCRYPTEDKEYVALUE;
    }

}
