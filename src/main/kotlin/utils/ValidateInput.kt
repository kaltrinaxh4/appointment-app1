package utils

import java.util.*

object ValidateInput
{

    @JvmStatic
    fun readValidPhone(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1 ,10))
                return input
            else {
                print("Invalid phone number $input.")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }



    @JvmStatic
    fun readValidReview(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1 ,5))
                return input
            else {
                print("Invalid review $input. Enter a review from 1 to 10")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }


    @JvmStatic
    fun readValidTime(prompt: String?): Double
    {
        var input = ScannerInput.readNextDouble(prompt)
        do {
            if (Utilities.validRangeDouble(input, 0.00 ,23.00))
                return input
            else {
                print("Invalid time $input. Time should be in the range 0.00 t0 23.00")
                input = ScannerInput.readNextDouble(prompt)
            }
        } while (true)
    }

    @JvmStatic
    fun readValidCategory(prompt: String?): String
    {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (CategoryUtility.isValidCategory(input))
                return input
            else {
                print("Invalid category $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }




    @JvmStatic
    fun readValidDate(prompt: String?): String
    {
        print(prompt)
        var input = Scanner(System.`in`).nextLine()
        do {
            if (DateUtility.isDateValid(input))
                return input
            else {
                print("Invalid date $input.  Please try again: ")
                input = Scanner(System.`in`).nextLine()
            }
        } while (true)
    }


}


