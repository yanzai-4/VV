package UI.Translator;

// ##### 简介 #####
// AI语言编译器
// 用于翻译人类语言为AI语言

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import java.util.ArrayList;

public class Splitter {

    // ##### 调用函数 #####
    // 编译
    public ArrayList<String> getSplit(String input_text) {
        input_text = this.removeOther(input_text);
        input_text = this.lowCase(input_text);
        input_text = this.spaceAfterDigit(input_text);
        input_text = this.addEnd(input_text);
        input_text = this.symbolEnd(input_text);
        input_text = this.symbolComma(input_text);
        input_text = this.symbolAsk(input_text);
        input_text = this.symbolWow(input_text);
        return this.doSplit(input_text);
    }

    // ##### 功能函数 #####
    // 移除非法字符
    private String removeOther(String input_text) {
        String keepChar = "[^0-9A-Za-z,.?! ]";
        return input_text.replaceAll(keepChar, "");
    }

    // 添加空格在数字后
    private String spaceAfterDigit(String input_text) {
        StringBuilder temp_buffer = new StringBuilder(input_text);
        for(int i = 0; i < temp_buffer.length(); i ++) {
            if (Character.isDigit(temp_buffer.charAt(i))) {
                temp_buffer.insert(i + 1, " ");
            }
        }
        return temp_buffer.toString();
    }

    // 整体小写
    private String lowCase(String input_text) {
        return input_text.toLowerCase();
    }

    // 添加末尾符号
    private String addEnd(String input_text) {
        String last = input_text.substring(input_text.length() - 1);
        if (!last.equals(",") && !last.equals("?") && !last.equals("!")) {
            input_text += ". ";
        }
        return input_text;
    }

    // 提取出句号
    private String symbolEnd(String input_text) {
        if(!input_text.contains(".")) {
            return input_text;
        }
        String temp_text = "";
        String[]symbol_field = input_text.split("[.]");
        for(String temp_sentence : symbol_field){
            if(!temp_sentence.equals(" ") && !temp_sentence.equals("")){
                temp_text += temp_sentence;
                String last = temp_text.substring(temp_text.length() - 1);
                if(!last.equals(",") && !last.equals("?") && !last.equals("!")){
                    temp_text += " .";
                }
                temp_text += " ";
            }
        }
        return temp_text.substring(0, temp_text.length() - 1);
    }

    // 提取出逗号
    private String symbolComma(String input_text) {
        if(!input_text.contains(",")) {
            return input_text;
        }
        String temp_text = "";
        String[]symbol_field = input_text.split("[,]");
        for(String temp_sentence : symbol_field){
            if(!temp_sentence.equals(" ") && !temp_sentence.equals("")){
                temp_text += temp_sentence;
                String last = temp_text.substring(temp_text.length() - 1);
                if(!last.equals(".") && !last.equals("?") && !last.equals("!")){
                    temp_text += " ,";
                }
                temp_text += " ";
            }
        }
        return temp_text.substring(0, temp_text.length() - 1);
    }

    // 提取出问好
    private String symbolAsk(String input_text) {
        if(!input_text.contains("?")) {
            return input_text;
        }
        String temp_text = "";
        String[]symbol_field = input_text.split("[?]");
        for(String temp_sentence : symbol_field){
            if(!temp_sentence.equals(" ") && !temp_sentence.equals("")){
                temp_text += temp_sentence;
                String last = temp_text.substring(temp_text.length() - 1);
                if(!last.equals(".") && !last.equals(",") && !last.equals("!")){
                    temp_text += " ?";
                }
                temp_text += " ";
            }
        }
        return temp_text.substring(0, temp_text.length() - 1);
    }

    // 提取出感叹号
    private String symbolWow(String input_text) {
        if(!input_text.contains("!")) {
            return input_text;
        }
        String temp_text = "";
        String[]symbol_field = input_text.split("[!]");
        for(String temp_sentence : symbol_field){
            if(!temp_sentence.equals(" ") && !temp_sentence.equals("")){
                temp_text += temp_sentence;
                String last = temp_text.substring(temp_text.length() - 1);
                if(!last.equals(".") && !last.equals("?") && !last.equals(",")){
                    temp_text += " !";
                }
                temp_text += " ";
            }
        }
        return temp_text.substring(0, temp_text.length() - 1);
    }

    // 转换段落为文字列
    private ArrayList<String> doSplit(String input_text) {
        ArrayList<String> result = new ArrayList<>();
        String[]word_field = input_text.split(" ");
        for(String x : word_field){
            if(!x.equals("")){
                result.add(x);
            }
        }
        return result;
    }
}
