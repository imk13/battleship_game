
import models.*
import strategy.IWinningStrategy
import java.lang.Exception
import java.util.*

class BattleShipGame(private val players: List<Player>, private val winningStrategy: IWinningStrategy) {
    companion object {
        fun setGame(boundary: RectangularBoundary, ship1Positions: List<Coordinate>, ship2Positions: List<Coordinate>): List<Player> {
            val board = Board(
                boundary = boundary,
                ships = ship1Positions.map { Ship("Ship", true, coordinate = it) }.toMutableList(),
                missileHitLocations = mutableListOf()
            )

            val player1 = Player(UUID.randomUUID().toString(), "P1", board.copy())
            val player2 = Player(
                UUID.randomUUID().toString(),
                "P2",
                board.copy(ships = ship2Positions.map { Ship("Ship", true, coordinate = it) }.toMutableList())
            )
            return listOf<Player>(player1, player2)
        }

    }

    fun start(numberOfMissiles: Int, playerMissilePositions: List<LinkedList<Coordinate>>) {
        var currentPlayerIndex = 0
        var numberOfHits = numberOfMissiles * 2
        while (numberOfHits-- > 0) {
            val currentPlayer = players[currentPlayerIndex]
            val nextPlayerIndex =  (currentPlayerIndex + 1)% players.size
            // TODO player change coordinate
            try {
                val missileHitLocation = playerMissilePositions[currentPlayerIndex].first
                println("Player ${currentPlayer.name} hitting ${players[nextPlayerIndex].name}  at ${missileHitLocation}")
                currentPlayer.takeChance(PlayerChance(players[nextPlayerIndex], missileHitLocation))
            } catch (exception: Exception) {
                println(exception.message);
            }
            playerMissilePositions[currentPlayerIndex].removeFirst()
            currentPlayerIndex = nextPlayerIndex
        }
        winningStrategy.showWinner(players)
    }
}