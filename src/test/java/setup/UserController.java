package setup;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop=prop;
        RestAssured.baseURI="https://dailyfinanceapi.roadtocareer.net";
    }
    public Response login(UserModel userModel){
        Response response=given().contentType("application/json").body(userModel).when().post("/api/auth/login");
        return response;
    }

    public Response createUser(UserModel userModel){
        Response response=given().contentType("application/json").body(userModel)
                .when().post("/api/auth/register");
        return response;

    }

    public Response getItemList() {
        // Fetch the token from properties
        String token = prop.getProperty("userToken");
        System.out.println(token);

        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/costs/");



        return response;
    }


    public Response AddItem(AddItemModel addItemModel) {
        String token = prop.getProperty("userToken");

        // Log the token for debugging
        System.out.println("Authorization Token: " + token);


        // Pass the AddItemModel object as the request body
        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(addItemModel) // Add the request body here
                .when()
                .post("/api/costs");

        return response;
    }

    public Response UpdateItem(AddItemModel addItemModel){
        String token = prop.getProperty("userToken");
        String Id=prop.getProperty("User_Id");
         System.out.println("User Token:"+token);
         System.out.println("Id:"+Id);

        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(addItemModel) // Add the request body here
                .when()
                .put("/api/costs/"+Id);

        return response;

    }


    public Response deleteUser(String itemListId){
        String token = prop.getProperty("userToken");
        System.out.println(token);


        Response response=given().contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when().delete("/api/costs/"+itemListId);
        return response;
    }







}
