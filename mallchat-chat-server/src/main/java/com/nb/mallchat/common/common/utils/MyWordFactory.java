package com.nb.mallchat.common.common.utils;

import com.nb.mallchat.common.common.dao.SensitiveWordDao;
import com.nb.mallchat.common.common.domain.entity.SensitiveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyWordFactory implements IWordFactory {
	@Autowired
	private SensitiveWordDao sensitiveWordDao;

	@Override
	public List<String> getWordList() {
		return sensitiveWordDao.list()
				.stream()
				.map(SensitiveWord::getWord)
				.collect(Collectors.toList());
	}
}
