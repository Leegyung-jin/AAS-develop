package kr.co.hulan.aas.common.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneNoUtils {

  public static boolean isValidPhoneNo(String phoneNo){
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    try {
      PhoneNumber phoneNumber = phoneUtil.parse(phoneNo, "KR");
      return phoneUtil.isValidNumberForRegion(phoneNumber, "KR");
    } catch (NumberParseException e) {
      return false;
    }
  }
}
