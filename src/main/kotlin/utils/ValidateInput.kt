package utils

import utils.ScannerInput.readNextInt
import java.util.*

/**
 * Utility object for validating user input related to categories and priorities.
 */
object ValidateInput {

    /**
     * Reads and validates a category input from the user.
     *
     * @param prompt The prompt to display before reading input.
     * @return A valid category input.
     */
    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    /**
     * Reads and validates a priority input from the user.
     *
     * @param prompt The prompt to display before reading input.
     * @return A valid priority input within the range of 1 to 5.
     */
    @JvmStatic
    fun readValidPriority(prompt: String?): Int {
        var input =  readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1 ,5))
                return input
            else {
                print("Invalid priority $input.")
                input = readNextInt(prompt)
            }
        } while (true)
    }
}