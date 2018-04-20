import scala.util.control.Breaks

class T {
  var v = 0


}

object T {

  def f(): Int = {
    1
  }

  def main(args: Array[String]): Unit = {
    val t = new T

    val a: Array[Int] = Array(1, 2, 3)
    for (x <- a) {

    }

    (new String).hashCode

    Breaks.breakable {
      var i = 0
      while (true) {
        i += 1
        if (i == 5) Breaks.break
      }
    }

    val f: (Int, Int) => Int = (x: Int, y: Int) => x + y

    val list = List(-11, -10, -5, 0, 5, 10)
    list.filter((x: Int) => 0 < x)
    list.filter(0 < _)
    list.filter(0 <)

    val g = (_: Int) + (_: Int)

    list.foreach((x: Int) => println(x))
    list.foreach(x => println(x))
    list.foreach(println(_))
    list.foreach(println _)
    list.foreach(println)
  }
}
