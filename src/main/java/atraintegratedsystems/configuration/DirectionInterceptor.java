package atraintegratedsystems.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class DirectionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();

        // Get the current locale language from the session (or cookie)
        String lang = (String) session.getAttribute("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");
        if (lang == null) {
            lang = "en"; // default language
        }

        // Set direction based on the current language
        String direction = (lang.equals("ps") || lang.equals("fa")) ? "rtl" : "ltr";
        session.setAttribute("dir", direction);

        return true;
    }
}

