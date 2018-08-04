package test.com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.ContainsDigitValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ContainsDigitValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ContainsDigitValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"000d545239sd", true},
                {"sdkfldkfD4535", true},
                {"", false},
                {"0", true},
                {"-1", true},
                {"sdfffffffffffffffffffffsdfdfdfsdfRdfdfsfdfddddddddddddddddddddddddd", false},
                {"Asdfs5", true},
                {"asdfdfZ", false},
                {"7asdfdfZ", true},
        };
    }
}