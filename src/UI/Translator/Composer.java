package UI.Translator;

// ##### 简介 #####
// AI语言转译器
// 用于翻译AI语言为人类语言

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Composer {

    // ##### 调用函数 #####
    // 转译
    public String getCompose(ArrayList<String> input_text) {
        String text = this.doCompose(input_text);
        return this.upCase(text);
    }

    // ##### 功能函数 #####
    // 组合文字列
    private String doCompose(ArrayList<String> input_text) {
        String text = "";
        boolean digit = false;
        for (String temp_word : input_text) {
            if (Pattern.compile("[0-9]*").matcher(temp_word).matches()) {
                if (!digit) {
                    text += " ";
                    digit = true;
                }
            }
            else {
                if (digit) {
                    digit = false;
                }
                if (!temp_word.equals(".") && !temp_word.equals(",") && !temp_word.equals("?") && !temp_word.equals("!")) {
                    text += " ";
                }
            }
            text += temp_word;
        }
        return text.substring(1);
    }

    // 自动大写段落首字母
    private String upCase(String input_text) {
        StringBuilder temp_buffer = new StringBuilder(input_text);
        temp_buffer.replace(0,1, String.valueOf(Character.toUpperCase(input_text.charAt(0))));
        for(int i = 1; i < input_text.length() - 1; i ++) {
            char temp_char = input_text.charAt(i - 1);
            if (temp_char == '.' || temp_char == '?' || temp_char == '!') {
               temp_buffer.replace(i + 1,i + 2, String.valueOf(Character.toUpperCase(input_text.charAt(i + 1))));
            }
        }
        return temp_buffer.toString();
    }
}
