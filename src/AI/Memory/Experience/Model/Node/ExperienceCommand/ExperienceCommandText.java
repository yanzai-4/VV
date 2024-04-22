package AI.Memory.Experience.Model.Node.ExperienceCommand;

// ##### 简介 #####
// 方案样本固定指令
// 保存固定形式的文字结构
// 数据储存结构：type+word-word-word

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import java.util.ArrayList;

public class ExperienceCommandText extends ExperienceCommand {

    // ##### 继承 #####
    // 变量： ExperienceCommandType type
    // 功能： getType(), toData(), toString()

    // ##### 变量 #####
    // 固定内容
    private ArrayList<String> content;

    // ##### 构造函数 #####
    // 创建新指令
    public ExperienceCommandText() {
        content = new ArrayList<>();
        type = ExperienceCommandType.Text;
    }

    // 返回当前内容
    public ArrayList<String> getContent() {
        return content;
    }

    // 设置当前内容
    public void setContent(ArrayList<String> input_content){
        content = input_content;
    }

    // 添加新内容
    public void addContent(String input_word){
        content.add(input_word);
    }

    // 检查当前指令是否为空
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @Override
    public boolean equals(Object input_object) {
        if (this == input_object) {
            return true;
        }
        if (input_object == null || getClass() != input_object.getClass()) {
            return false;
        }
        ExperienceCommandText that = (ExperienceCommandText) input_object;
        return content.equals(that.content);
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        String text = "";
        for (String temp_word : content) {
            text += temp_word + "-";
        }
        return type.name() + "=" + text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceCommandText{" +
                "content=" + content +
                '}';
    }
}
