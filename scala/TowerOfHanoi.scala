import scala.collection.mutable.Stack
import scala.io.StdIn.readLine
import scala.math.{max, min, pow}
import scala.sys.exit


class TowerOfHanoi {


  def printRules = {
    println("There are 3 rods and n disks. You choose n in range 1-9 to start the game.")
    println("The more disks, the harder puzzle is. At the beginning all disks are placed at left-side rod.")
    println("The puzzle is solved once you manage to transfer them all to the right-side rode")
    println("Disks are transferred between rods one at a time. Bigger disk cannot be placed on top of a smaller one. ")
    println("Type 'q' at any point to exit. Good Luck!")
  }

  def getInput: Int = {

    // allowed input: numbers from 1 to 9 or char 'r'
    println("To start, input 'r' for showing the rules or specify level right away by typing number (1-9).")

    def input() = readLine("Input: ")

    var inputOK = false
    var i = ""

    while (!inputOK) {
      i = input()

      // if 'r' -> print rules and ask for number
      if (i == "r") printRules

      // correct level
      else if ((1 to 9).toList.toString.contains(i)) {
        inputOK = true
      }

      // quit game
      else if (i == "q") {
        println("Thanks for playing. Bye, bye.")
        exit()
      }

      // if not number or 'r' -> try again
      else println("Only accepting numbers 1-9 and 'r'. try again")
    }
    i.toInt

  }

  def minimumMoves(n: Int): Int = (pow(2, n) - 1).toInt

  def initialRods(n: Int): Array[Stack[Int]] = {
    // array of 3 stacks
    // 1st stack: 1, 2, 3, ... , n
    // 2nd & 3rd stack empty
    val rods = Array(Stack[Int]() pushAll (n to 1 by -1), Stack[Int](), Stack[Int]())
    rods
  }

  def isIndexValid(rod: String): Boolean = {
    if (rod.isEmpty) true
    else if ((0 to 2).toList.toString.contains(rod)) {
      true
    }
    else false
  }

  def isMoveValid(rods: Array[Stack[Int]], outIndex: Int, inIndex: Int): Boolean = {

    // check for valid rod indexes
    // both must be in range 0-2 and they must not be equal
    if (outIndex == inIndex) {
      return false
    }

    // check if a move can happen
    // 1. rodOut cannot be empty
    // 2. top of rodIn must either be empty or bigger than top of rodOut
    if (rods(outIndex).nonEmpty && (rods(inIndex).isEmpty || (rods(outIndex)(0) < rods(inIndex)(0)))) {
      true
    }
    else false
  }


  def move(rods: Array[Stack[Int]] /*,rodOut: Int, rodIn: Int*/): Array[Stack[Int]] = {

    def out(): Int = {
      var s = readLine("Take disks from rod number: ")

      // quit
      if (s == "q") {
        println("Thanks for playing. Bye, bye.")
        exit()
      }

      while (s.isEmpty || !isIndexValid(s)) {
        println("Invalid index. Try again.")
        s = readLine("Out index: ")
      }

      s.toInt
    }

    def in(): Int = {
      var s = readLine("Put disks on rod number: ")

      // quit
      if (s == "q") {
        println("Thanks for playing. Bye, bye.")
        exit()
      }


      while (s.isEmpty || !isIndexValid(s)) {
        println("Invalid index. Try again.")
        s = readLine("In index: ")
      }
      s.toInt
    }

    var rodOut = out()
    var rodIn = in()

    while (!isMoveValid(rods, rodOut, rodIn)) {
      println("Invalid move. Try again.")
      rodOut = out()
      rodIn = in()
    }

    rods(rodIn).push(rods(rodOut).pop)
    rods

  }

  def printState(rods: Array[Stack[Int]]): Unit = {

    def centerAlign(item: String, cellWidth: Int) = {
      def leftSpaces = " " * ((cellWidth - item.length) / 2)

      def rightSpaces = " " * (cellWidth - item.length - leftSpaces.length)

      leftSpaces + item + rightSpaces
    }

    // width of each column equals stack's largest element + 2 spaces, but not less than myMin
    def myMin = 7

    def width0: Int = {
      if (rods(0).nonEmpty) max(myMin, 2 * rods(0)(rods(0).size - 1) - 1)
      else myMin
    }

    def width1: Int = {
      if (rods(1).nonEmpty) max(myMin, 2* rods(1)(rods(1).size - 1) - 1)
      else myMin
    }

    def width2: Int = {
      if (rods(2).nonEmpty) max(myMin, 2* rods(2)(rods(2).size - 1) - 1)
      else myMin
    }

    def width = Array(width0, width1, width2)

    // height equals number of all disks
    // if disks are divided into multiple rods
    //  - leave space over them to give player impression that he can put disks at the top
    def height = rods(0).size + rods(1).size + rods(2).size

    for (i <- height to 1 by -1) {

      // print row:
      // 1st column part - 2nd column - 3rd column - new line

      // if 1st column part is this high - print value
      if (i <= rods(0).size) {
        print(centerAlign("=" * (2 * rods(0)(rods(0).size - i) - 1), width0) + "  ")
      }
      else print(centerAlign(" ", width0) + "  ")

      if (i <= rods(1).size) {
        print(centerAlign("=" * (2 * rods(1)(rods(1).size - i) - 1), width1) + "  ")
      }
      else print(centerAlign(" ", width1) + "  ")

      if (i <= rods(2).size) {
        print(centerAlign("=" * (2 * rods(2)(rods(2).size - i) - 1), width2))
      }
      else print(centerAlign(" ", width2) + "  ")

      println()
    }

    // print columns's caption
    println(centerAlign("Rod 0", width0) + "  " + centerAlign("Rod 1", width1) + "  " + centerAlign("Rod 2", width2))

  }


  // 1. game shuts down when user type 'q' at any point
  // 2. game finishes once problem is solved
  def isGameFinished(rods: Array[Stack[Int]], n: Int): Boolean = {
    if (rods(2).size == n) true
    else false
  }


  def play(): Unit = {

    val n = getInput
    var moves = 0

    println(s"For n of $n, the minimum number of moves to solve this puzzle is ${minimumMoves(n)}.")
    var rods = initialRods(n)

    printState(rods)

    while (!isGameFinished(rods, n)) {
      move(rods)
      moves += 1
      printState(rods)
    }

    println(s"Good job. You solved it with $moves move(s)!")
  }
}