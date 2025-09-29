package www

import com.raquo.laminar.api.L.*

case class Button(color: Button.ColorMod) {
  val colorobj = color(Button.Color)
  
  def apply() = {
    println(colorobj)
    button("Button")
  }
}

object Button {
  enum Color {
    case Red, Green, Blue
  }
  type ColorMod = Color.type => Color
  
}