package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * DateofAppointmentUtility provides utility methods related to date validation for appointments.
 */
object DateofAppointmentUtility {

    /**
     * Checks if the provided date string is in a correct format.
     *
     * @param dateToCheck The date string to check.
     * @return true if the date string is correct, false otherwise.
     */
    @JvmStatic
    fun isDateCorrect(dateToCheck: String?): Boolean {
        val dateFormatter1 = DateTimeFormatter.ofPattern("d/MM/yyyy")
        val dateFormatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateFormatter3 = DateTimeFormatter.ofPattern("dd/M/yyyy")
        val dateFormatter4 = DateTimeFormatter.ofPattern("d/M/yyyy")

        val formatters = listOf(dateFormatter1, dateFormatter2, dateFormatter3, dateFormatter4)

        for (formatter in formatters) {
            try {
                LocalDate.parse(dateToCheck, formatter)
                return true
            } catch (e: DateTimeParseException) {

            }
        }

        return false
    }
}
