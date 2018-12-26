package com.hoc.sudokusolver

import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel
import javax.swing.filechooser.FileNameExtensionFilter

class MainPanel internal constructor() : JPanel() {
  private val solver = SudokuSolver(this::update)
  private var cells = EMPTY

  init {
    initComponents()
  }

  private fun initComponents() {
    layout = null
    preferredSize = Dimension(CELL_SIZE * SIZE, CELL_SIZE * SIZE + 100)

    val buttonChooseFileWidth = 150
    val buttonRunWidth = 100
    val buttonNoDelayWidth = 100

    val buttonHeight = 50
    val buttonY = CELL_SIZE * SIZE + 30
    val spaceBetweenButtons = 10
    val buttonStartX =
      (CELL_SIZE * SIZE - (buttonChooseFileWidth + spaceBetweenButtons + buttonNoDelayWidth + spaceBetweenButtons + buttonRunWidth)) / 2

    JButton("Choose input file").apply {
      setBounds(
        buttonStartX,
        buttonY,
        buttonChooseFileWidth,
        buttonHeight
      )
      addActionListener {
        //val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
        val fileChooser = JFileChooser("D:/Admin/Documents/Cpp/DoAnGTLT/DoAnGTLT")
        val filter = FileNameExtensionFilter("TEXT FILES", "txt", "text")
        fileChooser.fileFilter = filter

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          solver.cancel()
          solver.input = fileChooser.selectedFile.absolutePath
          update(EMPTY)
        }
      }
    }.let(::add)

    JButton("Run").apply {
      setBounds(
        buttonStartX + buttonChooseFileWidth + spaceBetweenButtons,
        buttonY,
        buttonRunWidth,
        buttonHeight
      )
      addActionListener {
        update(EMPTY)
        solver.run()
      }
    }.let(::add)

    JButton("No delay").apply {
      setBounds(
        buttonStartX + buttonChooseFileWidth + spaceBetweenButtons + buttonNoDelayWidth + spaceBetweenButtons,
        CELL_SIZE * SIZE + 30,
        buttonNoDelayWidth,
        buttonHeight
      )
      addActionListener { solver.setDelayTime(0) }
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
          number = 0,
          isBlankCell = false
        )
      }
    }
  }
}
