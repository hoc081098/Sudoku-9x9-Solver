package com.hoc.sudokusolver

import java.awt.*
import java.awt.Color.DARK_GRAY
import java.awt.Color.GREEN
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform

const val CELL_SIZE = 60
private val FONT = Font(Font.MONOSPACED, Font.PLAIN, 34)

data class SudokuCell(
  private val number: Int,
  private val isCurrent: Boolean,
  private val row: Int,
  private val col: Int
) {

  private val startX = CELL_SIZE * this.col
  private val startY = CELL_SIZE * this.row
  private val text= number.toString()

  fun paint(g: Graphics) {
    (g as Graphics2D).run {
      color = Color.BLACK
      fillRect(startX, startY, CELL_SIZE, CELL_SIZE)

      if (isCurrent) {
        color = GREEN
        stroke = BasicStroke(
          2f,
          BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL
        )
        drawRect(
          startX + 1,
          startY + 1,
          CELL_SIZE - 2,
          CELL_SIZE - 2
        )
      } else {
        color = DARK_GRAY
        stroke = BasicStroke(
          1f,
          BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL
        )
        drawRect(
          startX + 1,
          startY + 1,
          CELL_SIZE - 2,
          CELL_SIZE - 2
        )
      }

      if (number in 1..SIZE) {
        val frc = FontRenderContext(AffineTransform(), true, true)
        val textWidth = FONT.getStringBounds(text, frc).width.toInt()
        val textHeight = FONT.getStringBounds(text, frc).height.toInt()

        val x = (CELL_SIZE - textWidth) / 2
        val y = CELL_SIZE - textHeight / 2

        color = Color.pink
        font = FONT
        drawString(
          text,
          startX + x,
          startY + y
        )
      }
    }
  }
}
