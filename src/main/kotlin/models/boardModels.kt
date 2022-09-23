package models

abstract class BoardItem(
    open val name: String,
    open var isAlive: Boolean,
    open val coordinate: Coordinate
)

data class Ship(override val name: String, override var isAlive: Boolean, override val coordinate: Coordinate)
    : BoardItem(name, isAlive, coordinate)

data class RectangularBoundary (val topLeft: Coordinate, val bottomRight: Coordinate) {
    fun containsCoordinate(coordinate: Coordinate) : Boolean {
        return (coordinate.x >= topLeft.x && coordinate.y >= topLeft.y) &&
                (coordinate.x <= bottomRight.x && coordinate.y <= bottomRight.y)
    }
}

data class Board(
    val boundary: RectangularBoundary,
    val ships : MutableList<BoardItem>,
    val missileHitLocations: MutableList<Coordinate>
){
    fun takeHit (coordinate: Coordinate) {

        if(!boundary.containsCoordinate(coordinate)) {
            throw IllegalArgumentException("Hit coordinate (${coordinate.x}, ${coordinate.y}) is invalid")
        }

        missileHitLocations.add(coordinate)
        val shipHit = ships.find {
            it.coordinate.x == coordinate.x && it.coordinate.y == it.coordinate.y
        }
        shipHit?.let {
            shipHit.isAlive = false
        }
    }

    fun getAllMissileHitLocations(): List<Coordinate> {
        return missileHitLocations;
    }
}