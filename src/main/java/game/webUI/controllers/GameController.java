package game.webUI.controllers;


import game.domain.Game;
import game.domain.Player;
import game.requests.GuessedNumberRequest;
import game.responses.GuessedNumberResponse;
import game.services.GameService;
import game.services.GuessedNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GuessedNumberService guessedNumberService;
    @Autowired
    private GameService gameService;

    @GetMapping("/game")
    public String showGamePage(@RequestParam(name = "restart", required = false) String restart,
                                   ModelMap modelMap, HttpSession session) {
        Player player = (Player) session.getAttribute("currentPlayer");

        if (player == null) {
            return "redirect:/";
        }

        Game game = gameService.getAttachedGame(player);

        if (game == null || game.isFinished()) {
            game = gameService.startNewGame(player, false);
        } else if (restart != null) {
            game = gameService.startNewGame(player, true);
        }

        List<String> turns = game.getTurns();
        if (!turns.isEmpty()) {
            modelMap.addAttribute("turns", turns);
            modelMap.addAttribute("lastGuess", turns.get(turns.size() - 1));
        }

        modelMap.addAttribute("request", new GuessedNumberRequest());
        modelMap.addAttribute("username", player.getUsername());
        modelMap.addAttribute("triesLeft", game.getTriesLeft());
        modelMap.addAttribute("gameResult", game.getLastResult());

        return "game";
    }

    @PostMapping("/game")
    public String processMakeTurnRequest(@ModelAttribute(value = "request") GuessedNumberRequest request,
                                         ModelMap modelMap, HttpSession session) {
        Player player = (Player) session.getAttribute("currentPlayer");

        if (player == null) {
            return "redirect:/";
        }

        Game game = gameService.getAttachedGame(player);

        GuessedNumberResponse response = guessedNumberService.execute(request);

        if (response.hasErrors()) {
            modelMap.addAttribute("errors", response.getErrors());
        } else {
            String result = gameService.makeGuess(response.getGuessedNumber(), player, game);
            if (result.contains("You")) {
                return "redirect:/gameOver";
            }
        }
        return "redirect:/game";
    }

}
