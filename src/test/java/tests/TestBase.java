package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.CreateTestCaseBody;
import models.CreateTestCaseResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.TestCasePage;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static spec.Specs.requestSpec;
import static spec.Specs.responseSuccess;
import static tests.TestData.testCaseName;

public class TestBase {

    TestCasePage testCasePage = new TestCasePage();

    public static String
            projectId = "2285",
            allureTestOpsSession = "0af2d481-9f3f-4f47-a792-1b1bfd1d36de",
            xsrfToken = "b15b11eb-035c-420b-9c41-a1dbb1711b27";

    public static String testCaseId;

    @BeforeAll
    static void setUp() {
        WebDriverProvider.configuration();
        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given(requestSpec)
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/rs/testcasetree/leaf")
                        .then()
                        .spec(responseSuccess)
                        .extract().as(CreateTestCaseResponse.class));

        Integer testCaseId = createTestCaseResponse.getId();

        step("Verify testcase name", () -> {
            assertThat(createTestCaseResponse.getName()).isEqualTo(testCaseName);
        });
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}
