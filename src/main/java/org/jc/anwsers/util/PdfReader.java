package org.jc.anwsers.util;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * @since 2020年9月27日
 */
public class PdfReader {
	private String path;

	public PdfReader(String filePath) {
		path = filePath;
	}

	public String readAll() throws IOException {
		// Loading an existing document
		File file = new File(path);
		PDDocument document = PDDocument.load(file);
		// Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();
		pdfStripper.setSortByPosition(true);
		// Retrieving text from PDF document
		String text = pdfStripper.getText(document);

		// Closing the document
		document.close();
		return text;
	}

	public String[] readAllLines() throws IOException {
		String content = readAll();
		return content.split("\r\n?");
	}

	public String getPath() {
		return path;
	}
}
