package com.hoc.sudokusolver

import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView

class MainPanel internal constructor() : JPanel() {
  private val solver = SudokuSolver(this::update)

  private var cells = EMPTY
  private var task: SudokuSolver.Task? = null

  init {
    initComponents()
  }

  private fun initComponents() {
    layout = null
    preferredSize = Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE + 100)

    JButton("Chọn file input").apply {
      setBounds(5, CELL_SIZE * SIZE + 30, 185, 50)
      addActionListener {
        val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
        val filter = FileNameExtensionFilter("TEXT FILES", "txt", "text")
        fileChooser.fileFilter = filter

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          solver.input = fileChooser.selectedFile.absolutePath

          if (task?.isCancelled == false) {
            task?.cancel(true)
          }

          update(EMPTY)
        }
      }
    }.let(::add)

    JButton("Run").apply {
      setBounds(197, CELL_SIZE * SIZE + 30, 100, 50)
      addActionListener {
        update(EMPTY)
        if (task != null && !task!!.isCancelled) {
          task?.cancel(true)
        }
        task = solver.run()
      }
    }.let(::add)

    JButton("Không delay").apply {
      setBounds(300, CELL_SIZE * SIZE + 30, 140, 50)
      addActionListener {
        if (task?.isCancelled == false) {
          task?.delayTime = 0L
        }
      }
    }.let(::add)
  }

  private fun update(lists: List<List<SudokuCell>>) {
    this.cells = lists
    repaint()
    println("Updated $cells")
  }

  override fun paintComponent(g: Graphics) {
    super.paintComponent(g)
    cells.forEach { row -> row.forEach { it.paint(g) } }
  }

  companion object {
    private val EMPTY = (0 until SIZE).map { i ->
      (0 until SIZE).map {
        SudokuCell(
          row = i,
          col = it,
          isCurrent = false,
          number = 0
        )
      }
    }
  }
}
