package com.hoc.sudokusolver


import java.io.File
import java.lang.Thread.sleep
import javax.swing.SwingWorker

const val SIZE = 9
const val DEFAULT_STEP_DELAY = 100L

class SudokuSolver(private val consumer: (List<List<SudokuCell>>) -> Unit) {
  var output = "./output.txt"
  var input = "./input.txt"

  fun run() = Task(input, output, consumer).apply { execute() }

  class Task(
    private val input: String,
    private val output: String,
    private val consumer: (List<List<SudokuCell>>) -> Unit
  ) : SwingWorker<Unit, List<List<SudokuCell>>>() {
    var delayTime = DEFAULT_STEP_DELAY

    override fun process(chunks: List<List<List<SudokuCell>>>) = chunks.forEach(consumer)

    override fun doInBackground() {
      val list = File(input)
        .readText()
        .split("\\D+".toRegex())
        .mapNotNull { it.toIntOrNull() }
      require(list.size == SIZE * SIZE) { "Only solve 9x9" }

      val a = list.chunked(SIZE).map { it.toMutableList() }
      if (solve(a, 0, 0)) {
        a.joinToString("\n") {
          it.joinToString(separator = " ")
        }.let { File(output).writeText(it) }
      }
    }

    private fun solve(
      a: List<MutableList<Int>>,
      row: Int,
      col: Int
    ): Boolean {
      println("Solve row = $row, col = $col")

      when {
        col > SIZE - 1 -> {
          return if (row == SIZE - 1) {
            publish(convertToUI(a, SIZE - 1, SIZE - 1))
            true
          } else {
            solve(a, row + 1, 0)
          }
        }
        a[row][col] == 0 -> {
          for (nextNum in 1..SIZE) {
            if (check(a, row, col, nextNum)) {

              a[row][col] = nextNum
              publish(convertToUI(a, row, col))
              sleep(delayTime)

              if (solve(a, row, col + 1)) {
                return true
              }

              a[row][col] = 0
              publish(convertToUI(a, row, col))
              sleep(delayTime)
            }
          }
        }
        else -> return solve(a, row, col + 1)
      }
      return false
    }

    private fun convertToUI(a: List<List<Int>>, curRow: Int, curCol: Int) =
      a.mapIndexed { i, r ->
        r.mapIndexed { j, e ->
          SudokuCell(
            number = e,
            col = j,
            row = i,
            isCurrent = curRow == i && curCol == j
          )
        }
      }

    private fun check(a: List<List<Int>>, row: Int, col: Int, nextNum: Int) = a[row].all { it != nextNum } &&
        (0 until SIZE).all { a[it][col] != nextNum } &&
        (row / 3 * 3 until row / 3 * 3 + 3).all { i ->
          (col / 3 * 3 until col / 3 * 3 + 3).all { a[i][it] != nextNum }
        }
  }
}