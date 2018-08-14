package test.com.dk.gym.validation;

import com.dk.gym.validation.AbstractValidator;
import com.dk.gym.validation.EmailValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EmailValidatorTest {

    private AbstractValidator validator;

    @BeforeMethod
    public void setUp() throws Exception {

        validator = new EmailValidator();
    }

    @Test(dataProvider = "data", groups = {"base"})
    public void testValidate(String first, boolean expected) throws Exception {

        boolean fact = validator.validate(first);

        Assert.assertEquals(fact, expected);
    }

    @DataProvider(name="data")
    public Object[][] dataForValidate() {
        return new Object[][]{
                {"@mail.ru", false},
                {"a@mail.com", true},
                {"asdfsdfsdfsdfdfsdfsdf@gmail.com", true},
                {"asd@gmaaaaaaaaaaaaaaaaaaail.com", true},
                {"and@gon.developer", false},
                {"sdfd$mail.zet", false},
        };
    }

}