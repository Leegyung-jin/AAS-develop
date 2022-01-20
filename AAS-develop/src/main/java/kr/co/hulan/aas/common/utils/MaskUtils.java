package kr.co.hulan.aas.common.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskUtils {

  private static final String MOBILE_PATTERN = "^(\\d{3})-?(\\d{3,4})-?(\\d{4})$";

  public static String maskPhoneNo(String phoneNo) {
    String replaceString = phoneNo;

    Matcher matcher = Pattern.compile(MOBILE_PATTERN).matcher(phoneNo);

    if(matcher.matches()) {
      replaceString = "";

      boolean isHyphen = false;
      if(phoneNo.indexOf("-") > -1) {
        isHyphen = true;
      }

      for(int i=1;i<=matcher.groupCount();i++) {
        String replaceTarget = matcher.group(i);
        if(i == 2) {
          char[] c = new char[replaceTarget.length()];
          Arrays.fill(c, '*');

          replaceString = replaceString + String.valueOf(c);
        } else {
          replaceString = replaceString + replaceTarget;
        }

        if(isHyphen && i < matcher.groupCount()) {
          replaceString = replaceString + "-";
        }
      }
    }
    else {
      StringBuffer str = new StringBuffer();
      char[] phoneChar = phoneNo.toCharArray();
      for( int idx = 0; idx < phoneChar.length; idx++ ){
        char c = phoneChar[idx];
        if( idx%2 == 1 ){
          str.append("*");
        }
        else {
          str.append(String.valueOf(c));
        }
      }
    }
    return replaceString;
  }
}
