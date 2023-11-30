package models

data class Note (var appointmentID: String,
                 var appointmentPriority: Int,
                 var appointmentClient: String,
                 var isAppointmentCancelled: Boolean){
}