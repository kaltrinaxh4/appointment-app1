package utils

/**
 * Utility object for working with categories.
 */
object CategoryUtility {

    /**
     * Set of predefined categories.
     * NOTE: JvmStatic annotation means that the categories variable is static.
     * (i.e., it can be referenced through the class name without creating an object of CategoryUtility.)
     */
    @JvmStatic
    val categories = setOf("Home", "College") // add more categories here.

    /**
     * Checks if the provided category is a valid category.
     *
     * @param categoryToCheck The category to be checked for validity.
     * @return `true` if the category is valid, `false` otherwise.
     */
    @JvmStatic
    fun isValidCategory(categoryToCheck: String?): Boolean {
        for (category in categories) {
            if (category.equals(categoryToCheck, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}