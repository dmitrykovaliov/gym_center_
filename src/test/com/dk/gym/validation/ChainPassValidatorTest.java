package test.com.dk.gym.validation;

import com.dk.gym.validation.chain.ChainPassValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainPassValidatorTest {

    private ChainPassValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainPassValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name = "data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"sdf651", false},
                {"1234567", false},
                {"12345678", false},
                {"A1234567", true},
                {"0", false},
                {"-1", false},
                {"321654sdfaA98712,560", true},
                {"321654sdfaA98712,560*", false},
                {"5321A654987125609", true},
                {"6516458987989879871.4", false},
        };
    }
}