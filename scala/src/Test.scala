object Test {

  def main(args: Array[String]): Unit = {

    var capital = Map("US" -> "Washington", "France" -> "Paris")
    capital += ("Japan" -> "Tokyo")
    println(capital("France"))

    val oneTwoThree = 1 :: 2 :: 3 :: Nil
    println(oneTwoThree)

    val a = 333
    val s = s"value: ${a}"
    println(s)

    for (i <- 1 to 4)
      println("Iteration " + i)
  }

}
