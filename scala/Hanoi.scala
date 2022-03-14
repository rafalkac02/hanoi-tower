object Hanoi extends App {

  // inform somehow of the minimum number of moves (post or pre solving)


  var towers = new TowersOfHanoi
  val n = towers.getInput()
  println(towers.initialRods(n))
}
