package test.com.dk.gym.validator;

import com.dk.gym.validator.chain.ChainPriceValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainPriceValidatorTest {

    private ChainPriceValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainPriceValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"25456", true},
                {"651645898798987987456", false},
                {"15656456.56", true},
                {"156545,56", true},
                {"156545,560", false},
                {"156545,56", true},
                {".56", false},
                {"6516458987989879871.4", false},
                {"651645898798987987.4", true},
                {"6516458987989879874", true},
                {"651645898798987987", true},
        };
    }
}