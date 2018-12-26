package com.hoc.sudokusolver

import java.awt.*
import java.awt.Color.DARK_GRAY
import java.awt.Color.WHITE
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform

const val CELL_SIZE = 60
private val FONT = Font("Firacode", Font.PLAIN, 34)

data class SudokuCell(
  private val number: Int,
  private val isCurrent: Boolean,
  private val row: Int,
  private val col: Int,
  private val isBlankCell: Boolean
) {

  private val startX = CELL_SIZE * this.col
  private val startY = CELL_SIZE * this.row
  private val text = number.toString()

  fun paint(g: Graphics) {
    (g as Graphics2D).run {
      color = Color.BLACK
      fillRect(startX, startY, CELL_SIZE, CELL_SIZE)

      val topLeftX = startX + 1
      val topLeftY = startY + 1

      if (isCurrent) {
        color = Color.LIGHT_GRAY
        stroke = BasicStroke(
          2f,
          BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL
        )
        drawRect(
          topLeftX,
          topLeftY,
          CELL_SIZE - 2,
          CELL_SIZE - 2
        )
      } else {
        stroke = BasicStroke(
          1f,
          BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_BEVEL
        )

        val bottomRightX = topLeftX + CELL_SIZE - 2
        val bottomRightY = topLeftY + CELL_SIZE - 2
        val grid3x3Color = Color(0xaaaaaa)

        color = if (row > 0 && row % 3 == 0) {
          grid3x3Color
        } else {
          DARK_GRAY
        }
        drawLine(
          topLeftX,
          topLeftY,
          bottomRightX,
          topLeftY
        )

        color = if (col < SIZE - 1 && (col + 1) % 3 == 0) {
          grid3x3Color
        } else {
          DARK_GRAY
        }
        drawLine(
          bottomRightX,
          topLeftY,
          bottomRightX,
          bottomRightY
        )

        color = if (row < SIZE - 1 && (row + 1) % 3 == 0) {
          grid3x3Color
        } else {
          DARK_GRAY
        }
        drawLine(
          bottomRightX,
          bottomRightY,
          topLeftX,
          bottomRightY
        )

        color = if (col > 0 && col % 3 == 0) {
          grid3x3Color
        } else {
          DARK_GRAY
        }
        drawLine(
          topLeftX,
          bottomRightY,
          topLeftX,
          topLeftY
        )
      }

      if (number in 1..SIZE) {
        val frc = FontRenderContext(AffineTransform(), true, true)
        val textWidth = FONT.getStringBounds(text, frc).width.toInt()
        val textHeight = FONT.getStringBounds(text, frc).height.toInt()

        val x = (CELL_SIZE - textWidth) / 2
        val y = CELL_SIZE - textHeight / 2

        color = if (isBlankCell) {
          Color.CYAN
        } else {
          Color.PINK
        }
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
