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
public class IndexController {

	@Autowired
	private GameService gameService;

	@GetMapping("/")
	public String showMainPage(ModelMap modelMap, HttpSession session) {
		Player player = (Player) session.getAttribute("currentPlayer");
		boolean loggedIn = false;
		boolean unfinishedGameExists = false;
		if (player != null) {
			loggedIn = true;
			modelMap.addAttribute("username", player.getUsername());
			Game game = gameService.getAttachedGame(player);
			if (game != null && !game.isFinished()) {
				unfinishedGameExists = true;
			}
		}
		modelMap.addAttribute("loggedIn", loggedIn);
		modelMap.addAttribute("unfinishedGameExists", unfinishedGameExists);
		return "index";
	}

}