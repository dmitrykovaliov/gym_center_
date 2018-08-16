package test.com.dk.gym.validation;

import com.dk.gym.validation.chain.ChainPercentValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainPercentValidatorTest {

    private ChainPercentValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainPercentValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"-0", false},
                {"0", true},
                {"-1", false},
                {"12", true},
                {"-15", false},
                {"9", true},
                {"45", true},
                {"99", true},
                {"100", true},
        };
    }
}