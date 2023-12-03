import models.Client
import java.time.LocalDateTime

data class Appointment(
    val appointmentId: Int,
    val client: Client,
    val serviceType: String,
    val appointmentDateTime: LocalDateTime, // Updated to use LocalDateTime for date and time
    val appointmentDuration: String,
    val appointmentCostPaid: Double,
    val appointmentSatisfaction: Int,
    var isAppointmentCancelled: Boolean = false
)