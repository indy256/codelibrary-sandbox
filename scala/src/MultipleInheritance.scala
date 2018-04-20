trait W {
  def d: Unit
}

trait A extends W {
  override def d() {
    println("Invoked A")
  }
}

trait B extends W {
  override def d() {
    println("Invoked B")
  }
}

abstract class C extends W {
  override def d() {
    println("Invoked C")
  }
}

class E extends C with A with B {
}

object MultipleInheritance {
  def main(args: Array[String]): Unit = {
    new E().d()
  }
}
