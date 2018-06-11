package com.agileEngine.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SmartXMLAnalyzer {

	private static String CHARSET_NAME = "utf8";

	public static void main(String[] args)  {

		String firstResourcePath = args[0];
		String secondResourcePath = args[1];
		String basicButtonCssQuery = args[2];
		
		String similarButtonCssQuery = "a[onclick], [href], [class], [title], [rel]";

		Optional<Elements> elementsOpt = findElementsByQuery(new File(
				firstResourcePath), basicButtonCssQuery);
		Optional<Elements> searchElementsOpt = findElementsByQuery(new File(
				secondResourcePath), similarButtonCssQuery);

		List<String> basicAttrs = new ArrayList<String>();
		if (elementsOpt.isPresent()) {
			for (Element element : elementsOpt.get()) {
				String foundElement = formatAttrs(element);
				String[] attrsArray = foundElement.split(",");
				List<String> attrsFromElem = new ArrayList<String>(
						Arrays.asList(attrsArray));
				attrsFromElem.remove(0);
				basicAttrs = formatComparisonList(attrsFromElem);
			}
		}

		Element similarButton = null;
		if (searchElementsOpt.isPresent()) {
			for (Element element : searchElementsOpt.get()) {
				String stringifiedAttributesOpt = formatAttrs(element);
				String[] possibleButton = stringifiedAttributesOpt.split(",");
				if (possibleButton.length == 5) {
					List<String> possibleButtonList = new ArrayList<String>(
							Arrays.asList(possibleButton));
					List<String> possibleAttrs = formatComparisonList(possibleButtonList);
					if (basicAttrs.equals(possibleAttrs)) {
						similarButton = element;
						break;
					}
				}
			}
		}

		if (similarButton != null) {
			Elements path = similarButton.parents();
			for (int i = path.size() - 1; i >= 0; i-- ){
				System.out.printf("%s[%s] " + "> ", path.get(i).tagName(), path.get(i).elementSiblingIndex());
			}
			System.out.print("a");
		}
	}

	private static List<String> formatComparisonList(List<String> searchPar) {
		
		List<String> newList = new ArrayList<String>();
		for (String str : searchPar) {
			str = str.substring(0, str.indexOf(" = "));
			newList.add(str.trim());
		}
		return newList;
	}

	private static String formatAttrs(Element elementsOpt) {
		
		return elementsOpt.attributes().asList().stream()
				.map(attr -> attr.getKey() + " = " + attr.getValue())
				.collect(Collectors.joining(", "));
		
	}

	private static Optional<Elements> findElementsByQuery(File htmlFile,
			String cssQuery) {

		try {
			Document doc = Jsoup.parse(htmlFile, "UTF-8");
			return Optional.of(doc.select(cssQuery));
		} catch (IOException e) {
			return Optional.empty();
		}
	}

}
