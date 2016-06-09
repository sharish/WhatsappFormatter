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

import java.util.ArrayList;

/**
 * Created by Harish on 11/05/16.
 */
public class WhatsappUtil {

    /**
     * Flag marking a sequence of character to be a specific formatting type.
     */
    public static class Flag {

        int start; int end; char flag;

        public Flag(int start, int end, char flag) {
            this.start = start;
            this.end = end;
            this.flag = flag;
        }
    }

    public static final char NEW_LINE = '\n';
    public static final char SPACE = ' ';
    public static final char BOLD_FLAG = '*';
    public static final char STRIKE_FLAG = '~';
    public static final char ITALIC_FLAG = '_';


    public static final int INVALID_INDEX = -1;

    /**
     * Checks whether the character present at the index of the CharSequence is a flagged character.
     * @param text - input text.
     * @param index - index to check.
     * @return true if the character is flagged, false otherwise.
     */
    public static boolean isFlagged(CharSequence text, int index) {
        if(index > INVALID_INDEX && index < text.length()) {
            char c = text.charAt(index);
            return c == SPACE || c == NEW_LINE || c == BOLD_FLAG || c == ITALIC_FLAG || c == STRIKE_FLAG;
        }
        return true;
    }

    /**
     * Converts List of Characters to String.
     * @param characters input list.
     * @return String value.
     */
    public static String getText(ArrayList<Character> characters) {
        char[] chars = new char[characters.size()];

        for (int i = 0; i < chars.length; i++) {
            chars[i] = characters.get(i);
        }
        return new String(chars);
    }

    /**
     * Tells whether has a flag in the same line as mentioned by fromIndex character.
     * @param sequence - text
     * @param flag - expected flag.
     * @param fromIndex - index representing the line.
     * @return
     */
    public static boolean hasFlagSameLine(CharSequence sequence, char flag, int fromIndex) {

        for(int i=fromIndex;i<sequence.length();i++) {
            char c = sequence.charAt(i);
            if(c == NEW_LINE) {
                return false;
            }

            if(c == flag ) {
                return i != fromIndex;
            }
        }

        return false;
    }
}
