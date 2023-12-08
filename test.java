import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.StringReader;

public class test {
    public static void main(String[] args) {
        String xmlString = "<root><title>Main Title</title><content>Some content<subsection>Subsection</subsection>More content</content></root>";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(inputSource);

            // Extract text content
            String title = extractTitle(document);
            String content = extractContent(document);

            // Print the results
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractTitle(Document document) {
        NodeList titleNodes = document.getElementsByTagName("title");
        if (titleNodes.getLength() > 0) {
            return titleNodes.item(0).getTextContent();
        }
        return null;
    }

    private static String extractContent(Document document) {
        StringBuilder contentBuilder = new StringBuilder();
        NodeList nodes = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if ("section".equals(node.getNodeName())) {
                    // Ignore sections and their content
                    i += countDescendantNodes(node);
                }
            } else if (node.getNodeType() == Node.TEXT_NODE) {
                contentBuilder.append(node.getTextContent());
            }
        }

        return contentBuilder.toString();
    }

    private static int countDescendantNodes(Node node) {
        int count = 0;
        NodeList descendants = node.getChildNodes();

        for (int i = 0; i < descendants.getLength(); i++) {
            Node descendant = descendants.item(i);

            if (descendant.getNodeType() == Node.ELEMENT_NODE) {
                count += countDescendantNodes(descendant) + 1;
            } else {
                count++;
            }
        }

        return count;
    }
}