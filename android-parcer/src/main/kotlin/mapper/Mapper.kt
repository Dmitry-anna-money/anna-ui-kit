package mapper

import models.Token
import models.Token.Value.*
import models.Types

fun mapType(type: String) = Types.values().find { it.type == type }

fun mapToken(value: Any, fontSize: Boolean = false, letterSpacing: Boolean = false): Token.Value {
    if (value is String)
        return when {
            value.contains("[{$}]".toRegex()) -> Link(value.asPath())
            value.contains("linear-gradient") -> Gradient
            value.contains("dp|px".toRegex()) || value.all { it.isDigit() } -> value.filter { it.isDigit() }.toInt().let {
                when {
                    fontSize -> Sp(it)
                    letterSpacing -> Floating(it.toFloat())
                    else -> Dp(it)
                }
            }
            value.contains("sp") -> Sp(value.filter { it.isDigit() }.toInt())
            value.contains("%") -> Percent(value.filter { it.isDigit() }.toInt())
            value.contains(".") -> Floating(value.filter { it.isDigit() }.toFloat())
            value.contains("#") -> Color(value)
            else -> Text(value)
        }
    if (value is Map<*, *>)
        when {
            value.contains("type") -> return BoxShadow
            value.contains("fontFamily") -> return Typography(
                fontFamily = mapToken(value["fontFamily"] as String),
                fontWeight = mapToken(value["fontWeight"] as String),
                lineHeight = mapToken(value["lineHeight"] as String),
                fontSize = mapToken(value["fontSize"] as String),
                letterSpacing = mapToken(value["letterSpacing"] as String),
                textCase = mapToken(value["textCase"] as String),
                textDecoration = mapToken(value["textDecoration"] as String),
            )
        }
    error("cannot get value from $value")
}

private fun String.asPath() = replace("[{$}]".toRegex(), "").split("[_.]".toRegex())