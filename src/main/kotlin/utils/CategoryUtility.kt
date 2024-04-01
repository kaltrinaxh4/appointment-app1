package utils

/**
 * CategoryUtility provides utility methods related to appointment categories.
 */
object CategoryUtility {

    /**
     * Set of valid appointment categories.
     */
    @JvmStatic
    val categories = setOf("Hairdye", "Haircut", "Blowdry", "KeratinTreatment", "HairPerm")

    /**
     * Checks if the provided category is valid.
     *
     * @param categoryToCheck The category to check.
     * @return true if the category is valid, false otherwise.
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
