package com.debugi.app.anyvblog.utils;

import org.junit.Test;

import android.test.AndroidTestCase;


public class ConfigTest extends AndroidTestCase {
	@Test
	public void testGetCachePath() {
		System.out.println(Config.getCachePath(getContext()));
	}
}
