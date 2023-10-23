package game.webUI.controllers;

import game.domain.Player;
import game.requests.LoginRequest;
import game.responses.LoginResponse;
import game.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(name = "logout", required = false) String logout,
                                ModelMap modelMap, HttpSession session) {
        Player player = (Player) session.getAttribute("currentPlayer");

        if (player != null) {
            if (logout != null) {
                session.removeAttribute("currentPlayer");
            }
            return "redirect:/";
        }

        modelMap.addAttribute("request", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String processLoginRequest(@ModelAttribute(value = "request") LoginRequest request,
                                      ModelMap modelMap, HttpSession session) {
        LoginResponse response = loginService.execute(request);
        if (response.hasErrors()) {
            modelMap.addAttribute("errors", response.getErrors());
            return "login";
        } else {
            session.setAttribute("currentPlayer", response.getPlayer());
            return "redirect:/game";
        }
    }

}
