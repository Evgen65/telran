import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import helpers.DataHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class  RegistrationTest extends TestSase {
    @BeforeMethod
    public void precondition() {
        if (page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("account-options")).isVisible()) {
            page.waitForTimeout(5000);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("account-options")).click();
            page.getByText("Logout").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes")).click();
        }
    }

    @Test
    public void registrationSuccessTest() {
        String name = "name" + DataHelper.randomNumeric(4);
        String email = DataHelper.generateEmail(8);
        String password = DataHelper.generatePassword(8);
        String country = "Israel";
        String city = "City" + DataHelper.randomNumeric(4);
        page.navigate("https://music-guru-classic-e2e.web.app/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Email")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Don't have an account? Sign Up")).click();
        page.getByLabel("Username").click();
        page.getByLabel("Username").fill(name);
        page.getByLabel("Email").click();
        page.getByLabel("Email").fill(email);
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill(password);
        page.getByText("arrow_drop_down").click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Country")).fill("isr");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Israel")).locator("div").nth(1).click();
        page.getByLabel("State/City").click();
        page.getByLabel("State/City").fill(city);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign up")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Check if verified")).click();
        page.locator("(//i[normalize-space()='person'])[1]").click();
        page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Profile")).click();
        // page.waitForLoadState(LoadState.NETWORKIDLE);
        String locator = String.format("(//p[contains(text(),'%s')])[2]", email);
        page.waitForSelector(locator);
        String elementText = page.locator(locator).textContent();
        Assert.assertTrue(elementText.contains(email));

    }

    @Test
    public void registrationUnsuccessfulTest() {
        String name = "gilad";
        String email = "gilad.test.2022@gmail.com";
        String password ="Abcd1234$";
        String country = "Israel";
        String city = "City";
        page.navigate("https://music-guru-classic-e2e.web.app/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Email")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Don't have an account? Sign Up")).click();
        page.getByLabel("Username").click();
        page.getByLabel("Username").fill(name);
        page.getByLabel("Email").click();
        page.getByLabel("Email").fill(email);
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill(password);
        page.getByText("arrow_drop_down").click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Country")).fill("isr");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Israel")).locator("div").nth(1).click();
        page.getByLabel("State/City").click();
        page.getByLabel("State/City").fill(city);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign up")).click();
        try {
            ElementHandle dialogElement = page.waitForSelector("//div[contains(text(), 'A user with this email already exists!')]",
                    new Page.WaitForSelectorOptions().setTimeout(5000));

            Assert.assertNotNull(dialogElement, "Alert 'A user with this email already exists!' is not present");
            String dialogMessage = dialogElement.textContent();
            Assert.assertEquals(dialogMessage, "A user with this email already exists!", "Alert message is not 'A user with this email already exists!'");
            System.out.println("Alert 'A user with this email already exists!' is present");
        } catch (PlaywrightException e) {
            System.out.println("Alert 'A user with this email already exists!' is not present");
        }
    }
}