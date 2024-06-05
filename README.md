# TuketionParser
TuketionParser: a powerful and flexible HTML parser for Kotlin, providing effective document handling, even with big and complex ones. It's built with support for CSS-like selectors, XPath queries, rendering of HTML templates, and extensibility through plugins.

## Features

- **CSS-like Selectors**: Easily select and manipulate HTML elements using familiar CSS selectors.
- **XPath Support**: Advanced querying with XPath for precise element selection.
- **Template Rendering**: Render HTML templates with dynamic context data.
- **Extensibility**: Enhance functionality through custom plugins.
- **Efficient Parsing**: Optimized for performance with large and complex HTML documents.
- **Ease of Use**: Intuitive API design for quick and easy HTML parsing and manipulation.

## Usage

### Parsing a List of Elements

```kotlin
val parser = TuketionParser()
val html = "<html><body><p>First</p><p>Second</p></body></html>"
val result = parser.parseList(html, "p")
println(result) // Output: [First, Second]
```

### Parsing a Single Element as a String

```kotlin
val html = "<html><body><p>First</p><p>Second</p></body></html>"
val result = parser.parseString(html, "p")
println(result) // Output: First
```

### Parsing an Attribute from an Element

```kotlin
val html = "<html><body><a href='https://example.com'>Link</a></body></html>"
val result = parser.parseAttribute(html, "a", "href")
println(result) // Output: https://example.com
```

### Modifying an Element by Its ID

```kotlin
val html = "<html><body><div id='content'>Old Content</div></body></html>"
val result = parser.modifyElementById(html, "content", "New Content")
println(result) // Output: <html><head></head><body><div id="content">New Content</div></body></html>
```

### Adding an Element as a Child to a Specified Parent Element

```kotlin
val html = "<html><body><div id='container'></div></body></html>"
val result = parser.addElement(html, "#container", "<p>New Paragraph</p>")
println(result) // Output: <html><head></head><body><div id="container"><p>New Paragraph</p></div></body></html>
```

### Removing an Element by Its ID

```kotlin
val html = "<html><body><div id='content'>Content to Remove</div></body></html>"
val result = parser.removeElementById(html, "content")
println(result) // Output: <html><head></head><body></body></html>
```

### Parsing the First Match of an Element as a String

```kotlin
val html = "<html><body><p>First</p><p>Second</p></body></html>"
val result = parser.parseFirstMatch(html, "p")
println(result) // Output: First
```

### Parsing All Matches of Elements as a List

```kotlin
val html = "<html><body><p>First</p><p>Second</p></body></html>"
val result = parser.parseAllMatches(html, "p")
println(result) // Output: [First, Second]
```

### Parsing the Content Between Specified Tags

```kotlin
val html = "<html><body><div>Content</div></body></html>"
val result = parser.parseBetweenTags(html, "div")
println(result) // Output: <div>Content</div>
```

### Parsing Elements Using XPath

```kotlin
val html = "<html><body><div>Content</div></body></html>"
val result = parser.parseByXPath(html, "//div")
result.forEach { println(it.outerHtml()) } // Output: <div>Content</div>
```

### Rendering an HTML Template with Context

```kotlin
val template = "<html><body><h1>{{title}}</h1><p>{{message}}</p></body></html>"
val context = mapOf("title" to "Hello, World!", "message" to "This is a template rendering example.")
val result = parser.renderTemplate(template, context)
println(result) // Output: <html><body><h1>Hello, World!</h1><p>This is a template rendering example.</p></body></html>
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
```
