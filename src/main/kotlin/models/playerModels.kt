package models


data class Player(
    val id: String,
    val name: String,
    val board: Board
) {
    fun takeChance(playerChance: PlayerChance) {
        if (playerChance.opponentPlayer.id == this.id){
            throw IllegalArgumentException("Player cannot hit itself");
        }
        playerChance.opponentPlayer.takeHit(playerChance.coordinate);
    }
    private fun takeHit(coordinate: Coordinate) {
        board.takeHit(coordinate)
    }
}

data class PlayerChance(val opponentPlayer: Player, val coordinate: Coordinate)