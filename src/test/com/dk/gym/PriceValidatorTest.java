package test.com.dk.gym;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.PriceValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PriceValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new PriceValidator();
    }

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"25456", true},
                {"651645898798987987456", true},
                {"15656456.56", true},
                {"156545,56", true},
                {"156545,560", false},
                {"156545,56", true},
                {".56", false},
        };
    }
}