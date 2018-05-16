import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

val deferred = (1..1_000_000).map { n ->
    async {
        delay(1000)
        n
    }
}

fun main(args: Array<String>) {
    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }
}
