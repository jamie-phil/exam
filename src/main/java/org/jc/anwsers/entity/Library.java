package org.jc.anwsers.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2020年9月27日
 */
public class Library extends ArrayList<Question> {
	private static final long serialVersionUID = -6915758473716017563L;

	public List<Question> find(String qw) {
		List<Question> list = new ArrayList<>();
		for (Question key : this) {
			if (key.getQuestion().contains(qw)) {
				list.add(key);
			}
		}
		return list;
	}

	private List<Integer> indexOf(String key) {
		List<Integer> ret = new ArrayList<>();
		for (int i = 0, l = this.size(); i < l; ++i) {
			if (this.get(i).getQuestion().contains(key)) {
				ret.add(i);
			}
		}
		return ret;
	}

	/**
	 * @param qs
	 */
	public void add(List<Question> qs) {
		for (Question q : qs) {
			String key = q.getQuestion();
			List<Integer> index = this.indexOf(key);
			if (!index.isEmpty()) {
				boolean exist = false;
				Question last = null;
				String opt0 = q.getOptions().get(0);
				opt0 = opt0.substring(2);
				int hit = -1;
				outter: for (int i : index) {
					last = this.get(i);
					for (String opt : last.getOptions()) {
						if (opt.contains(opt0)) {
							exist = true;
							hit = i;
							break outter;
						}
					}
				}
				if (exist) {
					if (last.isRight) {
						// 已有正确答案
						continue;
					} else if (q.isRight) {
						// 现有的答案错误，新试卷答案正确
						this.remove(hit);
						this.add(q);
					} else {
						// 新试卷也不正确
						last.getWrongAnswers().addAll(q.getWrongAnswers());
					}
				} else {
					this.add(q);
				}
			} else {
				this.add(q);
			}
		}
	}
}
