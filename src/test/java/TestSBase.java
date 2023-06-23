import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestSBase {
    protected Browser browser;
    protected Page page;
    protected BrowserContext context;

    @BeforeClass
    public void setUp() {

        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setChannel("chrome"));

        context = browser.newContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
            browser = null;
        }

    }

}
