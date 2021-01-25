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

}
