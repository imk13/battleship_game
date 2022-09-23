import models.*
import strategy.DefaultWinningStrategy
import java.io.FileInputStream
import java.util.*


fun getCoordinates(line: String): List<Coordinate> {
    return line.trim().split(":").map { it.trim() }.map { point ->
        val xy = point.split(",").map { it.trim().toInt() }
        Coordinate(xy[0], xy[1])
    }
}

fun main() {

    try {
        // the file to be opened for reading
        val fis = FileInputStream("sample_input/sample_input.txt")
        val sc = Scanner(fis) // file to be scanned

        // returns true if there is another line to read
            //Add your code here to process input commands
        val boardSize = sc.nextLine().trim().toInt()
        val numberOfShips = sc.nextLine().trim().toInt()

        val boardTopLeft = Coordinate(0, 0)
        val boardBottomRight = Coordinate(boardSize-1, boardSize-1)
        val ship1Positions = getCoordinates(sc.nextLine())
        val ship2Positions = getCoordinates(sc.nextLine())

        val numberOfMissiles = sc.nextLine().toInt()

        val missile1Positions = LinkedList<Coordinate>(getCoordinates(sc.nextLine()))
        val missile2Positions = LinkedList<Coordinate>(getCoordinates(sc.nextLine()))

        val boardBoundary = RectangularBoundary(topLeft = boardTopLeft, bottomRight = boardBottomRight)

        val players = BattleShipGame.setGame(boardBoundary, ship1Positions, ship2Positions)
        BattleShipGame(players, DefaultWinningStrategy()).start(numberOfMissiles, listOf(missile1Positions, missile2Positions))



    }catch (ex: Exception){
        println(ex.message)
    }
}