package com.dk.gym.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NotEmptyValidatorTest {

    private BaseValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new NotEmptyValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"", false},
                {null, false},
                {"-1", true},
                {"null", true},
                {"false", true},
                {"sdfffffffffffffffffffffsdfdfdfsdfRdfdfsfdfddddddddddddddddddddddddd", true},
        };
    }
}