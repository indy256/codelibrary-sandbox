import java.util.Random

// https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm
object Kmp {
  def prefixFunction(s: String): Array[Int] = {
    val p: Array[Int] = new Array[Int](s.length)
    var k: Int = 0
    var i: Int = 1
    while (i < s.length) {
      {
        while (k > 0 && s.charAt(k) != s.charAt(i)) k = p(k - 1)
        if (s.charAt(k) == s.charAt(i)) {
          k += 1
        }
        p(i) = k
      }
      {
        i += 1; i - 1
      }
    }
    p
  }

  def kmpMatcher(s: String, pattern: String): Int = {
    val m: Int = pattern.length
    if (m == 0) return 0
    val p: Array[Int] = prefixFunction(pattern)
    var i: Int = 0
    var k: Int = 0
    while (i < s.length) {
      {
        while (k > 0 && pattern.charAt(k) != s.charAt(i)) k = p(k - 1)
        if (pattern.charAt(k) == s.charAt(i)) {
          k += 1; k
        }
        if (k == m) return i + 1 - m
      }
      {
        i += 1; i - 1
      }
    }
    -1
  }

  // random tests
  def main(args: Array[String]) {
    val rnd: Random = new Random(1)
    var step: Int = 0
    while (step < 10000) {
      {
        val s: String = getRandomString(rnd, 100)
        val pattern: String = getRandomString(rnd, 5)
        val pos1: Int = kmpMatcher(s, pattern)
        val pos2: Int = s.indexOf(pattern)
        if (pos1 != pos2) throw new RuntimeException
      }
      {
        step += 1; step - 1
      }
    }
  }

  def getRandomString(rnd: Random, maxlen: Int): String = {
    val n: Int = rnd.nextInt(maxlen)
    val s: Array[Char] = new Array[Char](n)
    var i: Int = 0
//    while (i < n) s(i) = ('a' + rnd.nextInt(3)).toChar {
//      i += 1; i - 1
//    }
    new String(s)
  }
}
