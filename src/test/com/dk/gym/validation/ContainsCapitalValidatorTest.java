package test.com.dk.gym.validation;

import com.dk.gym.validation.atomic.AbstractValidator;
import com.dk.gym.validation.atomic.ContainsCapitalValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContainsCapitalValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ContainsCapitalValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"000d545239sd", false},
                {"sdkfldkfD4535", true},
                {"", false},
                {"-1", false},
                {"sdfffffffffffffffffffffsdfdfdfsdfRdfdfsfdfddddddddddddddddddddddddd", true},
                {"Asdfs5", true},
                {"asdfdfZ", true},
        };
    }
}