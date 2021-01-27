import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShareLane {
    private static final String URL = "https://www.sharelane.com/";
    private static final String REGISTER_URL = "https://www.sharelane.com/cgi-bin/register.py";

    @Test
    public static void enterSite() {
        open(URL);
        $(By.xpath("//*[text()='ENTER']")).click();
        //String result = $(By.className("footer")).getText();
        String result = $(By.linkText("Shopping Cart")).getText();
        Assert.assertEquals(result, "Shopping Cart");
    }

    @Test
    public static void zipCodeEmpty() {
        open(REGISTER_URL);
        $(By.cssSelector("input[value= 'Continue']")).click();
        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public static void zipCodeFiveDigits() {
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.cssSelector("input[value= 'Continue']")).click();
        String result = $(By.cssSelector("input[value= 'Register']")).getAttribute("value");//QUESTION
        Assert.assertEquals(result, "Register");
    }

    @Test
    public static void zipCodeSixDigits() {
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("111111");
        $(By.cssSelector("input[value= 'Continue']")).click();
        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public static void positiveSignIn() {
        signIn();
        String result = $(By.className("confirmation_message")).getText();
        Assert.assertEquals(result, "Account is created!");
    }

    @Test
    public static void emptySignIn() {
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.cssSelector("input[value= 'Continue']")).click();
        $(By.cssSelector("input[value= 'Register']")).click();
        String result = $(By.className("error_message")).getText();
        Assert.assertEquals(result, "Oops, error on page. Some of your fields have invalid data or email was previously used");
    }

    @Test
    public static void positiveLogIn() {
        signIn();
        WebElement table = $(By.cssSelector("table[border= '1']"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        int rows_count = rows.size();
        List<WebElement> columns = new ArrayList<WebElement>();
        for (int i = 0; i < rows_count; i++) {
            columns.addAll(rows.get(i).findElements(By.tagName("td")));
        }
        String email = columns.get(1).getText();
        String password = columns.get(3).getText();
        $(By.cssSelector("a[href= './main.py']")).click();
        $(By.name("email")).sendKeys(email);
        $(By.name("password")).sendKeys(password);
        $(By.cssSelector("input[value= 'Login']")).click();
        String result = $(By.className("user")).getText().substring(0, 5);
        Assert.assertEquals(result, "Hello");

    }


    private static void signIn() {
        open(REGISTER_URL);
        $(By.name("zip_code")).sendKeys("11111");
        $(By.cssSelector("input[value = 'Continue']")).click();
        $(By.name("first_name")).sendKeys("John");
        $(By.name("last_name")).sendKeys("Doe");
        $(By.name("email")).sendKeys("jdoe@gmail.com");
        $(By.name(("password1"))).sendKeys("12345");
        $(By.name(("password2"))).sendKeys("12345");
        $(By.cssSelector("input[value= 'Register']")).click();
    }


}
