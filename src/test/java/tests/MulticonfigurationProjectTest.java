package tests;

import model.ConsoleOutputPage;
import model.HomePage;
import model.NewItemPage;
import model.RenameItemErrorPage;
import model.multiconfiguration.MultiConfigurationProjectStatusPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class MulticonfigurationProjectTest extends BaseTest {
    private static final String PROJECT_NAME = TestUtils.getRandomStr(8);
    private static final String NEW_PROJECT_NAME = TestUtils.getRandomStr(8);

    @Test
    public void testCreateMultiConfigurationProjectWithValidName() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickDashboard();

        Assert.assertTrue(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMulticonfigurationProjectAddDescription() {
        MultiConfigurationProjectStatusPage multConfPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickAddDescription()
                .fillDescription("Description")
                .clickSave();

        Assert.assertEquals(multConfPage.getProjectDescriptionText(), "Description");
    }

    @Test(dependsOnMethods = "testMulticonfigurationProjectAddDescription")
    public void testMultiConfigurationProjectRenameProjectViaDropDownMenu() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickJobDropdownMenu(PROJECT_NAME)
                .clickRenameMultiConfigurationDropDownMenu()
                .clearFieldAndInputNewName(NEW_PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(NEW_PROJECT_NAME), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaDropDownMenu")
    public void testMultiConfigurationProjectRenameProjectViaSideMenu() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(NEW_PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName(PROJECT_NAME)
                .clickRenameButton();

        Assert.assertEquals(multiConfigPrStatusPage.getNameMultiConfigProject(PROJECT_NAME), PROJECT_NAME);
    }

    @Test
    public void testMultiConfigurationProjectDelete() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickMultConfJobName(PROJECT_NAME)
                .deleteMultiConfigProject();

        Assert.assertFalse(homePage.getJobNamesList().contains(PROJECT_NAME));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectRenameProjectViaSideMenu")
    public void testMultiConfigurationProjectDisable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.getTextDisabledWarning().contains("This project is currently disabled"));
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectDisable")
    public void testMultiConfigurationProjectEnable() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .clickEnableOrDisableButton()
                .clickSaveButton();

        Assert.assertTrue(multiConfigurationProjectStatusPage.disableButtonIsDisplayed());
    }

    @Test
    public void testCreateMultiConfigurationProjectWithDescription() {
        final String nameMCP = "MultiConfigProject000302";
        final String descriptionMCP = "Description000302";

        MultiConfigurationProjectStatusPage multiConfigProject = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(nameMCP)
                .selectMultiConfigurationProjectAndClickOk()
                .inputDescription(descriptionMCP)
                .showPreview()
                .clickSaveButton()
                .clickDashboard()
                .clickMultConfJobName(nameMCP);

        MultiConfigurationProjectStatusPage multiConfigProjectPreview = new MultiConfigurationProjectStatusPage(getDriver());

        Assert.assertEquals(multiConfigProject.getNameMultiConfigProject(nameMCP), nameMCP);
        Assert.assertEquals(multiConfigProject.getProjectDescriptionText(), descriptionMCP);
        Assert.assertEquals(multiConfigProjectPreview.getProjectDescriptionText(), descriptionMCP);

    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithValidName",
            "testMulticonfigurationProjectAddDescription",
            "testMultiConfigurationProjectRenameProjectViaDropDownMenu",
            "testMultiConfigurationProjectRenameProjectViaSideMenu"})
    public void testMultiConfigurationProjectBuild() {

        int countBuildsBeforeNewBuild = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .countBuildsOnSidePanel();

        new MultiConfigurationProjectStatusPage(getDriver()).clickBuildNowOnSideMenu(PROJECT_NAME);
        getDriver().navigate().refresh();

        int countBuildsAfterNewBuild = new MultiConfigurationProjectStatusPage(getDriver())
                .countBuildsOnSidePanel();

        Assert.assertNotEquals(countBuildsAfterNewBuild, countBuildsBeforeNewBuild);
    }

    @Test(dependsOnMethods = "testMultiConfigurationProjectBuild")
    public void testCreateNewMCProjectAsCopyFromExistingProject() {
        String actualProjectName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(NEW_PROJECT_NAME)
                .setCopyFromItemName(PROJECT_NAME)
                .clickOK()
                .clickSaveButton()
                .clickDashboard()
                .getJobName(NEW_PROJECT_NAME);

        Assert.assertEquals(actualProjectName, NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateNewMCProjectAsCopyFromExistingProject")
    public void testFindMultiConfigurationProject() {
        MultiConfigurationProjectStatusPage multiConfigurationProjectStatusPage = new HomePage(getDriver())
                .setSearchAndClickEnter(NEW_PROJECT_NAME);

        Assert.assertEquals(multiConfigurationProjectStatusPage.getNameMultiConfigProject(NEW_PROJECT_NAME), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMultiConfigurationProjectDisableCheckIconDashboardPage() {
        HomePage homePage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickDisableButton()
                .clickDashboard();

        Assert.assertTrue(homePage.isDisplayedIconProjectDisabled());
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithValidName",
            "testMultiConfigurationProjectDisableCheckIconDashboardPage"})
    public void testMultiConfigurationProjectEnableCheckIconDashboardPage() {
        HomePage homePage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickEnableButton()
                .clickDashboard();

        Assert.assertTrue(homePage.isDisplayedIconProjectEnabled());
    }

    @Test
    public void testMultiConfigurationProjectRenameToInvalidNameViaSideMenu() {
        RenameItemErrorPage renameItemErrorPage = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickMultConfJobName(PROJECT_NAME)
                .clickRenameSideMenu()
                .clearFieldAndInputNewName("&")
                .clickSaveButtonAndGetError();

        Assert.assertEquals(renameItemErrorPage.getErrorMessage(), "???&amp;??? is an unsafe character");
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithValidName",
            "testMultiConfigurationProjectBuild"})
    public void testMultiConfigurationProjectsRunJobInBuildHistory() {
        List<String> listNameOfLabels = new HomePage(getDriver())
                .clickDashboard()
                .clickMyViewsSideMenuLink()
                .clickBuildHistory()
                .getNameOfLabelsOnTimeLineBuildHistory();

        Assert.assertTrue(listNameOfLabels.contains(PROJECT_NAME + " #1"));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithDescription")
    public void testMultiConfigurationProjectDisableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickDisableButton();
        Assert.assertTrue(multiConfigPrStatusPage.iconProjectDisabledIsDisplayed());
    }

    @Test(dependsOnMethods = {"testCreateMultiConfigurationProjectWithDescription",
            "testMultiConfigurationProjectDisableCheckIconProjectName"})
    public void testMultiConfigurationProjectEnableCheckIconProjectName() {
        MultiConfigurationProjectStatusPage multiConfigPrStatusPage = new HomePage(getDriver())
                .clickMultConfJobName(PROJECT_NAME)
                .clickEnableButton();

        Assert.assertTrue(multiConfigPrStatusPage.iconProjectEnabledIsDisplayed());
    }

    @Test
    public void testDisableMultiConfigurationProject() {
        Boolean projectIconText = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton()
                .clickDisableButton()
                .clickDashboard()
                .getProjectIconText();

        Assert.assertTrue(projectIconText);
    }

    @Test(dependsOnMethods = "testDisableMultiConfigurationProject")
    public void testEnableMultiConfigurationProject() {
        HomePage buildNowButton = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickEnableButton()
                .clickDashboard()
                .clickProjectDropdownMenu(PROJECT_NAME);

        Assert.assertTrue(buildNowButton.buildNowButtonIsDisplayed());
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectWithValidName")
    public void testMultiConfigurationProjectCheckConsoleOutput() {
        ConsoleOutputPage multiConfigProjectConsole = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .scrollAndClickBuildSteps()
                .selectionAndClickExecuteWindowsFromBuildSteps()
                .enterCommandInBuildSteps("echo Hello world!")
                .clickSaveButton().clickBuildNowButton()
                .clickDropDownBuildIcon()
                .selectAndClickConsoleOutput();

        Assert.assertEquals(multiConfigProjectConsole.getTextConsoleOutputUserName(), "admin");
        Assert.assertTrue(multiConfigProjectConsole.getConsoleOutputText().contains("Finished: SUCCESS"));
    }

    @Ignore
    @Test
    public void testNewestBuildsButton() {
        new HomePage(getDriver()).clickNewItem();
        MultiConfigurationProjectStatusPage newMultiConfigItem = new NewItemPage(getDriver())
                .setItemName(PROJECT_NAME)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSaveButton();
        MultiConfigurationProjectStatusPage mcpStatusPage = new MultiConfigurationProjectStatusPage(getDriver());
        mcpStatusPage.multiConfigurationProjectBuildNow(getDriver());
        mcpStatusPage.multiConfigurationProjectNewestBuilds(getDriver());

        Assert.assertTrue(getDriver().
                findElement(By.xpath("//*[@id=/'buildHistory/']/div[2]/table/tbody/tr[2]")).isDisplayed());
    }

    @Test(dependsOnMethods = "testEnableMultiConfigurationProject")
    public void testSetConfigurationMatrix() {
        MultiConfigurationProjectStatusPage configMatrix = new HomePage(getDriver())
                .clickProject(PROJECT_NAME)
                .clickConfiguration(PROJECT_NAME)
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(PROJECT_NAME, "stage", 1)
                .enterValueUserDefinedAxis("sandbox dev uat prod", 1)
                .scrollAndClickButtonAddAxis()
                .selectUserDefinedAxis()
                .enterNameUserDefinedAxis(PROJECT_NAME, "maven_tool", 2)
                .enterValueUserDefinedAxis("clean validate compile test", 2)
                .clickApplyButton()
                .clickSaveButton();

        Assert.assertTrue(configMatrix.configurationMatrixIsDisplayed());
    }
}
