package com.shangchain.straitchain.utils;

import com.shangchain.straitchain.exception.EnsFormatException;
import org.web3j.ens.EnsResolutionException;

import java.net.IDN;

public class DomainValidatorUtil {

    public static String validateAndEncode(String domainName) {
        // Convert domain to lowercase, replace all @ to .
        domainName = domainName.toLowerCase().replaceAll("@", ".");

        domainName = normalise(domainName);

        // Validate domain name
        if (!isValid(domainName)) {
            throw new EnsFormatException();
        }

        // Return encoded domain name
        return domainName;
    }

    public static String normalise(String ensName) {
        try {
            return IDN.toASCII(ensName, 2).toLowerCase();
        } catch (IllegalArgumentException var2) {
            throw new EnsResolutionException("Invalid ENS name provided: " + ensName);
        }
    }

    // Validate domain name
    private static boolean isValid(String domainName) {
        String regex = "^(?=.{1,255}$)[0-9a-z](?:(?:[0-9a-z]|-){0,61}[0-9a-z])?(?:\\.[0-9a-z](?:(?:[0-9a-z]|-){0,61}[0-9a-z])?)*(?:[0-9a-z]{2,})?$";
        return domainName.matches(regex);
    }

}
