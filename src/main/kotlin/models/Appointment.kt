package models

/**
 * Represents an appointment.
 *
 * @property appointmentId The unique identifier for the appointment.
 * @property date The date of the appointment.
 * @property time The time of the appointment.
 * @property treatment The treatment or service provided during the appointment.
 * @property price The price of the appointment.
 * @property review The review score for the appointment.
 * @property isScheduled Indicates whether the appointment is scheduled or not.
 */
data class Appointment(
    var appointmentId: Int = 0,
    var date: String,
    var time: Double,
    var treatment: String,
    var price: Int,
    var review: Int,
    var isScheduled: Boolean
) {
    /**
     * Converts the appointment object to a string representation.
     *
     * @return A string representation of the appointment.
     */
    override fun toString() =
        if (isScheduled)
            "$appointmentId: $date $time  $treatment $price $review (Scheduled)"
        else
            "$appointmentId: $time $treatment $price $review (Not Scheduled)"
}
