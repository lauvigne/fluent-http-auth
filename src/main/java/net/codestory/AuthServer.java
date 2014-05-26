package net.codestory;

import net.codestory.http.*;
import net.codestory.http.filters.*;
import net.codestory.http.filters.basic.*;
import net.codestory.http.security.*;

public class AuthServer {
  public static void main(String[] args) {
    new WebServer(getConfiguration()).start();
  }

  public static Configuration getConfiguration() {
    Users users = Users.singleUser("admin", "pwd");
    Filter filter = new BasicAuthFilter("/admin", "simple-auth", users);

    return routes -> routes
        .get("/admin/secret1", (context) -> "OK")
        .get("/admin/secret2", (context) -> "OK")
        .get("/public/page1", (context) -> "OK")
        .filter(filter);
  }
}
