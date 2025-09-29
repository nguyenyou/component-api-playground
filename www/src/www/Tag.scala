package www

import com.raquo.laminar.api.L.*

  
case class Tag(color: Tag.TagMod) {

  def apply() = {
    div("Tag")
  }
}


object Tag {
  sealed trait Color

  object Color {
    case object Red extends Color

    case object Green extends Color

    case object Blue extends Color
  }

  type TagMod = Color.type => Color

}