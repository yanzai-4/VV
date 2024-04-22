package AI.Memory.Cognition.Model.Node;

// ##### 简介 #####
// 认知节点抽象类
// 用于统一多态类
// 数据储存结构：无

// ##### 日志 #####
// 1.0 版本
// 添加检查空连接集
// 2.0 版本
// 取消标签化 采取统一调配
// 2.1 版本
// 删除节点权重
// 3.0 版本
// 改为多态继承架构
// 3.1 版本
// 美化
// 3.2 版本
// 增加 getChain()
// 4.0 版本
// 修正
// done

public abstract class CognitionNode {

    // ##### 变量 #####
    // 当前节点储存词
    protected String info;
    // 当前节点类型
    protected CognitionType type;

    // ##### 构造函数 #####
    // 创建新节点
    public CognitionNode(String input_info) {
        info = input_info;
    }

    // ##### 功能函数 #####
    // 返回当前节点类型
    public CognitionType getType() {
        return type;
    }

    // 返回当前节点信息
    public String getInfo() {
        return info;
    }

    // 返回当前节点保存信息
    public String toData() {
        return null;
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "CognitionNode{" +
                "info='" + info + '\'' +
                ", type=" + type +
                '}';
    }
}

