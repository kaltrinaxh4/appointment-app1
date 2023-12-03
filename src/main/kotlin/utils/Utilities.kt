package utils

/**
 * Utility object providing common functions.
 */
object Utilities {

    /**
     * Checks if a given number is within a valid range (inclusive).
     *
     * @param numberToCheck The number to be checked.
     * @param min The minimum value of the valid range.
     * @param max The maximum value of the valid range.
     * @return `true` if the number is within the valid range, `false` otherwise.
     */
    @JvmStatic
    fun validRange(numberToCheck: Int, min: Int, max: Int): Boolean {
        return numberToCheck in min..max
    }

    /**
     * Checks if the provided index is a valid index for the given list.
     *
     * @param index The index to be checked.
     * @param list The list to check the index against.
     * @return `true` if the index is valid for the list, `false` otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}