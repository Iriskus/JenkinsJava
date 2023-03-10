package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import runner.BaseModel;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class BaseConfigSideMenuFrame<ConfigPage extends BaseConfigPage<?, ?, ?>> extends BaseModel {

    @FindBy(xpath = "//button[@data-section-id='general']")
    private WebElement linkGeneral;

    @FindBy(css = "button.task-link")
    private List<WebElement> configSideMenu;

    protected final ConfigPage configPage;

    public BaseConfigSideMenuFrame(WebDriver driver, ConfigPage configPage) {
        super(driver);
        this.configPage = configPage;
    }

    public ConfigPage clickGeneral() {
        linkGeneral.click();

        return configPage;
    }

    public Set<String> collectConfigSideMenu() {
        return configSideMenu.stream().map(WebElement::getText).collect(Collectors.toCollection(TreeSet::new));
    }
}
