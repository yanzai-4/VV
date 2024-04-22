package AI.Memory.Reminisce.Model.Node;

// ##### 简介 #####
// 记忆团
// 用于保存记忆片段
// 数据储存结构：memory-memory-memory

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 2.0
// 重命名
// done

import java.util.ArrayList;

public class ReminisceNodeCrumb {

    // ##### 变量 #####
    // 记忆片段
    private ArrayList<String> reminisce;

    // ##### 构造函数 #####
    // 创建新记忆团
    public ReminisceNodeCrumb(ArrayList<String> input_reminisce) {
        reminisce = input_reminisce;
    }

    // ##### 功能函数 #####
    // 获取当前记忆片段
    public ArrayList<String> getCrumb() {
        return reminisce;
    }

    // 打印当前记忆团信息
    public String toData() {
        String text = "";
        for (String temp_word : reminisce) {
            text += (temp_word + "-");
        }
        return text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ReminisceNodeCrumb{" +
                "reminisce=" + reminisce +
                '}';
    }
}
