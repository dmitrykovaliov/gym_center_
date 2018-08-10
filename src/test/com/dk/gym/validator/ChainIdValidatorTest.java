package test.com.dk.gym.validator;

import com.dk.gym.validator.chain.ChainIdValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainIdValidatorTest {

    private ChainIdValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainIdValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name = "data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"0", false},
                {"", false},
                {null, false},
                {"-1", false},
                {"1", true},
                {"2", true},
                {"25.26", false},
                {"15654", true},
                {String.valueOf(Integer.MAX_VALUE), true},
                {String.valueOf(Integer.MAX_VALUE + 1), false},
        };
    }
}