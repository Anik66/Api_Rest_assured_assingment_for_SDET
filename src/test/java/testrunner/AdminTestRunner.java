package testrunner;

import Utils.Utils;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Admincontroller;
import setup.Setup;
import setup.UserController;
import setup.UserModel;

public class AdminTestRunner extends Setup {

    @Test(priority = 1, description = "Check if user can register")
    public void createUser() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = "anikkumardas966+" + Utils.generateRandomId(1000, 9999) + "@gmail.com";
        String password = "1234";
        String phoneNumber = "01760" + Utils.generateRandomId(1000000, 9999999);
        String address = faker.address().fullAddress();
        String gender = "Female";
        boolean termsAccepted = true;


        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(termsAccepted);


        Response response = userController.createUser(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String id= jsonPath.get("_id");

        Utils.setEnvVar("Created_User_Id",id);
    }
    @Test(priority = 2, description = "Check if user can login successfully")
    public void login() throws ConfigurationException {

        Admincontroller admincontroller = new Admincontroller(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response response= admincontroller.login(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath=response.jsonPath();
        String token= jsonPath.get("token");
        System.out.println(token);
        Utils.setEnvVar("token",token);


        //Allure.description("Admin inputs valid credential to generate token");
    }

    @Test(priority = 3, description = "get All User")
    public void getAllUsers() {
        Admincontroller admincontroller = new Admincontroller(prop);


        Response response = admincontroller.searchUser();

        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.asString());


        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch user list!");

    }

    @Test(priority = 4, description = "serach by user Id")
    public void searchUser(){
        Admincontroller admincontroller = new Admincontroller(prop);
        Response response= admincontroller.SearchUserById(prop.getProperty("Created_User_Id"));
        System.out.println(response.asString());


       // Allure.description("Admin search new user by his Id");
    }
    @Test(priority = 5, description = "edit user info")
    public  void editUserInfo(){
        Admincontroller admincontroller = new Admincontroller(prop);

       UserModel userModel =new UserModel();
         userModel.setFirstName("Elinore");
         userModel.setLastName("Farrell");
         userModel.setEmail("anikkumardas966+2692@gmail.com");
         userModel.setPassword("1234");
         userModel.setPhoneNumber("01742112750");
         userModel.setAddress("726 Jarvis Drives, Harrisport, GA 02477-2883");
         userModel.setGender("Female");
         userModel.setTermsAccepted(true);


        Response response = admincontroller.EditUserInfo(userModel);
        System.out.println(response.asString());



    }





}
