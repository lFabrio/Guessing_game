package game.webUI.controllers;

import game.requests.ShowLeaderBoardRequest;
import game.responses.CoreError;
import game.responses.ShowLeaderBoardResponse;
import game.services.ShowLeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LeaderBoardController {

    @Autowired
    private ShowLeaderBoardService service;

    @GetMapping("/leaderboard")
    public String showLeaderBoardPage(ModelMap modelMap) {
        modelMap.addAttribute("request", new ShowLeaderBoardRequest());
        return "leaderboard";
    }

    @PostMapping("/leaderboard")
    public String showLeaderBoard(@ModelAttribute(value = "request")
                                  ShowLeaderBoardRequest request, ModelMap modelMap) {
        ShowLeaderBoardResponse response = service.execute(request);
        if (response.hasErrors()) {
            modelMap.addAttribute("errors", response.getErrors());
        } else if (response.getLeaderBoard().isEmpty()) {
            List<CoreError> errors = new ArrayList<>();
            errors.add(new CoreError("leaderboard", "Nothing to show :("));
            modelMap.addAttribute("errors", errors);
        } else {
            modelMap.addAttribute("leaderboard", response.getLeaderBoard());
        }
        return "leaderboard";
    }

}
