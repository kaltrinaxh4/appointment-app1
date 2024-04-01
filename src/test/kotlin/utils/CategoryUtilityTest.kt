package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.CategoryUtility.categories
import utils.CategoryUtility.isValidCategory

internal class CategoryUtilityTest {

    @Test
    fun categoriesReturnsFullCategoriesSet() {
        Assertions.assertEquals(5, categories.size)
        Assertions.assertTrue(categories.contains("Hairdye"))
        Assertions.assertTrue(categories.contains("Haircut"))
        Assertions.assertTrue(categories.contains("Blowdry"))
        Assertions.assertTrue(categories.contains("KeratinTreatment"))
        Assertions.assertTrue(categories.contains("HairPerm"))
        Assertions.assertFalse(categories.contains("Threading"))
    }

    @Test
    fun isValidCategoryTrueWhenCategoryExists() {
        Assertions.assertTrue(isValidCategory("hairdye"))
        Assertions.assertTrue(isValidCategory("haircut"))
        Assertions.assertTrue(isValidCategory("blowdry"))
        Assertions.assertTrue(isValidCategory("keratintreatment"))
        Assertions.assertTrue(isValidCategory("hairperm"))
        Assertions.assertFalse(isValidCategory("threading"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist() {
        Assertions.assertFalse(isValidCategory("Haird"))
        Assertions.assertFalse(isValidCategory("Hairc"))
        Assertions.assertFalse(isValidCategory("Blowd"))
        Assertions.assertFalse(isValidCategory("Keratin"))
        Assertions.assertFalse(isValidCategory("Hairp"))
        Assertions.assertFalse(isValidCategory("Hairperming"))
    }
}
