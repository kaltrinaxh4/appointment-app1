package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
object DateofAppointmentUtility {
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
