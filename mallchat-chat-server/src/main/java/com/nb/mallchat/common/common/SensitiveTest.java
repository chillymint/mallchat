package com.nb.mallchat.common.common;

import com.nb.mallchat.common.common.utils.ACFilter;
import com.nb.mallchat.common.common.utils.DFAFilter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 */
public class SensitiveTest {
	@Test
	public void DFA() {
		List<String> sensitiveList = Arrays.asList("abcd", "abcbba", "adabca");
		DFAFilter instance = DFAFilter.getInstance();
		instance.loadWord(sensitiveList);
		System.out.println(instance.hasSensitiveWord("adabcd"));
	}

	@Test
	public void AC() {
		List<String> sensitiveList = Arrays.asList("abcd", "abcbba", "adabca");
		ACFilter instance = new ACFilter();
		instance.loadWord(sensitiveList);
		instance.hasSensitiveWord("adabcd");
	}

	@Test
	public void DFAMulti() {
		List<String> sensitiveList = Arrays.asList("白痴", "你是白痴", "白痴吗");
		DFAFilter instance = DFAFilter.getInstance();
		instance.loadWord(sensitiveList);
		System.out.println(instance.filter("你是白痴吗"));
	}

	@Test
	public void ACMulti() {
		List<String> sensitiveList = Arrays.asList("白痴", "你是白痴", "白痴吗");
		ACFilter instance = new ACFilter();
		instance.loadWord(sensitiveList);
		System.out.println(instance.filter("你是白痴吗"));
	}
}
