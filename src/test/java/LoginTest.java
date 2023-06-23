import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends TestSase{

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
    public void LoginSuccessfulTest() {

        page.navigate("https://music-guru-classic-e2e.web.app/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Email")).click();
        page.getByLabel("Email").fill("abcd@mail.com");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("Abcd1234$$");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
        page.waitForTimeout(5000);
        Assert.assertTrue(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("account-options")).isVisible());

        try {
            ElementHandle dialogElement = page.waitForSelector("//div[contains(text(), 'Signed in successfully \uD83D\uDD25')]",
                    new Page.WaitForSelectorOptions().setTimeout(5000));

            Assert.assertNotNull(dialogElement, "Alert 'Signed in successfully \uD83D\uDD25' is not present");
            String dialogMessage = dialogElement.textContent();
            Assert.assertEquals(dialogMessage, "Signed in successfully \uD83D\uDD25", "Alert message is not 'Signed in successfully\uD83D\uDD25'");
            System.out.println("Alert 'Signed in successfully\uD83D\uDD25' is present");
        } catch (PlaywrightException e) {
            System.out.println("Alert 'Signed in successfully \uD83D\uDD25' is not present");

//        } finally {
//            browser.close();
        }


    }

    @Test
    public void LoginUnSuccessfulTestLogin() {
        page.navigate("https://music-guru-classic-e2e.web.app/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Email")).click();
        page.getByLabel("Email").fill("abcdmail.com");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("Abcd1234$$");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
        page.waitForTimeout(5000);

        Assert.assertFalse(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("account-options")).isVisible());
        try {
            ElementHandle dialogElement = page.waitForSelector("//div[contains(text(), 'Error signing in, please try again.')]",
                    new Page.WaitForSelectorOptions().setTimeout(5000));

            Assert.assertNotNull(dialogElement, "Alert 'Error signing in, please try again.' is not present");
            String dialogMessage = dialogElement.textContent();
            Assert.assertEquals(dialogMessage, "Error signing in, please try again.", "Alert message is not 'Error signing in, please try again.'");
            System.out.println("Alert 'Error signing in, please try again.' is present");
        } catch (PlaywrightException e) {
            System.out.println("Alert 'Error signing in, please try again.' is not present");

//        } finally {
//            browser.close();
        }

    }

    @Test
    public void LoginUnSuccessfulTestPassword() {
        page.navigate("https://music-guru-classic-e2e.web.app/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Email")).click();
        page.getByLabel("Email").fill("abcd@mail.com");
        page.getByLabel("Password").click();
        page.getByLabel("Password").fill("Abcd1234");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
        page.waitForTimeout(5000);
        Assert.assertTrue(page.waitForSelector("//div[@role='alert']").textContent().contains("Wrong password"));
        Assert.assertFalse(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("account-options")).isVisible());
        try {
            ElementHandle dialogElement = page.waitForSelector("//div[contains(text(), 'Wrong password')]",
                    new Page.WaitForSelectorOptions().setTimeout(5000));

            Assert.assertNotNull(dialogElement, "Alert 'Wrong password' is not present");
            String dialogMessage = dialogElement.textContent();
            Assert.assertEquals(dialogMessage, "Wrong password", "Alert message is not 'Wrong password'");
            System.out.println("Alert 'Wrong password' is present");
        } catch (PlaywrightException e) {
            System.out.println("Alert 'Wrong password' is not present");
//        } finally {
//            browser.close();
        }
    }
}
