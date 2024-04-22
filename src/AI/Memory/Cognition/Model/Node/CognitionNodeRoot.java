package AI.Memory.Cognition.Model.Node;

// ##### 简介 #####
// 认知节点根
// 用于保存末端节点信息
// 拥有唯一Sensation指定代码用于匹配
// 数据储存结构：info#type#code

// ##### 日志 #####
// 3.0 版本
// 改为多态继承架构
// 4.0 版本
// 修正
// done

import AI.Memory.Cognition.Model.Chain.CognitionChain;

public class CognitionNodeRoot extends CognitionNode {

    // ##### 继承 #####
    // 变量： String type, String info
    // 功能： getType(), getInfo(), getChain(), toData(), toString()

    // ##### 变量 #####
    // 当前节点认知指令
    private String code;

    // ##### 构造函数 #####
    // 创建新节点
    public CognitionNodeRoot(String input_info, String input_code) {
        super(input_info);
        code = input_code;
        type = CognitionType.Root;
    }

    // ##### 功能函数 #####
    // 返回当前节点认知指令
    public String getCode() {
        return code;
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        return info + "#" + type.name() + "#" + code;
    }
}
