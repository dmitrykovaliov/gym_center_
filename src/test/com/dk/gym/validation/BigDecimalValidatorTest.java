package test.com.dk.gym.validation;

import com.dk.gym.validation.atomic.AbstractValidator;
import com.dk.gym.validation.atomic.BigDecimalValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BigDecimalValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new BigDecimalValidator();
    }

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"1", true},
                {"0", true},
                {"-1.34", false},
                {"-165545461556", false},
                {"256.45", true},
                {"954565561545654561545", true},
                {"56545dfd", false}
        };
    }
}