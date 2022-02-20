package cn.youyitech.anyview.system.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author TT 2017年12月7日
 */
@Service
public class DuplicateRemovalUtil {
	// 去除重复的集合元素
	public <T> List<T> notContains(List<T> tList) {
		List<T> tListNew = new ArrayList<>();
		for (T major : tList) {
			if (!tListNew.contains(major)) {
				tListNew.add(major);
			}
		}
		tList = tListNew;
		return tList;
	}
}
