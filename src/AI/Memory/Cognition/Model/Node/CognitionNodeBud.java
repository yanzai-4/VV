package AI.Memory.Cognition.Model.Node;

// ##### 简介 #####
// 认知节点芽
// 用于暂存节点信息
// 在一段时间后会更改自己状态或自毁
// 数据储存结构：无

// ##### 日志 #####
// 3.0 版本
// 改为多态继承架构
// 3.1 版本
// 美化
// 4.0 版本
// 修正
// done

import AI.Memory.Cognition.Model.Message.CognitionMsgCheck;

import java.util.HashMap;

public class CognitionNodeBud extends CognitionNode {

    // ##### 继承 #####
    // 变量： String type, String info
    // 功能： getType(), getInfo(), getChain(), toData(), toString()

    // ##### 变量 #####
    // 认知节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 超线程检查消息
    private CognitionMsgCheck check;
    // 当前节点权重
    private int weight;

    // ##### 构造函数 #####
    // 创建新节点
    public CognitionNodeBud(HashMap<String, CognitionNode> input_cognitionNet, String input_info) {
        super(input_info);
        cognitionNet = input_cognitionNet;
        type = CognitionType.Bud;
        weight = 0;
        this.init();
    }

    // ##### 功能函数 #####
    // 初始化计时器
    private void init() {
        check = new CognitionMsgCheck(cognitionNet, this);
        check.start();
    }

    // 返回当前节点权重
    public int getWeight() {
        return weight;
    }

    // 返回当前节点信息并增加权重
    @Override
    public String getInfo() {
        weight += 1;
        if (weight > 3) {
            this.turnLeaf();
        }
        return this.info;
    }

    // 转变当前节点为节点枝
    public void turnBranch(String input_port) {
        CognitionNodeBranch temp_node = new CognitionNodeBranch(info);
        temp_node.addPort(input_port);
        cognitionNet.put(info, temp_node);
        if (check.isAlive()) {
            check.interrupt();
        }
    }

    // 转变当前节点为节点叶
    public void turnLeaf() {
        CognitionNodeLeaf temp_node = new CognitionNodeLeaf(info);
        cognitionNet.put(info, temp_node);
        if (check.isAlive()) {
            check.interrupt();
        }
    }

    // 删除当前节点
    public void delete() {
        cognitionNet.remove(info);
        if (check.isAlive()) {
            check.interrupt();
        }
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        return null;
    }
}
