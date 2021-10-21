package testcases;

import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashBoardPage;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;
import utils.Constants;
import utils.ExcelReading;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeTest extends CommonMethods {
    @Test(groups = "smoke")
    public void addEmployee() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        DashBoardPage dashBoardPage = new DashBoardPage();
        click(dashBoardPage.pimOption);
        click(dashBoardPage.addEmployeeButton);

        AddEmployeePage addEmployeePage = new AddEmployeePage();
        sendText(addEmployeePage.firstName, "Test");
        sendText(addEmployeePage.middleName, "Person");
        sendText(addEmployeePage.lastName, "Name");
        click(addEmployeePage.saveBtn);
    }
    @Test
    public void addMultipleEmployees() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        DashBoardPage dashBoardPage = new DashBoardPage();
        AddEmployeePage addEmployeePage = new AddEmployeePage();

        List<Map<String, String>> newEmployees = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, "EmployeeData");
        Iterator<Map<String, String>> it = newEmployees.iterator();
        while(it.hasNext()) {
            click(dashBoardPage.pimOption);
            click(dashBoardPage.addEmployeeButton);
            Map<String, String> map = it.next();
            sendText(addEmployeePage.firstName, map.get("FirstName"));
            sendText(addEmployeePage.middleName, map.get("MiddleName"));
            sendText(addEmployeePage.lastName, map.get("LastName"));
            sendText(addEmployeePage.photograph, map.get("Photograph"));

            if(!addEmployeePage.createLoginCheckBox.isSelected()) {
                click(addEmployeePage.createLoginCheckBox);
            }

            sendText(addEmployeePage.createUsername, map.get("Username"));
            sendText(addEmployeePage.createPassword, map.get("Password"));
            sendText(addEmployeePage.rePassword, map.get("Password"));
            click(addEmployeePage.saveBtn);
        }
    }
}
