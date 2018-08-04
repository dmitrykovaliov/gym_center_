package test.com.dk.gym.validator;

import com.dk.gym.validator.ChainDateValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChainDateValidatorTest {

    private ChainDateValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new ChainDateValidator();
    }

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"2018-08-06", true},
                {"208-08-06", false},
                {"2018-0-06", false},
                {"2018-08-6", false},
                {"2018.07.30", true},
                {"201.07.32", false},
                {"2018.0.30", false},
                {"2018.07.3", false},
                {"2018/05/06", true},
                {"218/05/06", false},
                {"2018/0/06", false},
                {"2018/05/6", false},
                {"2018/02/29", false},
                {"2018/02/28", true},
                {"18/04/12", false},
                {"2026/04/12", false},
                {"1949/07/19", false},
                {"", false},
                {" ", false},
                {"1", false},
                {"-1", false},
                {"dddd-dd-dd", false},
        };
    }

}