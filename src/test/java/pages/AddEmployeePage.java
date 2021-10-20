package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class AddEmployeePage extends CommonMethods {
    @FindBy(id = "firstName")
    public WebElement firstName;
    @FindBy(id = "lastName")
    public WebElement lastName;
    @FindBy(id = "middleName")
    public WebElement middleName;
    @FindBy(id = "btnSave")
    public WebElement saveBtn;

    public AddEmployeePage() {
        PageFactory.initElements(driver, this);
    }
}
