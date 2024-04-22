package AI.Memory.Experience.Model.Node.ExperienceCommand;

// ##### 简介 #####
// 方案样本指令
// 用于统一多态类
// 数据储存结构：无

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

public class ExperienceCommand {

    // ##### 变量 #####
    // 指令类型
    protected ExperienceCommandType type;

    // ##### 功能函数 #####
    // 获取当前指令类型
    public ExperienceCommandType getType() {
        return type;
    }
    // 返回当前节点保存信息
    public String toData() {
        return null;
    }


    // 返回打印信息
    @Override
    public String toString() {
        return null;
    }
}
