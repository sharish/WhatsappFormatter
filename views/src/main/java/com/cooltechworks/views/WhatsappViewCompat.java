/**
 *
 * Copyright 2016 Harish Sridharan

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cooltechworks.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static com.cooltechworks.views.WhatsappUtil.BOLD_FLAG;
import static com.cooltechworks.views.WhatsappUtil.INVALID_INDEX;
import static com.cooltechworks.views.WhatsappUtil.ITALIC_FLAG;
import static com.cooltechworks.views.WhatsappUtil.STRIKE_FLAG;

/**
 * Created by Harish on 12/05/16.
 */
public class WhatsappViewCompat {


    /**
     * WhatsappViewCompat for EditText.
     * @param editText - related EditText view on which the formatting should be applied on.
     * @param watchers - array of watchers that are watching on the text view content change.
     */
    public static void applyFormatting(final EditText editText, final TextWatcher... watchers) {

        TextWatcher mEditTextWatcher = new TextWatcher() {

            final TextWatcher mainWatcher = this;
            Handler handler = new Handler();

            private Runnable formatRunnable = new Runnable() {
                @Override
                public void run() {
                    format(editText, mainWatcher, watchers);
                }
            };

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sendBeforeTextChanged(watchers, s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendOnTextChanged(watchers, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

                handler.removeCallbacks(formatRunnable);
                handler.postDelayed(formatRunnable, 220);

            }
        };

        String text = editText.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            CharSequence formatted = extractFlagsForEditText(text);
            editText.setText(formatted);
        }
        editText.addTextChangedListener(mEditTextWatcher);

    }

    /**
     * Performs formatting.
     */
    private static void format(EditText editText, TextWatcher mainWatcher, TextWatcher[] otherWatchers ) {

        Editable text = editText.getText();
        CharSequence formatted = WhatsappViewCompat.extractFlagsForEditText(text);
        removeTextChangedListener(editText,mainWatcher);
        int selectionEnd = editText.getSelectionEnd();
        int selectionStart = editText.getSelectionStart();
        editText.setText(formatted);
        editText.setSelection(selectionStart, selectionEnd);
        Editable formattedEditableText = editText.getText();
        sendAfterTextChanged(otherWatchers, formattedEditableText);
        addTextChangedListener(editText, mainWatcher);
    }

    /**
     * WhatsappViewCompat for TextView.
     * @param textView - related TextView on which the formatting should be applied on.
     * @param watchers - array of watchers that are watching on the text view content change.
     */
    public static void applyFormatting(final TextView textView, final TextWatcher... watchers) {

        TextWatcher mEditTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sendBeforeTextChanged(watchers, s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendOnTextChanged(watchers, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

                CharSequence formatted = extractFlagsForTextView(s);

                removeTextChangedListener(textView, this);
                textView.setText(formatted, TextView.BufferType.EDITABLE);
                Editable formattedEditableText = (Editable) textView.getText();
                sendAfterTextChanged(watchers, formattedEditableText);
                addTextChangedListener(textView, this);
            }
        };

        String text = textView.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            CharSequence formatted = extractFlagsForTextView(text);
            textView.setText(formatted);
        }
        textView.addTextChangedListener(mEditTextWatcher);

    }

    private static void sendAfterTextChanged(TextWatcher[] mListeners, Editable s) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.length; i++) {
                mListeners[i].afterTextChanged(s);
            }
        }
    }

    private static void sendOnTextChanged(TextWatcher[] mListeners, CharSequence s, int start, int before, int count) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.length; i++) {
                mListeners[i].onTextChanged(s, start, before, count);
            }
        }
    }

    private static void sendBeforeTextChanged(TextWatcher[] mListeners, CharSequence s, int start, int count, int after) {
        if (mListeners != null) {
            for (int i = 0; i < mListeners.length; i++) {
                mListeners[i].beforeTextChanged(s, start, count, after);
            }
        }
    }

    /**
     * Default modifier for removing text change listener
     * @param textView - related text view.
     * @param watcher - text watcher which has to be removed.
     */
    static void removeTextChangedListener(TextView textView, TextWatcher watcher) {
        textView.removeTextChangedListener(watcher);
    }

    /**
     * Default modifier for removing text change listener
     * @param textView - related text view.
     * @param watcher - text watcher which has to be removed.
     */
    static void addTextChangedListener(TextView textView, TextWatcher watcher) {
        textView.addTextChangedListener(watcher);
    }


    /**
     * Performs formatting on the given text.
     *
     * @param text - input sequence.
     * @return formatted sequence.
     */
    public static CharSequence extractFlagsForTextView(CharSequence text) {

        char[] textChars = text.toString().toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<WhatsappUtil.Flag> flags = new ArrayList<>();

        WhatsappUtil.Flag boldFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, BOLD_FLAG);
        WhatsappUtil.Flag strikeFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, STRIKE_FLAG);
        WhatsappUtil.Flag italicFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, ITALIC_FLAG);


        for (int i = 0, j = 0; i < textChars.length; i++) {
            char c = textChars[i];

            if (c == BOLD_FLAG) {
                if (boldFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, BOLD_FLAG, i + 1)) {
                        boldFlag.start = j;
                        continue;
                    }
                } else {
                    boldFlag.end = j;
                    flags.add(boldFlag);
                    boldFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, BOLD_FLAG);
                    continue;
                }
            }
            if (c == STRIKE_FLAG) {
                if (strikeFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, STRIKE_FLAG, i + 1)) {
                        strikeFlag.start = j;
                        continue;
                    }
                } else {
                    strikeFlag.end = j;
                    flags.add(strikeFlag);
                    strikeFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, STRIKE_FLAG);
                    continue;
                }
            }
            if (c == ITALIC_FLAG) {
                if (italicFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, ITALIC_FLAG, i + 1)) {
                        italicFlag.start = j;
                        continue;
                    }
                } else {
                    italicFlag.end = j;
                    flags.add(italicFlag);
                    italicFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, ITALIC_FLAG);
                    continue;
                }
            }
            characters.add(c);
            j++;
        }

        String formatted = WhatsappUtil.getText(characters);
        SpannableStringBuilder builder = new SpannableStringBuilder(formatted);


        for (int i = 0; i < flags.size(); i++) {

            WhatsappUtil.Flag flag = flags.get(i);

            if (flag.flag == BOLD_FLAG) {
                StyleSpan bss = new StyleSpan(Typeface.BOLD);
                builder.setSpan(bss, flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (flag.flag == STRIKE_FLAG) {
                builder.setSpan(new StrikethroughSpan(), flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (flag.flag == ITALIC_FLAG) {
                StyleSpan iss = new StyleSpan(Typeface.ITALIC);
                builder.setSpan(iss, flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }

        return builder;
    }


    /**
     * Performs formatting on the given text.
     *
     * @param text - input sequence.
     * @return formatted sequence.
     */
    public static CharSequence extractFlagsForEditText(CharSequence text) {

        char[] textChars = text.toString().toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<WhatsappUtil.Flag> flags = new ArrayList<>();

        WhatsappUtil.Flag boldFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, BOLD_FLAG);
        WhatsappUtil.Flag strikeFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, STRIKE_FLAG);
        WhatsappUtil.Flag italicFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, ITALIC_FLAG);


        for (int i = 0, j = 0; i < textChars.length; i++) {
            char c = textChars[i];

            if (c == BOLD_FLAG) {
                if (boldFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, BOLD_FLAG, i + 1)) {
                        boldFlag.start = j + 1;
                    }
                } else {
                    boldFlag.end = j;
                    flags.add(boldFlag);
                    boldFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, BOLD_FLAG);
                }
            } else if (c == STRIKE_FLAG) {
                if (strikeFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, STRIKE_FLAG, i + 1)) {
                        strikeFlag.start = j + 1;
                    }
                } else {
                    strikeFlag.end = j;
                    flags.add(strikeFlag);
                    strikeFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, STRIKE_FLAG);
                }
            } else if (c == ITALIC_FLAG) {
                if (italicFlag.start == INVALID_INDEX) {
                    if (WhatsappUtil.hasFlagSameLine(text, ITALIC_FLAG, i + 1)) {
                        italicFlag.start = j + 1;
                    }
                } else {
                    italicFlag.end = j;
                    flags.add(italicFlag);
                    italicFlag = new WhatsappUtil.Flag(INVALID_INDEX, INVALID_INDEX, ITALIC_FLAG);
                }
            }
            characters.add(c);
            j++;
        }

        String formatted = WhatsappUtil.getText(characters);
        SpannableStringBuilder builder = new SpannableStringBuilder(formatted);


        for (int i = 0; i < flags.size(); i++) {

            WhatsappUtil.Flag flag = flags.get(i);
            if (flag.flag == BOLD_FLAG) {
                StyleSpan bss = new StyleSpan(Typeface.BOLD);
                builder.setSpan(bss, flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.start-1, flag.start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.end, flag.end+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            } else if (flag.flag == STRIKE_FLAG) {
                builder.setSpan(new StrikethroughSpan(), flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.start-1, flag.start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.end, flag.end+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            } else if (flag.flag == ITALIC_FLAG) {
                StyleSpan iss = new StyleSpan(Typeface.ITALIC);
                builder.setSpan(iss, flag.start, flag.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.start-1, flag.start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                builder.setSpan(new ForegroundColorSpan(Color.GRAY), flag.end, flag.end+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            }

        }

        return builder;
    }
}
