package steps;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class TestSteps {
    private AndroidDriver<MobileElement> driver;
    private AppiumDriverLocalService service;

    @Before
    public void before() {
        service = createAppiumDriverLocalService();
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I have a {} device")
    public void iHaveAIDevice(String deviceName) {
        driver = createIOSDriver(service.getUrl(), getPlatformVersion(deviceName), getWdaDevicePort(deviceName), getSystemPort(deviceName));
    }

    private AndroidDriver<MobileElement> createIOSDriver(final URL serverURL, final String platformversion, String udid, final int sysstemPort) {
        File app = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("apps/TheApp-v1.10.0.apk")).getFile());

         DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformversion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, sysstemPort);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        return new AndroidDriver<MobileElement>(serverURL, capabilities);
    }

    private AppiumDriverLocalService createAppiumDriverLocalService() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingAnyFreePort();
        service = serviceBuilder.build();
        service.start();
        return service;
    }

    private String getWdaDevicePort(final String deviceName) {
        switch (deviceName) {
            case "Note 9":
                return "4135384244363498";
            case "Note 10":
                return "R38MA0DMT6T";

        }
        return " ";
    }

    private String getPlatformVersion(final String deviceName) {
        switch (deviceName) {
            case "Note 9":
                return "9";
            case "Note 10":
                return "10";

        }
        return " ";
    }
    private int getSystemPort(final String deviceName) {
        switch (deviceName) {
            case "Note 9":
                return 8110;
            case "Note 10":
                return 8111;

        }
        return 8112;
    }
}