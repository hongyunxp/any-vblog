package com.debugi.app.anyvblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

public class TextUtils {
	private static final String TAG = "TextUtils";

	public static SpannableStringBuilder parse(String content, Context context) {
		String text = Html.fromHtml(content).toString();
		SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
		Pattern facePattern = Pattern.compile("/\\w{1,3}");
		Pattern topicPattern = Pattern.compile("#(\\S[^#]){1,}#");
		Pattern atPattern = Pattern.compile("@\\w{1,}");
		ForegroundColorSpan span = null;
		ImageSpan imageSpan = null;
		Matcher matcher = topicPattern.matcher(spannableString);
		while(matcher.find()) {
			Config.debug(TAG, "text=" + matcher.group());
			span = new ForegroundColorSpan(Color.BLUE);
			spannableString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		matcher = atPattern.matcher(spannableString);
		while(matcher.find()) {
			Config.debug(TAG, "text=" + matcher.group());
			span = new ForegroundColorSpan(Color.BLUE);
			spannableString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		matcher = facePattern.matcher(spannableString);
		boolean ex = false;
		while (matcher.find()) {
			Config.debug(TAG, "group=" + matcher.group());
			int rid = 0;
			if (matcher.group().length() == 4) {
				rid = Face.getResourceId(matcher.group().substring(1));
				if (rid == 0) {
					Config.debug(TAG, "other matcher " + matcher.group().substring(1,3));
					rid = Face.getResourceId(matcher.group().substring(1,3));
					ex = true;
				}
			} else {
				rid = Face.getResourceId(matcher.group().substring(1));
			}
			Drawable d = null;
			if (rid != 0) {
				d = context.getResources().getDrawable(rid);
			}
			if (d != null) {
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				imageSpan = new ImageSpan(d);
			}
			spannableString.setSpan(imageSpan, matcher.start(), ex?matcher.end() - 1:matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}
}
