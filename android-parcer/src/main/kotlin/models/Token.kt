package models

data class Token(
    val name: List<String>,
    val value: Value,
    val description: String?,
) {
    sealed class Value {
        data class Text(val text: String) : Value()
        data class Dp(val dp: Int) : Value()
        data class Sp(val sp: Int) : Value()
        data class Floating(val floating: Float) : Value()
        data class Percent(val percent: Int) : Value()
        data class Color(val color: String) : Value()
        data class Link(val link: List<String>) : Value()
        data class Typography(
            val fontFamily: Value,
            val fontWeight: Value,
            val lineHeight: Value,
            val fontSize: Value,
            val letterSpacing: Value,
            val paragraphSpacing: Value,
            val textCase: Value,
            val textDecoration: Value,
        ) : Value()

        //TODO
        object Gradient : Value()

        //TODO
        object BoxShadow : Value()
    }
}