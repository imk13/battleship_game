package strategy;

import models.Player;

import java.util.List;

public interface IWinningStrategy {
    void showWinner(List<Player> playerList);
}
