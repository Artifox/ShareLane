import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShareLane {
    private static final String URL = "https://www.sharelane.com/";
    private static final String REGISTER_URL = "https://www.sharelane.com/cgi-bin/register.py";

    @Test
    public static void enterSite(){
        open(URL);
        $(By.xpath("/html/body/center/table/tbody/tr[20]/td/p/a/b")).click();
        //String result = $(By.className("footer")).getText();
        String result = $(By.linkText("Shopping Cart")).getText();
        Assert.assertEquals(result, "Shopping Cart");
    }

    @Test
    public static void zipCodeEmpty(){
        open(REGISTER_URL);
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]/input")).click();
        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public static void zipCodeFiveDigits(){
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]/input")).click();
        String result = $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[1]/td[1]/p")).getText();
        Assert.assertEquals(result, "First Name*");
    }

    @Test
    public static void zipCodeSixDigits(){
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("111111");
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]/input")).click();
        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public static void positiveSignIn(){
        signIn();
        String result = $(By.className("confirmation_message")).getText();
        Assert.assertEquals(result, "Account is created!");
    }

    @Test
    public static void emptySignIn(){
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]/input")).click();
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[6]/td[2]/input")).click();

        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. Some of your fields have invalid data or email was previously used");
    }

    @Test
    public static void positiveLogIn(){
        signIn();
        String email = $(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();
        String password = $(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[2]/td[2]")).getText();
        $(By.xpath("/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td/p/a")).click();
        $(By.name("email")).sendKeys(email);
        $(By.name("password")).sendKeys(password);
        $(By.xpath("/html/body/center/table/tbody/tr[3]/td/table/tbody/tr/td[3]/input")).click();

        String result = $(By.className("user")).getText().substring(0, 5);
        result.substring(0, 4);
        Assert.assertEquals(result, "Hello");
    }


    private static void signIn() {
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td[2]/input")).click();
        $(By.name("first_name")).sendKeys("John");
        $(By.name("last_name")).sendKeys("Doe");
        $(By.name("email")).sendKeys("jdoe@gmail.com");
        $(By.name(("password1"))).sendKeys("12345");
        $(By.name(("password2"))).sendKeys("12345");
        $(By.xpath("/html/body/center/table/tbody/tr[5]/td/table/tbody/tr[2]/td/table/tbody/tr[6]/td[2]/input")).click();
    }
}
