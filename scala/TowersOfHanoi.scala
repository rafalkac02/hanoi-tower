import scala.collection.mutable.Stack
import scala.io.StdIn.readLine
import scala.math.pow


class TowersOfHanoi {


  def printRules() = {
    println("My beautiful rules")
    println("TO-DO")
  }

  def getInput(): Int = {

    // allowed input: numbers from 1 to 9 or char 'r'
    println("To start, input 'r' for showing the rules or specify level right away by typing number (1-9).")

    def input() = readLine("Input: ")
    var inputOK = false
    var i = ""

    while (inputOK == false) {
      i = input()

      // if 'r' -> print rules and ask for number
      if (i == "r") printRules()

      // correct level
      else if ((1 to 9).toList.toString.contains(i)) {
        inputOK = true
      }

      // if not number or 'r' -> try again
      else println("Invalid input. try again")
    }
    i.toInt

  }

  def minimumMoves(n: Int) = (pow(2, n) - 1).toInt

  def initialRods(n:Int) = {
    // array of 3 stacks
    // 1st stack: 1, 2, 3, ... , n
    // 2nd & 3rd stack empty
    val rods = Array( Stack[Int]()pushAll(n to 1 by -1), Stack[Int](), Stack[Int]() )
    rods
  }

  def isMoveValid(rods: Array[Stack[Int]],rodOut: String, rodIn: String): Boolean = {

    // check for valid rod indexes
    // both must be in range 0-2 and they must not be equal
    if (!(0 to 2).toList.toString.contains(rodOut | rodIn) || rodOut == rodIn)
    {
      return false
    }

    // check if a move can happen
    // 1. rodOut cannot be empty
    // 2. top of rodIn must either be empty or bigger than top of rodOut
    else if (!(rods(rodOut).isEmpty) && ((rods(rodOut)(0) < rods(rodIn)(0)) || rods(rodIn).isEmpty)) {
      true
    }
    else false
  }


  def move(rods: Array[Stack[Int]],rodOut: Int, rodIn: Int) = {
    def out = readLine("Take disks from rode number: ")
    def in = readLine("Put it in rode number: ")


  }


  // 1. game shuts down when user type 'q' at any point
  // 2. game finishes once problem is solved
  def isGameFinished = ???


}