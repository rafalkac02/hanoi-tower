import scala.collection.mutable.Stack
import scala.io.StdIn.readLine
import scala.math.{max, min, pow}


class TowerOfHanoi {


  def printRules = {
    println("My beautiful rules")
    println("TO-DO")
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
    if ((0 to 2).toList.toString.contains(rod)) {
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
    if (rods(outIndex).nonEmpty && (rods(inIndex).isEmpty || (rods(outIndex)(0) < rods(inIndex)(0))) ) {
      true
    }
    else false
  }


  def move(rods: Array[Stack[Int]] /*,rodOut: Int, rodIn: Int*/) = {

    def out(): Int = {
      var s = readLine("Take disks from rod number: ")
      while (!isIndexValid(s)) {
        println("Invalid index. Try again.")
        s = readLine("Out index: ")
      }
      s.toInt
    }

    def in(): Int = {
      var s = readLine("Put disks on rod number: ")
      while (!isIndexValid(s)) {
        println("Invalid index. Try again.")
        s = readLine("In index: ")
      }
      s.toInt
    }

    var rodOut = out()
    var rodIn = in()

    while(!isMoveValid(rods, rodOut, rodIn)) {
      println("Invalid move. Try again.")
      rodOut = out()
      rodIn = in()
    }

    rods(rodIn).push(rods(rodOut).pop)
    rods
  }

  def printState(rods: Array[Stack[Int]]) = {

    def centerAlign(item: String, cellWidth: Int) = {
      def leftSpaces = " " * ((cellWidth-item.length)/2)
      def rightSpaces = " " * (cellWidth-item.length-leftSpaces.length)
      leftSpaces + item + rightSpaces
    }

    // width of each column equals stack's highest element + 2 spaces, but not less than myMin
    def myMin = 7
    def width0: Int = {
      if (rods(0).nonEmpty) min(myMin, rods(0)(rods(0).size - 1) + 2)
      else myMin
    }
    def width1: Int = {
      if (rods(1).nonEmpty) min(myMin, rods(1)(rods(1).size - 1) + 2)
      else myMin
    }
    def width2: Int = {
      if (rods(2).nonEmpty) min(myMin, rods(2)(rods(2).size - 1) + 2)
      else myMin
    }
    def width = Array(width0, width1, width2)

    // height equals highest stack
    def height = max(max(rods(0).size, rods(1).size), rods(2).size)

    for (i <- height to 1 by -1) {

      // print row:
      // print 1st column part
      // print 2nd column part
      // print 3rd column part

      // if 1st column part is this high - print value
      if (i <= rods(0).size) {
        print(centerAlign(rods(0)(rods(0).size - i).toString, width0) + "  ")
      }
      else print(" " * width0 + "  ")

      if (i <= rods(1).size) {
        print(centerAlign(rods(1)(rods(1).size - i).toString, width1) + "  ")
      }
      else print(" " * width1 + "  ")

      if (i <= rods(2).size) {
        print(centerAlign(rods(2)(rods(2).size - i).toString, width2))
      }
      else print(" " * width2 + "  ")

      println()
    }

    // print columns's caption
    println(centerAlign("Rod 0  ", width0) + centerAlign("Rod 1  ", width1) + centerAlign("Rod 2", width2))

  }


  // 1. game shuts down when user type 'q' at any point
  // 2. game finishes once problem is solved
  def isGameFinished(rods: Array[Stack[Int]], n: Int): Boolean = {
    if (rods(2).size == n) true
    else false
  }


  def play() = {
    val n = getInput
    println(s"For n of $n, the minimum number of moves to solve this puzzle is ${minimumMoves(n)}")
    var rods = initialRods(n)
    println("rods are now: ")
    printState(rods)

    var moves = 0

    while (!isGameFinished(rods, n)) {
      move(rods)
      moves += 1
      println("rods are now: ")
      printState(rods)
    }

    println(s"Good job. You solved it with $moves moves!")
  }
}