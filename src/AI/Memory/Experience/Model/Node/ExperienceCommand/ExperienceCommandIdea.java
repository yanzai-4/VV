package AI.Memory.Experience.Model.Node.ExperienceCommand;

// ##### 简介 #####
// 方案样本衍生指令
// 衍生出新的情况用于思考
// 开头必须为 think
// 数据储存结构：type=command+command~command+command

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import java.util.ArrayList;
import java.util.Objects;

public class ExperienceCommandIdea extends ExperienceCommand {

    // ##### 继承 #####
    // 变量： ExperienceCommandType type
    // 功能： getType(), toData(), toString()

    // ##### 变量 #####
    // 衍生方案
    private ArrayList<ExperienceCommand> idea;

    // ##### 构造函数 #####
    // 创建新指令
    public ExperienceCommandIdea() {
        idea = new ArrayList<>();
        type = ExperienceCommandType.Idea;
    }

    // 返回衍生方案
    public ArrayList<ExperienceCommand> getIdea() {
        return idea;
    }

    // 设置衍生方案
    public void setIdea(ArrayList<ExperienceCommand> input_idea) {
        idea = input_idea;
    }

    // 添加衍生方案
    public void addIdea(ExperienceCommand input_command) {
        if(input_command.getType() != ExperienceCommandType.Idea) {
            idea.add(input_command);
        }
    }

    // 返回当前方案是否为空
    public boolean isEmpty() {
        return idea.isEmpty();
    }


    @Override
    public boolean equals(Object input_object) {
        if (this == input_object) {
            return true;
        }
        if (input_object == null || getClass() != input_object.getClass()) {
            return false;
        }
        ExperienceCommandIdea that = (ExperienceCommandIdea) input_object;
        return idea.equals(that.idea);
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        String text = "";
        for (ExperienceCommand temp_command : idea) {
            text += temp_command.toData() + "~";
        }
        return type.name() + "=" + text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceCommandIdea{" +
                "idea=" + idea +
                '}';
    }
}
