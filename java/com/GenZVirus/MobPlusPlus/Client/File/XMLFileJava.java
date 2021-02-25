package com.GenZVirus.MobPlusPlus.Client.File;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.GenZVirus.MobPlusPlus.Client.GUI.Settings;
import com.GenZVirus.MobPlusPlus.Client.Render.OverlayRenderer;

public class XMLFileJava {

	private static int default_RED = 255;
	private static int default_GREEN = 0;
	private static int default_BLUE = 0;

	public static String default_xmlFilePath = "mobplusplus/settings.xml";

	public XMLFileJava() {
		try {

			File file = new File(default_xmlFilePath);
			File parent = file.getParentFile();
			if (!parent.exists() && !parent.mkdirs()) { throw new IllegalStateException("Couldn't create dir: " + parent); }

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// Root
			Element root = document.createElement("Root");
			document.appendChild(root);

			// RED
			Element red = document.createElement("RED");
			red.appendChild(document.createTextNode(Integer.toString(default_RED)));
			root.appendChild(red);

			// GREEN
			Element green = document.createElement("GREEN");
			green.appendChild(document.createTextNode(Integer.toString(default_GREEN)));
			root.appendChild(green);

			// BLUE
			Element blue = document.createElement("BLUE");
			blue.appendChild(document.createTextNode(Integer.toString(default_BLUE)));
			root.appendChild(blue);

			// Border
			Element border = document.createElement("Border");
			border.appendChild(document.createTextNode(Boolean.toString(true)));
			root.appendChild(border);

			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(default_xmlFilePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static void editElement(String elementTag, String elementTextContent) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(default_xmlFilePath);

			// Get root element

			checkFileElement(document, default_xmlFilePath, elementTag);
			Node element = document.getElementsByTagName(elementTag).item(0);
			element.setTextContent(elementTextContent);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(default_xmlFilePath));
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public static String readElement(String elementTag) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(default_xmlFilePath);
			checkFileElement(document, default_xmlFilePath, elementTag);
			Node element = document.getElementsByTagName(elementTag).item(0);
			return element.getTextContent();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		return "0";
	}

	public static void checkFileElement(Document document, String xmlFilePath, String elementTag) {
		Node element = document.getElementsByTagName(elementTag).item(0);
		if (element == null) {
			try {
				element = document.createElement(elementTag);
				element.appendChild(document.createTextNode(Integer.toString(0)));
				Node root = document.getElementsByTagName("Root").item(0);
				root.appendChild(element);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource domSource = new DOMSource(document);
				StreamResult streamResult = new StreamResult(new File(xmlFilePath));
				transformer.transform(domSource, streamResult);
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			resetElement(elementTag);
		}
	}

	public static void checkFileAndMake() {
		File file = new File(default_xmlFilePath);
		boolean found = file.exists();

		if (!found) {
			new XMLFileJava();
		}

	}

	public static void load() {
		OverlayRenderer.RED = ((float) Integer.parseInt(XMLFileJava.readElement("RED"))) / 255;
		OverlayRenderer.GREEN = ((float) Integer.parseInt(XMLFileJava.readElement("GREEN"))) / 255;
		OverlayRenderer.BLUE = ((float) Integer.parseInt(XMLFileJava.readElement("BLUE"))) / 255;
		OverlayRenderer.BORDER = Boolean.parseBoolean(XMLFileJava.readElement("Border"));
	}

	public static void save() {
		XMLFileJava.editElement("RED", Integer.toString((int) (OverlayRenderer.RED * 255)));
		XMLFileJava.editElement("GREEN", Integer.toString((int) (OverlayRenderer.GREEN * 255)));
		XMLFileJava.editElement("BLUE", Integer.toString((int) (OverlayRenderer.BLUE * 255)));
		XMLFileJava.editElement("Border", Boolean.toString(OverlayRenderer.BORDER));
	}

	public static void resetToDefault() {
		XMLFileJava.editElement("RED", Integer.toString(default_RED));
		XMLFileJava.editElement("GREEN", Integer.toString(default_GREEN));
		XMLFileJava.editElement("BLUE", Integer.toString(default_BLUE));
		XMLFileJava.editElement("Border", Boolean.toString(true));
		OverlayRenderer.RED = default_RED / 255;
		OverlayRenderer.GREEN = default_GREEN / 255;
		OverlayRenderer.BLUE = default_BLUE / 255;
		Settings.instance.RED.setDefault(default_RED);
		Settings.instance.GREEN.setDefault(default_GREEN);
		Settings.instance.BLUE.setDefault(default_BLUE);
	}

	private static void resetElement(String elementTag) {
		if (elementTag.contentEquals("RED")) {
			XMLFileJava.editElement("RED", Integer.toString(default_RED));
		} else if (elementTag.contentEquals("GREEN")) {
			XMLFileJava.editElement("GREEN", Integer.toString(default_GREEN));
		} else if (elementTag.contentEquals("BLUE")) {
			XMLFileJava.editElement("BLUE", Integer.toString(default_BLUE));
		} else if (elementTag.contentEquals("Border")) {
			XMLFileJava.editElement("Border", Boolean.toString(true));
		}
	}

}
