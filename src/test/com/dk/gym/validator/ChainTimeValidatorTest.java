package test.com.dk.gym.validator;

import com.dk.gym.validator.chain.ChainTimeValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainTimeValidatorTest {

    private ChainTimeValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainTimeValidator();
    }

    @Test(dataProvider = "data", groups = {"chain"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"05:55", true},
                {"10:00", true},
                {"5:59", true},
                {"25:06", false},
                {"00:00", true},
                {"00:60", false},
                {"000:01", false},
                {"05:052", false},
                {"23:59", true},
        };
    }

}