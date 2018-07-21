package com.dk.gym.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RangeValidatorTest {

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected, BaseValidator validator) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"0", true, new RangeValidator(0, 5)},
                {"1", true, new RangeValidator(0, 5)},
                {"2", true, new RangeValidator(0, 5)},
                {"-1", false, new RangeValidator(0, 5)},
                {"5", true, new RangeValidator(0, 5)},
                {"6", false, new RangeValidator(0, 5)},
                {"0", false, new RangeValidator(1, 7)},
                {"1", true, new RangeValidator(1, 7)},
                {"2", true, new RangeValidator(1, 7)},
                {"-1", false, new RangeValidator(1, 7)},
                {"7", true, new RangeValidator(1, 7)},
                {"8", false, new RangeValidator(1, 7)},
        };
    }

}