class TraitTest6 {

  trait Philosophical {
    def philosophize() {
      println("I consume memory, therefore I am!")
    }

    protected def f()
  }

  class Animal
  trait HasLegs

  class Frog extends Animal with Philosophical with HasLegs {
    override def toString = "green"

    override def f() = {}
  }


  abstract class IntQueue {
    def get(): Int
    def put(x: Int)
  }

  trait Doubling extends IntQueue {
    abstract override def put(x: Int) { super.put(2 * x) }
  }

//  class MyQueue extends Doubling {
//    override def get(): Int = 1
//  }
}
