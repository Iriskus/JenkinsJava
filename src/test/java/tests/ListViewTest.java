package tests;

import model.HomePage;
import model.views.ViewPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class ListViewTest extends BaseTest {

    private static final String RANDOM_LIST_VIEW_NAME = TestUtils.getRandomStr();

    @Test
    public void testCreateNewListViewWithExistingJob() {
        final String projectOne = TestUtils.getRandomStr();

        int quantityProjectsInListView = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(projectOne)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickAddViewLink()
                .setViewName(RANDOM_LIST_VIEW_NAME)
                .setListViewTypeAndClickCreate()
                .addJobToView(projectOne)
                .clickGlobalViewOkButton()
                .getJobNamesList().size();

        Assert.assertEquals(quantityProjectsInListView, 1);
        Assert.assertTrue(new HomePage(getDriver()).getViewList().contains(RANDOM_LIST_VIEW_NAME));
    }

    @Test(dependsOnMethods = "testCreateNewListViewWithExistingJob")
    public void testEditViewAddDescription() {
        final String descriptionRandom = TestUtils.getRandomStr();

        String actualDescription = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditViewLink()
                .addDescription(descriptionRandom)
                .clickGlobalViewOkButton()
                .getTextDescription();

        Assert.assertEquals(actualDescription, descriptionRandom);
    }

    @Test(dependsOnMethods = "testEditViewAddDescription")
    public void testEditViewDeleteDescription() {
        String descriptionText = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditDescription()
                .clearDescription()
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(descriptionText, "");
    }

    @Test(dependsOnMethods = "testEditViewDeleteDescription")
    public void testRemoveSomeHeadersFromProjectStatusTableInListView() {
        final List<String> namesRemoveColumns = List.of("Weather", "Last Failure", "Last Duration");

        int numberOfJobTableHeadersListView = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickEditViewLink()
                .removeSomeColumns(namesRemoveColumns)
                .clickApplyButton()
                .clickGlobalViewOkButton()
                .getJobTableHeadersSize();

        int numberOfJobTableHeadersAll = new ViewPage(getDriver())
                .goToDashboard()
                .getJobTableHeadersSize();

        Assert.assertNotEquals(numberOfJobTableHeadersAll, numberOfJobTableHeadersListView);
    }

    @Test(dependsOnMethods = "testRemoveSomeHeadersFromProjectStatusTableInListView")
    public void testDeleteListView() {

        List<String> viewList = new HomePage(getDriver())
                .clickView(RANDOM_LIST_VIEW_NAME)
                .clickDeleteViewItem()
                .clickYes()
                .getViewList();

        Assert.assertFalse(viewList.contains(RANDOM_LIST_VIEW_NAME));
    }
}
