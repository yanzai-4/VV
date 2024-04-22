package AI.Memory.Reminisce.Model.Node;

// ##### 简介 #####
// 记忆标签
// 用于保存单一记忆标签及其权重
// 数据储存结构：index-weight

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 2.0
// 重命名
// 2.1
// 重命名
// done

public class ReminisceNodeLabel {

    // ##### 变量 #####
    // 记忆标签
    private String tag;
    // 记忆权重
    private int weight;

    // ##### 构造函数 #####
    // 创建新记忆标签 默认权重为4
    public ReminisceNodeLabel(String input_tag) {
        tag = input_tag;
        weight = 4;
    }

    // ##### 功能函数 #####
    // 读取现有记忆标签
    public ReminisceNodeLabel(String input_tag, int input_weight) {
        tag = input_tag;
        weight = input_weight;
    }

    // 返回当前记忆标签
    public String getTag() {
        return tag;
    }

    // 增加标签权重 最大为6
    public void addWeight() {
        if (weight < 6) {
            weight += 1;
        }
    }

    // 降低标签权重
    public void subWeight() {
        weight -= 1;
    }

    // 返回当前标签是否存活
    public Boolean isDead() {
        return weight < 1;
    }

    // 返回当前标签储存信息
    public String toData() {
        return tag +  "-" + weight;
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ReminisceNodeLabel{" +
                "tag='" + tag + '\'' +
                ", weight=" + weight +
                '}';
    }
}
