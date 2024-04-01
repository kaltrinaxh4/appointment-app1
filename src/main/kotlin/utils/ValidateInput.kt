package utils

import java.util.*

/**
 * ValidateInput provides utility methods for validating user input.
 */
object ValidateInput {

    /**
     * Reads and validates a phone number from the user input.
     *
     * @param prompt The prompt message for the user.
     * @return The validated phone number.
     */
    @JvmStatic
    fun readValidPhone(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 10))
                return input
            else {
                print("Invalid phone number $input.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    /**
     * Reads and validates a review score from the user input.
     *
     * @param prompt The prompt message for the user.
     * @return The validated review score.
     */
    @JvmStatic
    fun readValidReview(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 10))
                return input
            else {
                print("Invalid review $input. Enter a review from 1 to 10")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }

    /**
     * Reads and validates a time value from the user input.
     *
     * @param prompt The prompt message for the user.
     * @return The validated time value.
     */
    @JvmStatic
    fun readValidTime(prompt: String?): Double {
        var input = ScannerInput.readNextDouble(prompt)
        do {
            if (Utilities.validRangeDouble(input, 0.00, 23.00))
                return input
            else {
                print("Invalid time $input. Time should be in the range 0.00 to 23.00")
                input = ScannerInput.readNextDouble(prompt)
            }
        } while (true)
    }

    /**
     * Reads and validates a category from the user input.
     *
     * @param prompt The prompt message for the user.
     * @return The validated category.
     */
    @JvmStatic
    fun readValidCategory(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input.  Please try again by checking categories in CategoryUtility: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }

    /**
     * Reads and validates a date from the user input.
     *
     * @param prompt The prompt message for the user.
     * @return The validated date.
     */
    @JvmStatic
    fun readValidDateofAppointment(prompt: String?): String {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (DateofAppointmentUtility.isDateCorrect(input))
                return input
            else {
                print("Invalid date $input.  Please try again by entering like this __/__/____: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }
}

