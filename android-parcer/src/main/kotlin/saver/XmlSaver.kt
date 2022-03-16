package saver

import models.Token
import models.Types
import java.io.File

class XmlSaver {
    enum class XmlAttr {
        Color,
        Dimen,
        TextStyle,
        String,
    }

    fun save(resourceDir: String, tokens: Map<Types, List<Token>>) {
        tokens.forEach { entry ->
            val dir = File("$resourceDir/values")
            val file = File("$resourceDir/values/${entry.key}.xml")
            dir.mkdir()
            file.createNewFile()
            file.bufferedWriter().use { writer ->
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                writer.newLine()
                writer.write("<resources>")
                writer.newLine()
                entry.value.forEach { token ->
                    val xml = when (token.value) {
                        Token.Value.BoxShadow -> ""
                        is Token.Value.Color -> XmlAttr.Color.createXmlAttr(token)
                        is Token.Value.Dp -> XmlAttr.Dimen.createXmlAttr(token)
                        is Token.Value.Floating -> XmlAttr.Dimen.createXmlAttr(token)
                        Token.Value.Gradient -> ""
                        is Token.Value.Link -> ""
                        is Token.Value.Percent -> XmlAttr.Dimen.createXmlAttr(token)
                        is Token.Value.Sp -> XmlAttr.Dimen.createXmlAttr(token)
                        is Token.Value.Text -> XmlAttr.String.createXmlAttr(token)
                        is Token.Value.Typography -> XmlAttr.TextStyle.createXmlAttr(token)
                    }
                    writer.write(xml)
                    writer.newLine()
                }
                writer.write("</resources>")
            }
        }
    }

    private fun XmlAttr.createXmlAttr(token: Token): String {
        return when (this) {
            XmlAttr.Color -> createColor(token)
            XmlAttr.Dimen -> createDimen(token)
            XmlAttr.TextStyle -> createTextStyle(token)
            XmlAttr.String -> createString(token)
        }
    }

    private fun createColor(token: Token): String {
        val name = token.name.parseName
        val value = token.value.parseValue("@color/")
        val description = token.description

        return "\t<color name=\"$name\">$value</color> <!--$description-->"
    }

    private fun createDimen(token: Token): String {
        val name = token.name.parseName
        val value = token.value.parseValue("@dimen/")
        val description = token.description

        return "\t<dimen name=\"$name\">$value</dimen> <!--$description-->"
    }

    private fun createTextStyle(token: Token): String {
        val name = token.name.parseName
        val value = token.value.parseValue("@style/")
        val description = token.description

        return "\t<style name=\"$name\">$value\t</style> <!--$description-->"
    }

    private fun createString(token: Token): String {
        val name = token.name.parseName
        val value = token.value.parseValue("@string/")
        val description = token.description

        return "\t<string name=\"$name\">$value</string> <!--$description-->"
    }

    private val List<String>.parseName get() = drop(1).snakeCase
    private val List<String>.parseLink get() = snakeCase
    private val List<String>.snakeCase get() = joinToString("_").replace(" ", "_")
    private fun Token.Value.parseValue(linkPrefix: String): String = when (this) {
        is Token.Value.Color -> color
        is Token.Value.Dp -> "${dp}dp"
        is Token.Value.Sp -> "${sp}sp"
        is Token.Value.Floating -> "$floating"
        is Token.Value.Percent -> "${percent}%"
        is Token.Value.Text -> text
        is Token.Value.Link -> linkPrefix + link.parseLink
        is Token.Value.Typography -> "\n" +
                "\t\t<item name=\"android:fontFamily\">@font/${(fontFamily as Token.Value.Text).text.replace(" ", "").lowercase()}_${(fontWeight as Token.Value.Text).text.lowercase()}</item>\n" +
                (if (lineHeight is Token.Value.Text && lineHeight.text == "auto") "" else "\t\t<item name=\"lineHeight\">${lineHeight.parseValue("@dimen/")}</item>\n") +
                "\t\t<item name=\"android:textSize\">${fontSize.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:letterSpacing\">${letterSpacing.parseValue("@dimen/")}</item>\n"
        //TODO
        Token.Value.BoxShadow -> ""
        //TODO
        Token.Value.Gradient -> ""
    }
}