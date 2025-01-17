package setup;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Admincontroller {
    Properties prop;
    public Admincontroller(Properties prop){
        this.prop=prop;
        RestAssured.baseURI="https://dailyfinanceapi.roadtocareer.net";
    }
    public Response login(UserModel userModel){
        Response response=given().contentType("application/json").body(userModel).when().post("/api/auth/login");
        return response;
    }

    public Response searchUser() {
        // Log the token for debugging
        String token = prop.getProperty("token");
       // System.out.println("Token: " + token);

        // Make the API request with the token
        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/user/users");

        return response;
    }

    public Response SearchUserById(String createdUserId){

        String token = prop.getProperty("token");
        System.out.println("Token: " + token);

        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/user/"+createdUserId);

        return response;

    }

    public Response EditUserInfo(UserModel userModel){

        String token = prop.getProperty("token");
        System.out.println("Token: " + token);

        String Id = prop.getProperty("Created_User_Id");
        System.out.println("Id:"+Id);


        Response response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(userModel)
                .when()
                .put("/api/user/"+Id);

        return response;

    }


}
