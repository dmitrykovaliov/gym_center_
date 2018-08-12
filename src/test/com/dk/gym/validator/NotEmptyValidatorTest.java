package test.com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.NotEmptyValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NotEmptyValidatorTest {

    private AbstractValidator validator;

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
                {" ", false},
                {null, false},
                {"-1", true},
                {"null", true},
                {"false", true},
                {"sdfffffffffffffffffffffsdfdfdfsdfRdfdfsfdfddddddddddddddddddddddddd", true},
        };
    }
}