import domain.Problem
import domain.Command
import domain.Match

fun main() {
    println("Application start")
    Problem(
        Match(),
        Command(),
        scenario = 1,
    ).doProblem()

    Problem(
        Match(),
        Command(),
        scenario = 2,
    ).doProblem()
}