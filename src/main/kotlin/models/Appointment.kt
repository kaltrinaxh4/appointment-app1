package models
data class Appointment(var appointmentId: Int = 0,
                       var date: String,
                       var time: Double,
                       var treatment: String,
                       var cost: Int,
                       var rating: Int,
                       var isScheduled: Boolean)
{