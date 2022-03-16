package parser

import com.google.gson.Gson
import mapper.mapToken
import mapper.mapType
import models.Token
import models.Types
import java.io.Reader

class Parser {
    private var count = 0
    private val tokens = HashMap<Types, ArrayList<Token>>()

    fun parse(reader: Reader): Map<Types, List<Token>> {
        val map = Gson().fromJson<Map<String, Any>>(reader, Map::class.java)
        parseJson(map)
        println("tokens found: $count")
        println("tokens parsed: ${tokens.values.sumOf { it.size }}")

        replaceLinksWithValues()

//        tokens.forEach {
//            println(it.key)
//            println(it.value.joinToString("\n"))
//        }

        return tokens
    }

    private fun replaceLinksWithValues() {
        println("replacing links with values")
        tokens.values.map { it.filter { it.value is Token.Value.Link } }.sumOf { it.size }
            .let { println("Links count before: $it") }

        val allTokens = tokens.values.flatten()
        tokens.values.forEach {
            it.forEach {
                if (it.value is Token.Value.Link) it.value = replaceLinkWithValue(it.value, allTokens)
                if (it.value is Token.Value.Typography) {
                    it.value = Token.Value.Typography(
                        fontFamily = replaceLinkWithValue((it.value as Token.Value.Typography).fontFamily, allTokens),
                        fontWeight = replaceLinkWithValue((it.value as Token.Value.Typography).fontWeight, allTokens),
                        lineHeight = replaceLinkWithValue((it.value as Token.Value.Typography).lineHeight, allTokens),
                        fontSize = replaceLinkWithValue((it.value as Token.Value.Typography).fontSize, allTokens),
                        letterSpacing = replaceLinkWithValue(
                            (it.value as Token.Value.Typography).letterSpacing,
                            allTokens
                        ),
                        textCase = replaceLinkWithValue((it.value as Token.Value.Typography).textCase, allTokens),
                        textDecoration = replaceLinkWithValue(
                            (it.value as Token.Value.Typography).textDecoration,
                            allTokens
                        ),
                    )
                }
            }
        }

        tokens.values.map { it.filter { it.value is Token.Value.Link } }.sumOf { it.size }
            .let { println("Links count after: $it") }
    }

    private fun replaceLinkWithValue(value: Token.Value, allTokens: List<Token>): Token.Value {
        if (value !is Token.Value.Link) return value
        val foundToken = allTokens.find { it.name.drop(1) == value.link } ?: error("Cannot find token with name: ${value.link}")
        return foundToken.value
    }

    private fun parseJson(json: Map<String, Any>, path: List<String> = emptyList()): Boolean {
        json.forEach {
            val map = it.value as? Map<String, Any> ?: return@forEach

            if (map["type"] == null) {
                parseJson(map, path + it.key)
            } else {
                count++
                parseToken(path + it.key, map)
            }
        }
        return true
    }

    private fun parseToken(path: List<String>, map: Map<String, Any>) {
        val value = map["value"]
        val type = map["type"] as String
        val description = map["description"] as String?

        when (mapType(type)) {
            Types.Color -> parseColor(path, value as String, description)
            Types.Size -> parseSize(path, value as String, description)
            Types.Spacing -> parseSpacing(path, value as String, description)
            Types.BorderRadius -> parseBorderRadius(path, value as String, description)
            Types.BorderWidth -> parseBorderWidth(path, value as String, description)
            Types.BoxShadow -> parseBoxShadow(path, value as Map<String, String>, description)
            Types.Typography -> parseTypography(path, value as Map<String, String>, description)
            Types.Opacity -> parseOpacity(path, value as String, description)
            Types.FontFamily -> parseFontFamily(path, value as String, description)
            Types.FontWeight -> parseFontWeight(path, value as String, description)
            Types.LineHeight -> parseLineHeight(path, value as String, description)
            Types.FontSize -> parseFontSize(path, value as String, description)
            Types.LetterSpacing -> parseLetterSpacing(path, value as String, description)
            Types.ParagraphSpacing -> parseParagraphSpacing(path, value as String, description)
            Types.TextCase -> parseTextCase(path, value as String, description)
            Types.TextDecoration -> parseTextDecoration(path, value as String, description)
            Types.Other -> parseOther(path, value as String, description)
            null -> error("cannot parse $type $value $description")
        }
    }

    private fun parseColor(path: List<String>, value: String, description: String?) {
        addToken(
            Types.Color,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseSize(path: List<String>, value: String, description: String?) {
        addToken(
            Types.Size,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseSpacing(path: List<String>, value: String, description: String?) {
        addToken(
            Types.Spacing,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseBorderRadius(path: List<String>, value: String, description: String?) {
        addToken(
            Types.BorderRadius,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseBorderWidth(path: List<String>, value: String, description: String?) {
        addToken(
            Types.BorderWidth,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseBoxShadow(path: List<String>, value: Map<String, String>, description: String?) {
        addToken(
            Types.BoxShadow,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseTypography(path: List<String>, value: Map<String, String>, description: String?) {
        addToken(
            Types.Typography,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseOpacity(path: List<String>, value: String, description: String?) {
        addToken(
            Types.Opacity,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseFontFamily(path: List<String>, value: String, description: String?) {
        addToken(
            Types.FontFamily,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseFontWeight(path: List<String>, value: String, description: String?) {
        addToken(
            Types.FontWeight,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseLineHeight(path: List<String>, value: String, description: String?) {
        addToken(
            Types.LineHeight,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseFontSize(path: List<String>, value: String, description: String?) {
        addToken(
            Types.FontSize,
            Token(
                path.parseName,
                mapToken(value, true),
                description,
            ),
        )
    }

    private fun parseLetterSpacing(path: List<String>, value: String, description: String?) {
        addToken(
            Types.LetterSpacing,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseParagraphSpacing(path: List<String>, value: String, description: String?) {
        addToken(
            Types.ParagraphSpacing,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseTextCase(path: List<String>, value: String, description: String?) {
        addToken(
            Types.TextCase,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseTextDecoration(path: List<String>, value: String, description: String?) {
        addToken(
            Types.TextDecoration,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun parseOther(path: List<String>, value: String, description: String?) {
        addToken(
            Types.Other,
            Token(
                path.parseName,
                mapToken(value),
                description,
            ),
        )
    }

    private fun addToken(key: Types, value: Token) {
        tokens.computeIfAbsent(key) { ArrayList() }.add(value)
    }

    private val List<String>.parseName get() = map { it.replace("-", "_").replace(":", "").split("_") }.flatten()
}
