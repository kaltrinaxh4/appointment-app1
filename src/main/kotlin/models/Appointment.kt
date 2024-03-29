package models
data class Appointment(var appointmentId: Int = 0,
                       var date: String,
                       var time: Double,
                       var treatment: String,
                       var price: Int,
                       var review: Int,
                       var isScheduled: Boolean)
{
    override fun toString() =
        if (isScheduled)
            "$appointmentId: $date $time  $treatment $price $review(Scheduled)"
        else
            "$appointmentId: $time $treatment $price $review (Not Available)"


}
