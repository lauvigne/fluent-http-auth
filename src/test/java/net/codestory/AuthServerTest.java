package net.codestory;

import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.specification.*;
import net.codestory.http.*;
import org.junit.*;

public class AuthServerTest {
  private static int port;

  @BeforeClass
  public static void setUp() throws Exception {
    WebServer server = new WebServer().configure(AuthServer.getConfiguration()).startOnRandomPort();
    port = server.port();
  }

  @Test
  public void authFilter() {
    givenPort().when().get("/admin/secret1").then().statusCode(401);
    givenPort().when().get("/admin/secret2").then().statusCode(401);
    givenPort().when().get("/public/page1").then().statusCode(200);

    givenPortAndBasicAuth().when().get("/admin/secret1").then().statusCode(200);
    givenPortAndBasicAuth().when().get("/admin/secret2").then().statusCode(200);
    givenPortAndBasicAuth().when().get("/public/page1").then().statusCode(200);
  }

  private RequestSpecification givenPortAndBasicAuth() {
    return givenPort().auth().basic("admin", "pwd");
  }

  private RequestSpecification givenPort() {
    return given().port(port);
  }
}