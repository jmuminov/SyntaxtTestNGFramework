package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddEmployeePage;
import pages.DashBoardPage;
import pages.EmployeeListPage;
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
        EmployeeListPage employeeListPage = new EmployeeListPage();
        SoftAssert softAssert = new SoftAssert();

        List<Map<String, String>> newEmployees = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, "EmployeeData");
        Iterator<Map<String, String>> it = newEmployees.iterator();
        while (it.hasNext()) {
            click(dashBoardPage.pimOption);
            click(dashBoardPage.addEmployeeButton);
            Map<String, String> map = it.next();
            sendText(addEmployeePage.firstName, map.get("FirstName"));
            sendText(addEmployeePage.middleName, map.get("MiddleName"));
            sendText(addEmployeePage.lastName, map.get("LastName"));
            String employeeIdValue = addEmployeePage.employeeID.getAttribute("value");
            sendText(addEmployeePage.photograph, map.get("Photograph"));

            if (!addEmployeePage.createLoginCheckBox.isSelected()) {
                click(addEmployeePage.createLoginCheckBox);
            }

            sendText(addEmployeePage.createUsername, map.get("Username"));
            sendText(addEmployeePage.createPassword, map.get("Password"));
            sendText(addEmployeePage.rePassword, map.get("Password"));
            click(addEmployeePage.saveBtn);

            click(dashBoardPage.pimOption);
            click(dashBoardPage.employeeListOption);

            sendText(employeeListPage.idEmployee, employeeIdValue);
            click(employeeListPage.searchButton);

            List<WebElement> rowData = driver.findElements(By.xpath("//*[@id = 'resultTable']/tbody/tr"));

            for (int i = 0; i < rowData.size(); i++) {
                System.out.println("I am inside the loop to get values for the newly generated employee");
                String rowText = rowData.get(i).getText();
                System.out.println(rowText);
                String expectedData = employeeIdValue + " " + map.get("FirstName") + " " + map.get("MiddleName") + " " + map.get("LastName");

                softAssert.assertEquals(rowText, expectedData);
            }
        }
        softAssert.assertAll();
    }
}