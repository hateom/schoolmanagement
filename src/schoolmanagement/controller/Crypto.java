/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.security.*;

/**
 *
 * @author deely
 */
public class Crypto {
    static String MD5Sum( String buffer ) {
        byte [] hash;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update( buffer.getBytes() );
            hash = md.digest();
       } catch( NoSuchAlgorithmException e ) {
           return "";
       }
         
       return new String( hash );
    }
}
