package utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DateofAppointmentUtilityTest {

    @Test
    fun dayMonthYearNoDashes() {
        assertTrue(DateofAppointmentUtility.isDateCorrect("5/5/2023"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("5-5-2023"))
        assertTrue(DateofAppointmentUtility.isDateCorrect("15/5/2023"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("15-5-2023"))
        assertTrue(DateofAppointmentUtility.isDateCorrect("31/12/2023"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("31-12-2023"))
    }


    @Test
    fun monthDayYear() {
        assertFalse(DateofAppointmentUtility.isDateCorrect("2/18/2023"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2-18-2023"))
    }

    @Test
    fun yearMonthDay() {
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023/5/5"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023-5-5"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023/5/15"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023-5-15"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023/12/31"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023-12-31"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023/2/18"))
        assertFalse(DateofAppointmentUtility.isDateCorrect("2023-2-18"))
    }
}
