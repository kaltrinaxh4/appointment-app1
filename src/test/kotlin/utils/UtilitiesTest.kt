package utils


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.Utilities.validRange

/**
 * Test class for Utilities.
 */
class UtilitiesTest {

    /**
     * Test cases for validRange function with positive test data.
     */
    @Test
    fun validRangeWorksWithPositiveTestData() {
        Assertions.assertTrue(validRange(1, 1, 1))
        Assertions.assertTrue(validRange(1, 1, 2))
        Assertions.assertTrue(validRange(1, 0, 1))
        Assertions.assertTrue(validRange(1, 0, 2))
        Assertions.assertTrue(validRange(-1, -2, -1))
    }

    /**
     * Test cases for validRange function with negative test data.
     */
    @Test
    fun validRangeWorksWithNegativeTestData() {
        Assertions.assertFalse(validRange(1, 0, 0))
        Assertions.assertFalse(validRange(1, 1, 0))
        Assertions.assertFalse(validRange(1, 2, 1))
        Assertions.assertFalse(validRange(-1, -1, -2))
    }
}
