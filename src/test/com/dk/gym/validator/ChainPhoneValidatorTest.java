package test.com.dk.gym.validator;

import com.dk.gym.validator.ChainPhoneValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainPhoneValidatorTest {

    private ChainPhoneValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainPhoneValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"25456", false},
                {"123456789", false},
                {"32165498712", true},
                {"3216549871", false},
                {"0", false},
                {"32165498712,560", false},
                {"532165498712560", true},
                {"5321654987125609", false},
                {"6516458987989879871.4", false},
        };
    }
}