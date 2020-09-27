package org.jc.anwsers.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * @since 2020年9月27日
 */
@Data
public class Question {
	public Question() {
		this.options = new ArrayList<>();
		this.wrongAnswers = new HashSet<>();
	}

	String question;
	String answer;
	Set<String> wrongAnswers;
	List<String> options;
	boolean isRight;

	/**
	 * @param line
	 */
	public void addOption(String e) {
		this.options.add(e);
	}

	/**
	 * @param a
	 */
	public void addWrongAnswer(String a) {
		this.wrongAnswers.add(a);
	}

	@Override
	public String toString() {
		return question + "\r\n    " + StringUtils.join(options, "\r\n    ") + "\r\n"
				+ (isRight ? ("正确答案：" + answer) : ("错误答案：" + wrongAnswers));
	}
}
