package eu.gaminatorium;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *Wszystkie klasy Springowe, które oddziaływują na całą aplikację (tak jak poniższy kontroler błędów)
 *i trudno je zakwalifikować do jednego z podpakiektów domenowych: game, user, security, service_communication
 * można wrzucać to tego pakietu.
 */

@Controller
class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode == null) {
            return new ResponseEntity<>("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Example error controller. Error: " + statusCode, HttpStatus.valueOf(statusCode));
    }
}
