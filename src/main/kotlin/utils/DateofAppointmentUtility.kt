package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
object DateofAppointmentUtility {
    @JvmStatic
    fun isDateCorrect(dateToCheck: String?): Boolean {
        val dateFormatter1 = DateTimeFormatter.ofPattern("_/__/____")
        val dateFormatter2 = DateTimeFormatter.ofPattern("__/__/____")
        val dateFormatter3 = DateTimeFormatter.ofPattern("__/_/____")
        val dateFormatter4 = DateTimeFormatter.ofPattern("_/_/____")

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
