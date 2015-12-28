package com.bookislife.sauce.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * How to find out your certificateSha1?
 * <p/>
 * Run the following command and paste the SHA1 line without any semicolon:
 * <code>
 * keytool -list -v -keystore your_app.keystore
 * </code>
 * <p/>
 * Created by SidneyXu on 2015/12/28.
 */
public class CertificateHelper {

    private final String certificateSha1;

    public CertificateHelper(String certificateSha1) {
        this.certificateSha1 = certificateSha1;
    }

    public boolean validateAppSignature(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            for (Signature signature : signatures) {
                byte[] signatureBytes = signature.toByteArray();
                String sha1InHex = calcSHA1(signatureBytes);
                return certificateSha1.equalsIgnoreCase(sha1InHex);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private String calcSHA1(byte[] signatureBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.update(signatureBytes);
        byte[] signatureHash = digest.digest();
        return StringUtils.bytesToHex(signatureHash);
    }

}
