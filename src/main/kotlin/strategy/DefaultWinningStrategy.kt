package strategy

import models.Board
import models.Coordinate
import models.Player

class DefaultWinningStrategy : IWinningStrategy{

    override fun showWinner(playerList: List<Player>){
        playerList.map {player ->
            println("Player " + player.name)
            printBoard(player.board)
        }
        val winnerPlayers = playerList.map { Pair(it, it.board.ships.filter { !it.isAlive }.size) }.sortedBy { it.second }
        winnerPlayers.forEach {
            println(it.first.name + ":" + it.second)
        }
        if(winnerPlayers[0].second == winnerPlayers[1].second) {
            println("It is a draw")
        } else if (winnerPlayers[0].second > winnerPlayers[1].second) {
            println(winnerPlayers[0].first.name + " won")
        }else{
            println(winnerPlayers[1].first.name + " won")
        }
    }

    private fun printBoard(board: Board) {
        val shipLocationsMap = board.ships.associateBy { it.coordinate };
        val missileHitLocationsMap = board.missileHitLocations.associateBy { it }

        (board.boundary.topLeft.x .. board.boundary.bottomRight.x).map {x ->
            (board.boundary.topLeft.y .. board.boundary.bottomRight.y).map { y->
                val boardCoordinate = Coordinate(x, y)
                if (shipLocationsMap.containsKey(boardCoordinate)) {
                    val ship = shipLocationsMap[boardCoordinate]
                    if(ship?.isAlive == true) {
                        print("B")
                    }else{
                        print("X");
                    }
                }else if(missileHitLocationsMap.containsKey(boardCoordinate)){
                    print("O")
                }else {
                    print("_")
                }
            }
            println()
        }
    }
}