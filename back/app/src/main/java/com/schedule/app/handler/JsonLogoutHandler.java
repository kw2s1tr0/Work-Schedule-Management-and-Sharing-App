package com.schedule.app.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JsonLogoutHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_OK); // 200
    response.setContentType("application/json;charset=UTF-8");

    response
        .getWriter()
        .write(
            """
                    {
                        "message": "Logout successful"
                    }
                    """);
  }
}
