package test.com.dk.gym.validation;

import com.dk.gym.validation.atomic.LineNotEmptyValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LineNotEmptyValidatorTest {

    private LineNotEmptyValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new LineNotEmptyValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidateZeroParameter(boolean expected) throws Exception {

        boolean fact = validator.validate();

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {false},
        };
    }

    @Test (dataProvider = "data1", groups = {"base"})
    public void testValidateOneParameter(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data1")
    public Object[][] data1ForValidate() {
        return new Object[][]{
                {"", false},
                {null, false},
                {"null", true},
                {"-1", true},
                {"diffferent", true},
        };
    }

    @Test (dataProvider = "data2", groups = {"base"})
    public void testValidateTwoParameter(String first, String second, boolean expected) throws Exception {

        boolean fact = validator.validate(first, second);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data2")
    public Object[][] data2ForValidate() {
        return new Object[][]{
                {"", "", false},
                {null, null, false},
                {"", null, false},
                {null, "", false},
                {"null", "", true},
                {"-1", "", true},
                {"diffferent", null, true},
        };
    }

    @Test (dataProvider = "data3", groups = {"base"})
    public void testValidateFiveParameter(String first, String second, String third
                                          , String forth, String fifth, boolean expected) throws Exception {

        boolean fact = validator.validate(first, second, third, forth, fifth);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data3")
    public Object[][] data3ForValidate() {
        return new Object[][]{
                {"", "", "", "", "", false},
                {null, null, null, null, null, false},
                {"", null, null, "", null, false},
                {null, "", null, null, "", false},
                {"null", "", "", "", "", true},
                {"", "", "", "", "-1", true},
                {"diffferent", null, "other", null, null, true},
        };
    }




}