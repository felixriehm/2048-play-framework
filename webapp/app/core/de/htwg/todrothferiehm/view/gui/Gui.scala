package core.de.htwg.todrothferiehm.view.gui

/**
  * Created by feriehm on 09.11.2015.
  */

import core.de.htwg.todrothferiehm.model._
import core.de.htwg.todrothferiehm.util.ControllerContainer
import play.api.Play
import play.api.Play.current

import scala.language.postfixOps
import scala.swing.Reactor
import scalafx.Includes._
import scalafx.animation.{Interpolator, Timeline}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.input._
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class Gui(val controllers: ControllerContainer) extends JFXApp with Reactor {

  listenTo(controllers.turnController, controllers.scoreController)

  var cells: Set[GuiCell] = Set[GuiCell]()

  reactions += {
    case n: SlideEvent => SlideTile(n.start, n.end)
    case m: TileAddedEvent => TileAdded(m.pos, m.number)
    case k: MergeEvent => MergeTile(k.pos, k.number)
    case v: ScoresGainedEvent => ScoresGaine(v.score)
    case l: NewGameStartedEvent => newGame(l.size)
    case e: FieldChangedEvent => updateField()
    case g: GameOverEvent => gameOver()
    case o: ScoreChangedEvent => Platform.runLater(score.text = o.score.toString)

  }

  def updateField(): Unit = {
    cells.foreach(c => c.redraw())
  }

  def ScoresGaine(scoreGain: Int): Unit = {
    Platform.runLater({
      val timeline = new Timeline {
        cycleCount = 1
        autoReverse = false

        var addedScore = new Label {
          text = "+" + scoreGain.toString
          styleClass = List("textFont")
        }
        addedScore.padding = Insets(0, 0, 0, score.width.value + addedScore.width.value + 100)

        bottomAnimationBox.children.add(addedScore)

        onFinished = (event: ActionEvent) => {
          bottomAnimationBox.children.remove(addedScore)
        }

        keyFrames = Seq(
          at(350 ms) {
            addedScore.translateYProperty() -> -30 tween Interpolator.LINEAR
          },
          at(350 ms) {
            addedScore.opacityProperty() -> 0 tween Interpolator.LINEAR
          }
        )
      }
      timeline.play()
    })
  }

  def MergeTile(pos: (Int, Int), number: Int): Unit = {
    Platform.runLater({
      val timeline = new Timeline {
        cycleCount = 1
        autoReverse = false

        val startcell = cells.find(c => c.row == pos._1 && c.col == pos._2).get

        var addedCell = new GuiCell(pos._2, pos._1, number)
        addedCell.layoutX = startcell.getLayoutX + grid.getLayoutX
        addedCell.layoutY = startcell.getLayoutY + grid.getLayoutY

        animationPane.children.add(addedCell)

        onFinished = (event: ActionEvent) => {
          animationPane.children.remove(addedCell)
        }

        keyFrames = Seq(
          at(100 ms) {
            addedCell.scaleXProperty() -> 1 tween Interpolator.LINEAR
          },
          at(100 ms) {
            addedCell.scaleYProperty() -> 1 tween Interpolator.LINEAR
          },
          at(275 ms) {
            addedCell.scaleXProperty() -> 1.3 tween Interpolator.LINEAR
          },
          at(275 ms) {
            addedCell.scaleYProperty() -> 1.3 tween Interpolator.LINEAR
          },
          at(450 ms) {
            addedCell.scaleXProperty() -> 1 tween Interpolator.LINEAR
          },
          at(450 ms) {
            addedCell.scaleYProperty() -> 1 tween Interpolator.LINEAR
          }
        )
      }
      timeline.play()
    })
  }

  def TileAdded(pos: (Int, Int), number: Int): Unit = {
    Platform.runLater({
      val timeline = new Timeline {
        cycleCount = 1
        autoReverse = false

        val startcell = cells.find(c => c.row == pos._1 && c.col == pos._2).get

        var addedCell = new GuiCell(pos._2, pos._1, number)
        addedCell.layoutX = startcell.getLayoutX + grid.getLayoutX
        addedCell.layoutY = startcell.getLayoutY + grid.getLayoutY

        animationPane.children.add(addedCell)

        onFinished = (event: ActionEvent) => {
          animationPane.children.remove(addedCell)
        }

        keyFrames = Seq(
          at(175 ms) {
            addedCell.scaleXProperty() -> 1.3 tween Interpolator.LINEAR
          },
          at(175 ms) {
            addedCell.scaleYProperty() -> 1.3 tween Interpolator.LINEAR
          },
          at(350 ms) {
            addedCell.scaleXProperty() -> 1 tween Interpolator.LINEAR
          },
          at(350 ms) {
            addedCell.scaleYProperty() -> 1 tween Interpolator.LINEAR
          }
        )
      }
      timeline.play()
    })
  }

  def SlideTile(start: (Int, Int), end: (Int, Int)): Unit = {
    if (start._1 == end._1 && start._2 == end._2) return
    Platform.runLater({
      val timeline = new Timeline {
        cycleCount = 1
        autoReverse = false

        val startcell = cells.find(c => c.row == start._1 && c.col == start._2).get

        val endcell = cells.find(c => c.row == end._1 && c.col == end._2).get

        var slideCell = new GuiCell(start._2, start._1, if (startcell.text.value == "") 0 else startcell.text.value.toInt)
        slideCell.layoutX = startcell.getLayoutX + grid.getLayoutX
        slideCell.layoutY = startcell.getLayoutY + grid.getLayoutY

        var hideCell = new GuiCell(end._2, end._1, if (endcell.text.value == "") 0 else endcell.text.value.toInt)
        hideCell.layoutX = endcell.getLayoutX + grid.getLayoutX
        hideCell.layoutY = endcell.getLayoutY + grid.getLayoutY

        animationPane.children.add(hideCell)
        animationPane.children.add(slideCell)

        onFinished = (event: ActionEvent) => {
          animationPane.children.remove(hideCell)
          animationPane.children.remove(slideCell)
        }

        keyFrames = Seq(
          if (start._1 == end._1) {
            at(100 ms) {
              slideCell.layoutYProperty -> (slideCell.getLayoutY + endcell.getLayoutY - startcell.getLayoutY) tween Interpolator.LINEAR
            }
          } else {
            at(100 ms) {
              slideCell.layoutXProperty -> (slideCell.getLayoutX + endcell.getLayoutX - startcell.getLayoutX) tween Interpolator.LINEAR
            }
          }
        )
      }
      timeline.play()
    })
  }

  def gameOver(): Unit = {
    gameOverScreen.opacity = 0.5
    gameOverScreenText.opacity = 1
  }

  def newGame(size: Int): Unit = {
    gameOverScreen.opacity = 0
    gameOverScreenText.opacity = 0
    Platform.runLater(score.text = "0")
    grid.redraw(size)
    gameOverScreen.prefWidth = (size * 100) + size - 1
    gameOverScreen.prefHeight = (size * 100) + size - 1
    gameOverScreen.maxWidth = gameOverScreen.prefWidth.value
    gameOverScreen.maxHeight = gameOverScreen.prefHeight.value
  }

  val grid = new GridPane {
    hgap = 1
    vgap = 1
    alignment = Pos.Center

    onKeyPressed = (k: KeyEvent) => k.code match {
      case KeyCode.W => controllers.turnController.up()
      case KeyCode.A => controllers.turnController.left()
      case KeyCode.S => controllers.turnController.down()
      case KeyCode.D => controllers.turnController.right()
      case _ =>
    }

    for (col <- 0 until controllers.turnController.startSize; row <- 0 until controllers.turnController.startSize) {
      val guiCell = new GuiCell(row, col)
      add(guiCell, col, row)
      cells += guiCell
    }

    def redraw(size: Int): Unit = {
      Platform.runLater(children.clear())
      cells = cells.empty
      for (col <- 0 until size; row <- 0 until size) {
        val guiCell = new GuiCell(row, col)
        Platform.runLater(add(guiCell, col, row))
        cells += guiCell
        Platform.runLater(guiCell.redraw())
      }
    }
  }

  var score = new Label {
    text = "0"
    styleClass = List("textFont")
  }

  var bottomAnimationBox = new StackPane {
    styleClass = List("container")
    children = new FlowPane {
      styleClass = List("score", "container", "border", "defaultGrey")
      children = score
    }
  }


  var gameOverScreen = new Region {
    prefWidth = (controllers.turnController.startSize * 100) + controllers.turnController.startSize - 1
    prefHeight = (controllers.turnController.startSize * 100) + controllers.turnController.startSize - 1
    maxWidth = prefWidth.value
    maxHeight = prefHeight.value
    opacity = 0
    styleClass = List("gameOverScreen")
  }

  var gameOverScreenText = new Label {
    text = "GameOver"
    opacity = 0
    styleClass = List("textFont")
  }

  var animationPane = new Pane {
    children = new StackPane {
      padding = Insets(10)
      children = List(new Rectangle {
        fill = Color.White
        width <==> gameOverScreen.prefWidth
        height <==> gameOverScreen.prefHeight
      }, grid, gameOverScreen, gameOverScreenText)
    }
  }

  var rootScene = new FlowPane {
    stylesheets += Play.classloader.getResource("resources/gui_main.css").toExternalForm
    alignment = Pos.Center
    val image = Play.classloader.getResource("resources/gui_bg_pattern.gif").toExternalForm

    style = "-fx-background-image: url('" + image + "'); -fx-background-repeat: repeat;"
    children = new BorderPane {
      top = new HBox {
        styleClass = List("container")

        val fieldSize = new TextField {
          text = "4"
          styleClass = List("fieldSizeTextField", "container", "textFont", "defaultGrey")
        }

        children = List(new Label {
          text = "2048"
          styleClass = List("gameTitle", "container", "textFont", "border", "defaultGrey")
        }, new HBox {
          styleClass = List("newGameButton", "border", "defaultGrey")
          spacing = 8
          onMouseClicked = (k: MouseEvent) => {
            def toInt(s: String): Option[Int] = {
              try {
                Some(s.toInt)
              } catch {
                case e: Exception => None
              }
            }
            val option = toInt(fieldSize.text.value)
            if (option.isDefined) controllers.turnController.newGame(option.get)
          }

          onMouseEntered = (k: MouseEvent) => {
            styleClass = List("newGameButton", "border", "defaultRed")
          }

          onMouseExited = (k: MouseEvent) => {
            styleClass = List("newGameButton", "border", "defaultGrey")
          }

          children = List(new Label {
            text = "new game"
            styleClass = List("textFont")
          }, fieldSize)
        })
      }

      center = animationPane

      bottom = bottomAnimationBox
    }

    onMouseClicked = (k: MouseEvent) => {
      grid.requestFocus()
    }
  }

  stage = new PrimaryStage {
    title = "2048"
    scene = new Scene {
      root = rootScene
    }
  }
  grid.requestFocus()


  class GuiCell(var col: Int, var row: Int, var number: Int) extends Label {
    def this(col: Int, row: Int) = this(col, row, -1)

    text = ""
    prefHeight = 100
    prefWidth = 100
    styleClass = List("defaultGrey", "container", "textFont")
    redraw()

    def redraw(): Unit = {
      var checkThisNumber = number
      Platform.runLater(text = if (number == 0) "" else number.toString)
      if (number == -1) {
        val modelcell = controllers.turnController.field.get(row, col)
        Platform.runLater(text = "")
        if (!modelcell.isEmpty) Platform.runLater(text = modelcell.number.toString)
        checkThisNumber = modelcell.number
      }

      var color = "#DDDDDD"
      checkThisNumber match {
        case 0 => color = "#DDDDDD"
        case 2 => color = "#DFCFCF"
        case 4 => color = "#E1C1C1"
        case 8 => color = "#E3B4B4"
        case 16 => color = "#E6A6A6"
        case 32 => color = "#E89898"
        case 64 => color = "#EA8A8A"
        case 128 => color = "#EC7C7C"
        case 256 => color = "#EE6F6F"
        case 512 => color = "#F06161"
        case 1024 => color = "#F25353"
        case 2048 => color = "#F44545"
      }
      style = "-fx-background-color: " + color + ";"
    }
  }

}
