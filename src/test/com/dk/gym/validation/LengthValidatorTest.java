package test.com.dk.gym.validation;

import com.dk.gym.validation.atomic.AbstractValidator;
import com.dk.gym.validation.atomic.LengthValidator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LengthValidatorTest {

    @Test (dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected, AbstractValidator validator) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"", true, new LengthValidator(0, 5)},
                {"s", true, new LengthValidator(0, 5)},
                {"asdf5", true, new LengthValidator(0, 5)},
                {"^d55f*", false, new LengthValidator(0, 5)},
                {"asdfdfsdfasdfdfdfd", false, new LengthValidator(0, 5)},
                {"", false, new LengthValidator(1, 7)},
                {"s", true, new LengthValidator(1, 7)},
                {"asdf5z", true, new LengthValidator(1, 7)},
                {"^d55f*zz", false, new LengthValidator(1, 7)},
                {"asdfdfsdfasdfdfdfd", false, new LengthValidator(1, 7)},
        };
    }
}