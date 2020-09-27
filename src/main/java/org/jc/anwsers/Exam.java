package org.jc.anwsers;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.jc.anwsers.entity.Library;
import org.jc.anwsers.entity.Question;
import org.jc.anwsers.util.PaperReader;

/**
 * 使用方法：<br/>
 * 1、创建一个文件夹，如lib/；<br/>
 * 2、将所有导出的考卷pdf放在一个文件夹内；<br/>
 * 3、将错题文件按err.txt格式放在lib/中；<br/>
 * 4、修改第22行文件路径，执行main方法。在console中输入搜索关键词即可。
 * 
 * @since 2020年9月27日
 */
public class Exam {
	public static void main(String... args) {
		Exam exam = new Exam("/lib");/// ←←修改这里！！！
		exam.run();
	}

	Library lib = new Library();

	String path;

	public Exam(String path) {
		this.path = path;
	}

	/**
	 * @param str
	 * @return
	 */
	private String find(String str) {
		List<Question> ret = lib.find(str);
		if (ret == null || ret.isEmpty()) {
			return "没有找到匹配的题目";
		}
		StringBuilder sb = new StringBuilder();
		for (Question q : ret) {
			sb.append("Q: " + q.toString()).append("\r\n\r\n");
		}
		return sb.toString();
	}

	public void run() {
		File dir = new File(path);
		if (dir.isDirectory()) {
			File[] papers = dir.listFiles();
			for (File paper : papers) {
				String name = paper.getName();
				System.out.println(name);
				if (name.endsWith(".pdf")) {
					List<Question> qs = new PaperReader(paper.getAbsolutePath()).read();
					lib.add(qs);
				} else if (name.endsWith(".txt")) {
					List<Question> qs = new PaperReader(paper.getAbsolutePath()).readText();
					lib.add(qs);
				}
			}
		}
		System.out.println("初始化已完成，共加载" + lib.size() + "道题目。");
		System.out.println("输入题目关键字进行搜索，输入q退出。");
		System.out.print(">>");
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			String str = in.nextLine();
			if ("q".equals(str)) {
				break;
			}

			System.out.println(find(str));
			System.out.print(">>");
		}
		in.close();
	}
}
