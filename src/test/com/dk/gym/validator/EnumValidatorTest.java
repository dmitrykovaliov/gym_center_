package test.com.dk.gym.validator;

import com.dk.gym.entity.Role;
import com.dk.gym.validator.EnumValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EnumValidatorTest {
    private EnumValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new EnumValidator();
    }

    @Test(dataProvider = "data")
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(Role.class, first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name = "data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"sdf651", false},
                {"AdmIn ", true},
                {"client", true},
                {" traIner", true},
                {"", false},
                {null, false},
        };
    }
}