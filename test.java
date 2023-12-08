import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public class test {

    public static void main(String[] args) throws Exception {
        // Your XML document as a string
        String xmlContent = "<root><person><name>John</name><age>30</age></person><person><name>Jane</name><age>25</age></person></root>";

        // Parse the XML content into a Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(xmlContent));
        Document document = builder.parse(inputSource);

        // Split the document into sub-documents (assuming each person is a sub-document)
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression expression = xPath.compile("/root/person");
        NodeList personNodes = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

        // Process each sub-document
        for (int i = 0; i < personNodes.getLength(); i++) {
            Node personNode = personNodes.item(i);

            // Extract fields using XPath expressions within the sub-document
            String name = extractField(personNode, "name/text()");
            String age = extractField(personNode, "age/text");

            // Do something with the extracted fields
            System.out.println("Person " + (i + 1) + ": Name=" + name + ", Age=" + age);
        }
    }

    private static String extractField(Node contextNode, String xpathExpression) throws Exception {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression expression = xPath.compile(xpathExpression);
        return (String) expression.evaluate(contextNode, XPathConstants.STRING);
    }
}