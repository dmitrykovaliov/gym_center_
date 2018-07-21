package com.dk.gym.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OnlyDigitsValidatorTest {

    private BaseValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new OnlyDigitsValidator();
    }

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"345234d", false},
                {"a345435", false},
                {"-1", false},
                {"0", true},
                {"6516545654651565456551565456", true},
                {"asdfdsf", false},
                {"/-*+-*/*-+", false},
        };
    }

}