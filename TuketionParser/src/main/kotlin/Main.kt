package org.tuketion

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.w3c.dom.NodeList
import java.util.*

class TuketionParser {

    private var parserMyList: ArrayList<String> = ArrayList()
    private var parserMyString: String = "null"
    private val plugins: MutableList<HtmlParserPlugin> = mutableListOf()

    // Function to add plugins
    fun addPlugin(plugin: HtmlParserPlugin) {
        plugins.add(plugin)
    }

    // Function to parse HTML using added plugins
    fun parseWithPlugins(html: String): String {
        var processedHtml = html
        for (plugin in plugins) {
            processedHtml = plugin.process(processedHtml)
        }
        return processedHtml
    }

    // Function to parse a list of elements
    fun parseList(html: String, selector: String, includeOuterHtml: Boolean = false): ArrayList<String> {
        parserMyList.clear()
        try {
            val doc: Document = Jsoup.parse(html)
            val elements: Elements = doc.select(selector)
            for (element in elements) {
                parserMyList.add(if (includeOuterHtml) element.outerHtml() else element.text())
            }
            if (parserMyList.isEmpty()) {
                parserMyList.add("Error: No matches found")
            }
        } catch (e: Exception) {
            parserMyList.add("Error: ${e.message}")
        }
        return parserMyList
    }

    // Function to parse a single element as a string
    fun parseString(html: String, selector: String, includeOuterHtml: Boolean = false): String {
        parserMyString = "null"
        try {
            val doc: Document = Jsoup.parse(html)
            val element: Element? = doc.selectFirst(selector)
            parserMyString = if (element != null) {
                if (includeOuterHtml) element.outerHtml() else element.text()
            } else {
                "Error: No match found"
            }
        } catch (e: Exception) {
            parserMyString = "Error: ${e.message}"
        }
        return parserMyString
    }

    // Function to parse an attribute from an element
    fun parseAttribute(html: String, selector: String, attribute: String): String {
        parserMyString = "null"
        try {
            val doc: Document = Jsoup.parse(html)
            val element: Element? = doc.selectFirst(selector)
            parserMyString = element?.attr(attribute) ?: "Error: No match found"
        } catch (e: Exception) {
            parserMyString = "Error: ${e.message}"
        }
        return parserMyString
    }

    // Function to modify an element by its ID
    fun modifyElementById(html: String, id: String, newContent: String): String {
        return try {
            val doc: Document = Jsoup.parse(html)
            val element: Element? = doc.getElementById(id)
            element?.html(newContent)
            doc.html()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Function to add an element as a child to a specified parent element
    fun addElement(html: String, selector: String, newElement: String): String {
        return try {
            val doc: Document = Jsoup.parse(html)
            val parent: Element? = doc.selectFirst(selector)
            parent?.append(newElement)
            doc.html()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Function to remove an element by its ID
    fun removeElementById(html: String, id: String): String {
        return try {
            val doc: Document = Jsoup.parse(html)
            val element: Element? = doc.getElementById(id)
            element?.remove()
            doc.html()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Function to parse the first match of an element as a string
    fun parseFirstMatch(html: String, selector: String): String {
        return parseString(html, selector)
    }

    // Function to parse all matches of elements as a list
    fun parseAllMatches(html: String, selector: String): ArrayList<String> {
        return parseList(html, selector)
    }

    // Function to parse the content between specified tags
    fun parseBetweenTags(html: String, tagName: String): String {
        val selector = tagName
        return parseString(html, selector, includeOuterHtml = true)
    }

    // Function to parse elements using XPath
    fun parseByXPath(html: String, xPathExpression: String): List<Element> {
        return try {
            val doc: Document = Jsoup.parse(html)
            val xPath: XPath = XPathFactory.newInstance().newXPath()
            val elements: NodeList = xPath.evaluate(xPathExpression, doc, XPathConstants.NODESET) as NodeList
            val result = mutableListOf<Element>()
            for (i in 0 until elements.length) {
                result.add(Element(elements.item(i).toString()))
            }
            result
        } catch (e: Exception) {
            listOf(Element("Error: ${e.message}"))
        }
    }

    // Function to render an HTML template with context
    fun renderTemplate(htmlTemplate: String, context: Map<String, String>): String {
        var rendered = htmlTemplate
        for ((key, value) in context) {
            rendered = rendered.replace("{{${key}}}", value)
        }
        return rendered
    }
}

// Plugin interface for custom parsing rules
interface HtmlParserPlugin {
    fun process(html: String): String
}
