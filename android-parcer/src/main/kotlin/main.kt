import parser.Parser
import saver.XmlSaver
import java.io.File

fun main(args: Array<String>) {
    val tokenPath = "../tokens/tokens.json"
    val outputDir = "../android/app/src/main/res"
    val parser = Parser()
    val saver = XmlSaver()

    val file = File(tokenPath)
    val reader = file.bufferedReader()
    val tokens = parser.parse(reader)
    saver.save(outputDir, tokens)
}