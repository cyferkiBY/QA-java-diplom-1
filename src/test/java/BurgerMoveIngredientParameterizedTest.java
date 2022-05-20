import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerMoveIngredientParameterizedTest {

    private enum TypeOfTheTest{EXCEPTION, NOT_EXCEPTION};

    static private final Ingredient INGREDIENT_FIRST = new Ingredient(IngredientType.FILLING, "Filling ingredient", 5);
    static private final Ingredient INGREDIENT_SECOND = new Ingredient(IngredientType.SAUCE, "Sauce ingredient", 4);
    static private final Ingredient INGREDIENT_THIRD = new Ingredient(IngredientType.SAUCE, "New sauce ingredient", 6);
    private final int index;
    private final int newIndex;
    private final List<Ingredient> expectedIngredientsList;
    private final TypeOfTheTest typeOfTheTest;
    private Burger burger;

    public BurgerMoveIngredientParameterizedTest(int index, int newIndex, List<Ingredient> expectedIngredientsList, TypeOfTheTest typeOfTheTest) {
        this.index = index;
        this.newIndex = newIndex;
        this.expectedIngredientsList = expectedIngredientsList;
        this.typeOfTheTest = typeOfTheTest;
    }

    @Parameterized.Parameters(name = "{index}: \"index\": {0}; \"newIndex\": {1};")
    public static Object[][] getIndexData() {
        return new Object[][]{
                {0, 2, Arrays.asList(INGREDIENT_SECOND, INGREDIENT_THIRD, INGREDIENT_FIRST), TypeOfTheTest.NOT_EXCEPTION},
                {2, 0, Arrays.asList(INGREDIENT_THIRD, INGREDIENT_FIRST, INGREDIENT_SECOND), TypeOfTheTest.NOT_EXCEPTION},
                {0, 0, Arrays.asList(INGREDIENT_FIRST, INGREDIENT_SECOND, INGREDIENT_THIRD), TypeOfTheTest.NOT_EXCEPTION},
                {0, 4, Arrays.asList(INGREDIENT_FIRST, INGREDIENT_SECOND, INGREDIENT_THIRD), TypeOfTheTest.EXCEPTION},
                {4, 0, Arrays.asList(INGREDIENT_FIRST, INGREDIENT_SECOND, INGREDIENT_THIRD), TypeOfTheTest.EXCEPTION},
        };
    }

    @Before
    public void setUp() {
        burger = new Burger();
        burger.addIngredient(INGREDIENT_FIRST);
        burger.addIngredient(INGREDIENT_SECOND);
        burger.addIngredient(INGREDIENT_THIRD);
    }

    @Test
    public void checkMoveIngredientWithValidIndex() {
        Assume.assumeTrue(typeOfTheTest == TypeOfTheTest.NOT_EXCEPTION);
        burger.moveIngredient(index, newIndex);
        assertEquals("Список ингредиентов не совпал с ожидаемым", expectedIngredientsList, burger.ingredients);
    }

    @Test
    public void checkMoveIngredientWithNotValidIndex() {
        Assume.assumeTrue(typeOfTheTest == TypeOfTheTest.EXCEPTION);
        //предположила, что исключение это ожидаемое и корректное поведение метода при вводе некорректного индекса
        Assert.assertThrows("Не вызвано ожидаемое исключение IndexOutOfBoundsException", IndexOutOfBoundsException.class, () -> {burger.moveIngredient(index, newIndex);});
    }

}