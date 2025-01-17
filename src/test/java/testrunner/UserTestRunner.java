package testrunner;

import Utils.Utils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
import setup.AddItemModel;
import setup.Setup;
import setup.UserController;
import setup.UserModel;

public class UserTestRunner extends Setup {

    @Test(priority = 1, description = "Login by old user")
    public void LoginByUser() throws ConfigurationException {
        UserController usercontroller = new UserController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("anikd1439+98777@gmail.com");
        userModel.setPassword("1234");
        Response response= usercontroller.login(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String userToken=jsonPath.get("token");
        String id= jsonPath.get("_id");
        System.out.println(id);
        Utils.setEnvVar("userToken",userToken);
        Utils.setEnvVar("User_Id",id);
    }


  @Test(priority = 2, description = "Get Item List")

    public void getItemList() {

        UserController usercontroller = new UserController(prop);
        Response response = usercontroller.getItemList();


        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());


    }

    @Test(priority = 3, description = "Add Item to User Costs")
    public void addItem() {
        UserController usercontroller = new UserController(prop);


        AddItemModel addItemModel = new AddItemModel();
        addItemModel.setItemName("course free");
        addItemModel.setQuantity(1);
        addItemModel.setAmount("2500");
        addItemModel.setPurchaseDate("2024-01-07");
        addItemModel.setMonth("January");
        addItemModel.setRemarks("500 tk discount");

        Response response = usercontroller.AddItem(addItemModel);
        System.out.println(response.asString());


    }
    @Test(priority = 4, description = "update user cost Item ")
    public  void updatedItemName() throws ConfigurationException {

        UserController usercontroller = new UserController(prop);

        AddItemModel addItemModel = new AddItemModel();
        addItemModel.setItemName("course free For SDET");
        addItemModel.setQuantity(1);
        addItemModel.setAmount("2500");
        addItemModel.setPurchaseDate("2024-01-07");
        addItemModel.setMonth("January");
        addItemModel.setRemarks("500 tk discount");
        usercontroller.UpdateItem(addItemModel);
        Response response = usercontroller.AddItem(addItemModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String id= jsonPath.get("_id");
        Utils.setEnvVar("itemListId",id);



    }
    @Test(priority = 5, description = "delete user cost Item ")
    public void DeleteItemList(){
        UserController userController=new UserController(prop);
        Response response= userController.deleteUser(prop.getProperty("itemListId"));
        System.out.println(response.asString());



    }


}

