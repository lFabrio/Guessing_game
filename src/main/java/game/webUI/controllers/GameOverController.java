package game.webUI.controllers;

import game.domain.Game;
import game.domain.Player;
import game.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class GameOverController {

    @Autowired
    private GameService gameService;

    @GetMapping("/gameOver")
    public String showGameOverPage(ModelMap modelMap, HttpSession session) {
        Player player = (Player) session.getAttribute("currentPlayer");
        if (player == null) {
            return "redirect:/";
        }

        Game currentGame = gameService.getAttachedGame(player);
        if (currentGame == null) {
            return "redirect:/";
        }
        String result = currentGame.getLastResult();
        modelMap.addAttribute("username", player.getUsername());
        modelMap.addAttribute("secretNumber", currentGame.getSecretNumber());
        modelMap.addAttribute("gameResult", result);
        return "gameOver";
    }

}
