package com.hoc.sudokusolver

import java.awt.*
import java.awt.Color.*
import java.awt.font.FontRenderContext
import java.awt.geom.AffineTransform
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.concurrent.thread

class TextPanel : JPanel() {
  private val yText: Int
  private var xText = WIDTH
  private val textFont = Font("Firacode", Font.BOLD, 26)

  companion object {
    private const val TEXT = "Sudoku 9x9 solver | Petrus Nguyễn Thái Học | Da Nang University of Technology"
    const val HEIGHT = 50
    const val WIDTH = CELL_SIZE * SIZE
    const val DELTA_X = 10
    const val DELAY_TIME: Long = 120
  }

  init {
    layout = null
    size = Dimension(WIDTH, HEIGHT)

    val frc = FontRenderContext(AffineTransform(), true, true)
    val textWidth = textFont.getStringBounds(TEXT, frc).width.toInt()
    val textHeight = textFont.getStringBounds(TEXT, frc).height.toInt()

    yText = HEIGHT - textHeight / 2

    thread {
      while (true) {
        xText -= DELTA_X
        if (xText <= -textWidth) {
          xText = WIDTH
        }
        SwingUtilities.invokeAndWait { repaint() }
        Thread.sleep(DELAY_TIME)
      }
    }
  }

  override fun paintComponent(g: Graphics?) {
    super.paintComponent(g)
    (g as Graphics2D).run {
      setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)

      color = listOf(
        DARK_GRAY,
        RED,
        YELLOW,
        ORANGE,
        GREEN,
        MAGENTA,
        CYAN,
        BLUE
      ).random()
      font = textFont
      drawString(TEXT, xText, yText)
    }
  }
}