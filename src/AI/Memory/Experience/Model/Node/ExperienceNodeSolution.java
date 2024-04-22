package AI.Memory.Experience.Model.Node;

// ##### 简介 #####
// 经验节点方法样本
// 用于储存方法内容以及其权重
// 数据结构： type&weight&solution;solution&**

// ##### 日志 #####
// 1.0 版本
// 简单架构
// 2.0 版本
// 增加类型
// 2.1 版本
// 增加不可沉默模式
// done

import AI.Memory.Experience.Model.Message.ExperienceMsgResist;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommand;

import java.util.ArrayList;

public class ExperienceNodeSolution {

    // ##### 变量 #####
    // 方法类型
    private ExperienceNodeSolutionType type;
    // 方法主内容
    private ArrayList<ExperienceCommand> solution;
    // 方法副内容
    private ArrayList<ExperienceCommand> subSolution;
    // 激活标记
    private boolean active;

    private boolean isRoot;
    // 方法权重
    private int weight;
    // 方法输出类型 1/2
    private int status;

    // ##### 构造函数 #####
    // 创建新节点
    public ExperienceNodeSolution(String input_type) {
        solution = new ArrayList<>();
        subSolution = new ArrayList<>();
        active = true;
        isRoot = false;
        weight = 7;
        status = 0;
        this.setType(input_type);
    }

    // 加载节点
    public ExperienceNodeSolution(String input_type, int input_weight) {
        solution = new ArrayList<>();
        subSolution = new ArrayList<>();
        active = true;
        isRoot = false;
        weight = input_weight;
        status = 0;
        this.setType(input_type);
    }

    // ##### 功能函数 #####
    // 返回当前方法权重
    public int getWeight() {
        return weight;
    }

    // 返回当前方法类型
    public ExperienceNodeSolutionType getType() {
        return type;
    }

    // 返回当前方法主内容
    public ArrayList<ExperienceCommand> getSolution() {
        return solution;
    }

    // 返回当前方法副内容
    public ArrayList<ExperienceCommand> getSubSolution() {
        return subSolution;
    }

    public void setRoot(boolean input_status) {
        isRoot = input_status;
    }

    // 设置当前方法类型
    private void setType(String input_type) {
        switch (input_type) {
            case "Say" -> {
                type = ExperienceNodeSolutionType.Say;
                status = 1;
            }
            case "Think" -> {
                type = ExperienceNodeSolutionType.Think;
                status = 1;
            }
            case "Reminisce" -> {
                type = ExperienceNodeSolutionType.Reminisce;
                status = 1;
            }
            case "Except" -> {
                type = ExperienceNodeSolutionType.Except;
                status = 1;
            }
            case "Enhance" -> {
                type = ExperienceNodeSolutionType.Enhance;
                status = 1;
            }
            case "Restrain" -> {
                type = ExperienceNodeSolutionType.Restrain;
                status = 1;
            }
            case "Learn" -> {
                type = ExperienceNodeSolutionType.Learn;
                status = 2;
            }
            case "Compare" -> {
                type = ExperienceNodeSolutionType.Compare;
                status = 2;
            }
            case "Relate" -> {
                type = ExperienceNodeSolutionType.Relate;
                status = 2;
            }
            case "Remember" -> {
                type = ExperienceNodeSolutionType.Remember;
                status = 2;
            }
        }
    }

    // 添加指令集到当前方法主内容
    public void setSolution(ArrayList<ExperienceCommand> input_commands) {
        solution = input_commands;
    }

    // 添加指令集到当前方法主内容
    public void setSubSolution(ArrayList<ExperienceCommand> input_commands) {
        subSolution = input_commands;
    }

    // 添加指令到当前方法主内容
    public void addSolution(ExperienceCommand input_command) {
        solution.add(input_command);
    }

    // 添加指令到当前方法副内容
    public  void addSubSolution(ExperienceCommand input_command) {
        subSolution.add(input_command);
    }

    // 增加当前方法权重
    public void addWeight() {
        if (weight < 10) {
            weight += 1;
        }
    }

    // 降低当前方法权重
    public void subWeight() {
        if (weight > 0) {
            weight -= 1;
        }
    }

    // 沉默当前样本 锁1权重一段时间
    public void deActive() {
        if (!isRoot) {
            active = false;
            new ExperienceMsgResist(this).start();
        }
    }

    // 激活当前样本
    public void active() {
        active = true;
    }

    // 返回当前样本是否激活
    public boolean isActive() {
        return active;
    }

    // 返回当前样本是否存活
    public boolean isDead() {
        return weight == 0 || status == 0 || solution.isEmpty();
    }

    // 返回当前样本摘要信息
    public String toInfo() {
        return type.name();
    }

    // 返回数据储存结构
    public String toData() {
        String text = "";
        for (ExperienceCommand temp_command : solution) {
            text += temp_command.toData() + ";";
        }
        text = text.substring(0, text.length() - 1) + "&";
        if (status == 2) {
            for (ExperienceCommand temp_command : subSolution) {
                text += temp_command.toData() + ";";
            }
        }
        else {
            text += "**";
        }
        String root = "F";
        if (isRoot) {
            root = "T";
        }
        return type.name() + "&" + weight + "&" + root + "&" + text.substring(0, text.length() - 1);
    }

    // 返回打印信息

    @Override
    public String toString() {
        return "ExperienceNodeSolution{" +
                "type=" + type +
                ", solution=" + solution +
                ", subSolution=" + subSolution +
                ", weight=" + weight +
                '}';
    }
}


