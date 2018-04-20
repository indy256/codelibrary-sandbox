class Cat {
  val dangerous = false
}

case class Tiger(override val dangerous: Boolean, private var age: Int) extends Cat

object TraitTest5 {
  def main(args: Array[String]): Unit = {
    println("123")

    val tiger: Tiger = Tiger(true, 5)
    println(tiger)

    val cat: Cat = Tiger(true, 5)
    println(cat.dangerous)

  }
}