package common.helper;

import org.apache.commons.codec.digest.DigestUtils;

public class TextHelper {

    public static String toSHA1(String text) {

        return DigestUtils.sha1Hex(text);
    }

}
