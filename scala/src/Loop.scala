object Loop {
  def main(args: Array[String]) = {
    for (i <- args.indices) {
      println(args(i))
      val s = (1).+(2)
    }
  }
}

