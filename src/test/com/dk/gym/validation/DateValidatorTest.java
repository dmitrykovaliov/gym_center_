package test.com.dk.gym.validation;

import com.dk.gym.validation.atomic.AbstractValidator;
import com.dk.gym.validation.atomic.DateValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DateValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new DateValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"2018-08-06", true},
                {"208-08-06", false},
                {"2018-0-06", false},
                {"2018-08-6", false},
                {"2018.07.30", true},
                {"201.07.30", false},
                {"2018.0.30", false},
                {"2018.07.3", false},
                {"2018/05/06", true},
                {"218/05/06", false},
                {"2018/0/06", false},
                {"2018/05/6", false},
                {"18/04/12", false},
                {"", false},
                {" ", false},
                {"1", false},
                {"-1", false},
                {"dddd-dd-dd", false},
        };
    }
}