package www

import com.raquo.laminar.api.L.*
import scala.compiletime.summonInline
// Generic conversion: apply a function from a singleton type `C` to its singleton value.
// Works for enums, case classes with companions, plain `object`s, etc.
transparent inline given modToValue[C <: Singleton, T]: Conversion[C => T, T] =
  (f: C => T) => f(summonInline[ValueOf[C]].value)
  
case class Tag(color: Tag.TagMod) {
  val obj: Tag.Color = color
  
  def apply() = {
    println(obj)
    div("Tag")
  }
}


object Tag {
  enum Color {
    case Red, Green, Blue
  }
  type TagMod = Color.type => Color

}