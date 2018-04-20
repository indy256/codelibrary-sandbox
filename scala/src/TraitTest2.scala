import scala.collection.mutable.ArrayBuffer

object TraitTest2 {

  trait IntQueue {
    def get: Int

    def put(x: Int)
  }

  class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]

    def get = buf.remove(0)

    def put(x: Int) {
      buf += x
    }
  }

  trait Doubling extends IntQueue {
    abstract override def put(x: Int) {
      super.put(2 * x)
    }
  }

  class MyQueue extends BasicIntQueue with Doubling {
  }

  def main(args: Array[String]): Unit = {
    val q = new MyQueue
    q.put(1)
    println(q.get)
  }
}
