package fr.marcwrobel.jbanking.creditCard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for credit cards
 *
 * @author Robert Breuer, Andreas Ly
 */
public class CreditCard {

  public String ccNumber;

  public CreditCard(String ccNumber) {
    if (ccNumber == null) {
      throw new IllegalArgumentException("The credit card number cannot be null");
    }

    String trimmedNumber = ccNumber.trim();

    String strippedNumber = trimmedNumber.replaceAll("-", "");

    if (!strippedNumber.matches("\\d+")) {
      throw new IllegalArgumentException(
          "This card contains other chars than numbers, hyphens and spaces");
    }

    this.ccNumber = strippedNumber;
  }

  public String getCreditCardNumber() {
    return this.ccNumber;
  }

  public Matcher getMatcher() {
    String ccNumber = getCreditCardNumber();

    String regex =
        "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|"
            + "(?<mastercard>5[1-5][0-9]{14})|"
            + "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|"
            + "(?<amex>3[47][0-9]{13})|"
            + "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|"
            + "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

    Pattern pattern = Pattern.compile(regex);

    return pattern.matcher(ccNumber);
  }

  public boolean regexValidator() {
    return getMatcher().matches();
  }

  public boolean checkSumValidator() {
    String ccNumber = getCreditCardNumber();
    int sum = 0;
    boolean alternate = false;

    for (int i = ccNumber.length() - 1; i >= 0; i--) {
      int n = Integer.parseInt(ccNumber.substring(i, i + 1));
      if (alternate) {
        n *= 2;
        if (n > 9) {
          n = (n % 10) + 1;
        }
      }
      sum += n;
      alternate = !alternate;
    }
    return (sum % 10 == 0);
  }

  public boolean isValid() {
    return regexValidator() && checkSumValidator();
  }

  public String getCompany() {
    Matcher matcher = getMatcher();
    matcher.matches();
    if (isValid()) {
      if (!matcher.group("visa").isEmpty()) {
        return "VISA";
      } else if (!matcher.group("mastercard").isEmpty()) {
        return "Master Card";
      } else if (!matcher.group("discover").isEmpty()) {
        return "Discover";
      } else if (!matcher.group("amex").isEmpty()) {
        return "American Express";
      } else if (!matcher.group("diners").isEmpty()) {
        return "Diners Card";
      } else if (!matcher.group("jcb").isEmpty()) {
        return "JCB";
      }
    }
    return "This card does not belong to a known company";
  }
}
