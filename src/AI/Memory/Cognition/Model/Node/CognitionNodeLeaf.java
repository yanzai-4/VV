package AI.Memory.Cognition.Model.Node;

// ##### 简介 #####
// 认知节点叶
// 用于保存独立节点信息
// 数据储存结构：info#type

// ##### 日志 #####
// 3.0 版本
// 改为多态继承架构
// 3.1 版本
// 美化
// 4.0 版本
// 修正
// done

public class CognitionNodeLeaf extends CognitionNode {

    // ##### 继承 #####
    // 变量： String type, String info
    // 功能： getType(), getInfo(), getChain(), toData(), toString()

    // ##### 构造函数 #####
    // 创建新节点
    public CognitionNodeLeaf(String input_info) {
        super(input_info);
        type = CognitionType.Leaf;
    }

    // ##### 功能函数 #####
    // 返回当前节点保存信息
    @Override
    public String toData() {
        return info + "#" + type.name();
    }
}
