package com.dk.gym.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TimeValidatorTest {

    private BaseValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new TimeValidator();
    }

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"08:10", true},
                {"00:12", true},
                {"11:05", true},
                {"8:1", false},
                {"8:25", true},
                {"8+25", false},
                {"08.25", false},
        };
    }
}