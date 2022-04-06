package fr.marcwrobel.creditCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.marcwrobel.jbanking.creditCard.CreditCard;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link CreditCard} class.
 *
 * @author Robert Breuer, Andreas Ly
 */
public class CreditCardTest {

  private static final String VALID_VISA = "4012-8888-8888-1881";
  private static final String INVALID_REGEX = "1234-5243-2235-1233-3333-2222-2222";
  private static final String INVALID_CHECKSUM_VISA = "4916-6760-3562-0210";

  @Test
  void nullCreditCard() {
    assertThrows(IllegalArgumentException.class, () -> new CreditCard(null));
  }

  @Test
  void creditCardsWithSpecialChars() {
    assertThrows(IllegalArgumentException.class, () -> new CreditCard("asdfas.....123123"));
  }

  @Test
  void getTrimmedNumberFromAValidCreditCard() {
    CreditCard card = new CreditCard(VALID_VISA);
    String result = card.getCreditCardNumber();
    String expectedResult = VALID_VISA.replace("-", "").trim();
    assertEquals(
        expectedResult,
        result,
        "The trimmed number from " + VALID_VISA + "should be " + card.getCreditCardNumber());
  }

  @Test
  void validRegexValidator() {
    CreditCard card = new CreditCard(VALID_VISA);
    Boolean result = card.regexValidator();
    assertEquals(true, result, "The regex validator of this card should be true");
  }

  @Test
  void invalidRegexValidator() {
    CreditCard card = new CreditCard(INVALID_REGEX);
    Boolean result = card.regexValidator();
    assertEquals(false, result, "The regex validator of this card should be false");
  }

  @Test
  void validCheckSumValidator() {
    CreditCard card = new CreditCard(VALID_VISA);
    Boolean result = card.checkSumValidator();
    assertEquals(true, result, "The checkSum validator of this card should be true");
  }

  void invalidCheckSumValidator() {
    CreditCard card = new CreditCard(INVALID_CHECKSUM_VISA);
    Boolean result = card.checkSumValidator();
    assertEquals(false, result, "The checkSum validator of this card should be false");
  }

  @Test
  void isValid() {
    CreditCard card = new CreditCard(VALID_VISA);
    Boolean result = card.isValid();
    assertEquals(true, result, "The Card isValid should equals true");
  }

  void isNotValid() {
    CreditCard card = new CreditCard(INVALID_CHECKSUM_VISA);
    Boolean result = card.isValid();
    assertEquals(false, result, "The Card isValid should equals false");
  }

  @Test
  void testGetCompanyVisa() {
    CreditCard card = new CreditCard(VALID_VISA);
    String result = card.getCompany();
    assertEquals("VISA", result, "The Card's company should equals VISA");
  }
}
