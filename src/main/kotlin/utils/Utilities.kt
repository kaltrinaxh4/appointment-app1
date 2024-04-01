package utils

import models.Appointment
import models.Client

/**
 * Utilities provides utility methods for formatting strings and performing validations.
 */
object Utilities {

    /**
     * Formats a list of clients into a string.
     *
     * @param clientsToFormat The list of clients to format.
     * @return The formatted string.
     */
    @JvmStatic
    fun formatListString(clientsToFormat: List<Client>): String =
        clientsToFormat.joinToString(separator = "\n") { client -> "$client" }

    /**
     * Formats a set of appointments into a string.
     *
     * @param appointmentsToFormat The set of appointments to format.
     * @return The formatted string.
     */
    @JvmStatic
    fun formatSetString(appointmentsToFormat: Set<Appointment>): String =
        appointmentsToFormat.joinToString(separator = "\n") { appointment -> "\t$appointment" }

    /**
     * Checks if the provided index is within the valid range for the given list.
     *
     * @param index The index to check.
     * @param list The list to check against.
     * @return true if the index is valid, false otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Checks if the provided number is within the valid range.
     *
     * @param numberToCheck The number to check.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return true if the number is within the range, false otherwise.
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    /**
     * Checks if the provided double number is within the valid range.
     *
     * @param numberToCheck The double number to check.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return true if the double number is within the range, false otherwise.
     */
    @JvmStatic
    fun validRangeDouble(numberToCheck: Double, min: Double, max: Double): Boolean {
        return numberToCheck in min..max
    }
}
