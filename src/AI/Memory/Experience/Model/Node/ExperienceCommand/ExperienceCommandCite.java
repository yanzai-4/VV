package AI.Memory.Experience.Model.Node.ExperienceCommand;

// ##### 简介 #####
// 方案样本引用指令
// 引用问题部分内容
// 开头和结尾可以有一方不存在
// 数据储存结构：type+start-start+end-end

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import java.util.ArrayList;
import java.util.Objects;

public class ExperienceCommandCite extends ExperienceCommand {

    // ##### 继承 #####
    // 变量： ExperienceCommandType type
    // 功能： getType(), toData(), toString()

    // ##### 变量 #####
    // 开头引索
    private ArrayList<String> start;
    // 结束引索
    private ArrayList<String> end;

    // ##### 构造函数 #####
    // 创建新指令
    public ExperienceCommandCite() {
        start = new ArrayList<>();
        end = new ArrayList<>();
        type = ExperienceCommandType.Cite;
    }

    // 返回开头引索
    public ArrayList<String> getStart() {
        return start;
    }

    // 返回结束引索
    public ArrayList<String> getEnd() {
        return end;
    }

    // 设置当前开头引索
    public void setStart(ArrayList<String> input_start) {
        start = input_start;
    }

    // 设置当前结束引索
    public void setEnd(ArrayList<String> input_end) {
        end = input_end;
    }

    // 添加开头引索
    public void addStart(String input_word) {
        start.add(input_word);
    }

    // 添加结束引索
    public void addEnd(String input_word) {
        end.add(input_word);
    }

    // 返回是否包含开头引索
    public boolean hasStart() {
        return !start.isEmpty();
    }

    // 返回是否包含结束引索
    public boolean hasEnd() {
        return !end.isEmpty();
    }

    // 返回当前任务是否存在
    public boolean isEmpty() {
        return start.isEmpty() && end.isEmpty();
    }

    // 转换开头引索为文本
    private String toStart() {
        if (start.isEmpty()) {
            return "*";
        }
        else {
            String text = "";
            for (String temp_word : start) {
                text += temp_word + "-";
            }
            return text.substring(0, text.length() - 1);
        }
    }

    // 转换结束引索为文本
    private String toEnd() {
        if (end.isEmpty()) {
            return "*";
        }
        else {
            String text = "";
            for (String temp_word : end) {
                text += temp_word + "-";
            }
            return text.substring(0, text.length() - 1);
        }
    }

    @Override
    public boolean equals(Object input_object) {
        if (this == input_object) {
            return true;
        }
        if (input_object == null || getClass() != input_object.getClass()) {
            return false;
        }
        ExperienceCommandCite that = (ExperienceCommandCite) input_object;
        return  (start.equals(that.start) && end.equals(that.end));
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        return type.name() + "=" + toStart() + "=" + toEnd();
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceCommandCite{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
