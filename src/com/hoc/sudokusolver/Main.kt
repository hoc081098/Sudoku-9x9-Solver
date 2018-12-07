package com.hoc.sudokusolver

import javax.swing.*
import java.awt.*
import java.util.Arrays

fun main() {
  SwingUtilities.invokeLater {
    UIManager.getInstalledLookAndFeels()
      .find { it.name == "Nimbus" }
      ?.let { UIManager.setLookAndFeel(it.className) }

    JFrame("Sudoku solver").run {
      defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

      contentPane.layout = BorderLayout()
      contentPane.add(MainPanel(), BorderLayout.CENTER)
      pack()

      setLocationRelativeTo(null)
      isVisible = true
    }
  }
}