/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyoninae.halcyon.cacher;

import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class Cacher {
    private final File file;
    private SoftReference<Document> doc;

    public Cacher(File f) {
        this.file = f;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            doc = new SoftReference<>(builder.parse(this.file));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Debugger.log(e);
        }
    }


    /**
     * @param nodeName
     * @return String[]
     */
    public String[] getContent(String nodeName) {
        List<String> content = new ArrayList<>();
        NodeList node = doc.get().getElementsByTagName(nodeName);
        for (int i = 0; i < node.getLength(); i++) {
            Node n = node.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                content.add(e.getTextContent());
            }
        }
        return content.toArray(new String[0]);
    }


    /**
     * @param rootName
     * @param content
     */
    public void build(String rootName, Map<String, String> content)
        throws TransformerException, ParserConfigurationException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement(rootName);
        newDoc.appendChild(root);
        Set<String> keys = content.keySet();
        for (String key : keys) {
            Element child = newDoc.createElement(key);
            child.setTextContent(content.get(key));
            root.appendChild(child);
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
