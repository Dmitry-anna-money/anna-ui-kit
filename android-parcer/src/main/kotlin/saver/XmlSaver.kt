package saver

import models.Token
import models.Types
import java.io.File

class XmlSaver {
    enum class XmlAttr {
        Color,
        Dimen,
        TextStyle,
        Attr,
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
                    val xml = when (entry.key) {
                        Types.BorderRadius -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.BorderWidth -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.BoxShadow -> ""
                        Types.Color -> XmlAttr.Color.createXmlAttr(token)
                        Types.FontFamily -> XmlAttr.Attr.createXmlAttr(token)
                        Types.FontSize -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.FontWeight -> XmlAttr.Attr.createXmlAttr(token)
                        Types.LetterSpacing -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.LineHeight -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.Opacity -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.Other -> XmlAttr.Attr.createXmlAttr(token)
                        Types.ParagraphSpacing -> XmlAttr.Attr.createXmlAttr(token)
                        Types.Size -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.Spacing -> XmlAttr.Dimen.createXmlAttr(token)
                        Types.TextCase -> XmlAttr.Attr.createXmlAttr(token)
                        Types.TextDecoration -> XmlAttr.Attr.createXmlAttr(token)
                        Types.Typography -> XmlAttr.TextStyle.createXmlAttr(token)
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
            XmlAttr.Attr -> createAttr(token)
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

    private fun createAttr(token: Token): String {
        val name = token.name.parseName
        val value = token.value.parseValue("@attr/")
        val description = token.description

        return "\t<attr name=\"$name\">$value</attr> <!--$description-->"
    }

    private val List<String>.parseName get() = drop(1).snakeCase
    private val List<String>.parseLink get() = snakeCase
    private val List<String>.snakeCase get() = joinToString("_")
    private fun Token.Value.parseValue(linkPrefix: String): String = when (this) {
        is Token.Value.Color -> color
        is Token.Value.Dp -> "${dp}dp"
        is Token.Value.Sp -> "${sp}sp"
        is Token.Value.Floating -> "${floating}f"
        is Token.Value.Percent -> "${percent}%"
        is Token.Value.Text -> text
        is Token.Value.Link -> linkPrefix + link.parseLink
        is Token.Value.Typography -> "\n" +
                "\t\t<item name=\"android:fontFamily\">${fontFamily.parseValue("@string/")}</item>\n" +
                "\t\t<item name=\"android:fontWeight\">${fontWeight.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:lineHeight\">${lineHeight.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:fontSize\">${fontSize.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:letterSpacing\">${letterSpacing.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:paragraphSpacing\">${paragraphSpacing.parseValue("@dimen/")}</item>\n" +
                "\t\t<item name=\"android:textCase\">${textCase.parseValue("@attr/")}</item>\n" +
                "\t\t<item name=\"android:textDecoration\">${textDecoration.parseValue("@attr/")}</item>\n"
        //TODO
        Token.Value.BoxShadow -> ""
        //TODO
        Token.Value.Gradient -> ""
    }
}