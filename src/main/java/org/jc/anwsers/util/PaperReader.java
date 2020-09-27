package org.jc.anwsers.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jc.anwsers.entity.Question;
import org.jc.anwsers.util.PdfReader;

/**
 * @since 2020年9月27日
 */
public class PaperReader extends PdfReader {
	public PaperReader(String filePath) {
		super(filePath);
	}

	/**
	 * 读取试卷
	 * 
	 * @return
	 */
	public List<Question> read() {
		List<Question> qs = new ArrayList<>();
		try {
			String[] paper = super.readAllLines();
			boolean begin = false;
			Question q = null;
			for (String line : paper) {
				if (line.matches("^\\d{1,2} .*")) {
					// 题目开始.
					// TODO 目前仅支持单行题目的情况，当出现多行题目时再做处理
					begin = true;
					q = new Question();
					Pattern p = Pattern.compile("(\\d{1,2})分/(\\d{1,2})分");
					Matcher m = p.matcher(line);
					if (m.find()) {
						q.setRight(m.group(1).equals(m.group(2)));
					}
					q.setQuestion(
							line.replaceFirst("\\d{1,2}", "").replaceAll(p.pattern(), "").trim());
				} else if (begin && line.matches("^[A-Z]、.*")) {
					q.addOption(line);
				} else if (begin && line.matches("^提交答案.*")) {
					String a = line.substring(5);
					if (q.isRight()) {
						q.setAnswer(a);
					} else {
						q.addWrongAnswer(a);
					}

					qs.add(q);
					begin = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return qs;
	}

	/**
	 * @return
	 */
	public List<Question> readText() {
		List<Question> qs = new ArrayList<>();
		String[] paper = readText(getPath());
		boolean begin = false;
		Question q = null;
		for (String line : paper) {
			if (line.matches("^\\d{1,2} .*")) {
				// 题目开始.
				// TODO 目前仅支持单行题目的情况，当出现多行题目时再做处理
				begin = true;
				q = new Question();
				q.setRight(true);
				q.setQuestion(line.replaceFirst("\\d{1,2}", "").trim());
			} else if (begin && line.matches("^[A-Z]、.*")) {
				q.addOption(line);
			} else if (begin && line.matches("^正确答案.*")) {
				String a = line.substring(5);
				if (q.isRight()) {
					q.setAnswer(a);
				} else {
					q.addWrongAnswer(a);
				}

				qs.add(q);
				begin = false;
			}
		}
		return qs;
	}

	static String[] readText(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> sbf = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sbf.add(tempStr);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sbf.toArray(new String[0]);
	}
}
