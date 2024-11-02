package atraintegratedsystems.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        String contextPath = request.getContextPath();
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_CODE_FINANCE")) {
//            response.sendRedirect(contextPath + "/Codes/Finance/FinanceSection");
//        } else if (roles.contains("ROLE_STANDARD")) {
//            response.sendRedirect(contextPath + "/Codes/Standard/StandardSection");
//        } else  {
//            response.sendRedirect(contextPath + "/");
//        }
//
//
//
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession();
        String lang = request.getParameter("lang"); // Capture the language from the request
        if (lang != null) {
            session.setAttribute("lang", lang);
        }

        // Redirect to index page with language parameter
        String redirectUrl = request.getContextPath() + "/index?lang=" + lang;
        response.sendRedirect(redirectUrl);
    }


}
