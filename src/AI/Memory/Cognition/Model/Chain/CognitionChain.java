package AI.Memory.Cognition.Model.Chain;

// ##### 简介 #####
// 认知链
// 保存认知节点返回的信息

// ##### 日志 #####
// 1.0 版本
// 增加跟进和后退功能
// 2.0 版本
// 独立出来
// 3.0 版本
// 修正
// 3.1 版本
// 改为多结果
// done

import AI.Memory.Cognition.Model.Node.CognitionNode;
import AI.Memory.Cognition.Model.Node.CognitionNodeBranch;
import AI.Memory.Cognition.Model.Node.CognitionType;
import AI.Memory.MemoryChain;
import AI.Memory.MemoryChainType;

import java.util.ArrayList;

public class CognitionChain implements MemoryChain {

    // ##### 变量 #####
    // 认知链目标
    private CognitionNode node;
    // 认知链结果
    private ArrayList<String> connection;

    // ##### 构造函数 #####
    // 创建新认知链
    public CognitionChain(CognitionNode input_node) {
        node = input_node;
        connection = new ArrayList<>();
        this.init();
    }

    // ##### 功能函数 #####
    // 初始化认知链
    private void init() {
        switch (node.getType()) {
            case Bud, Leaf -> {
                connection.add(node.getInfo());
            }
            case Root -> {
                connection.add(node.getInfo());
            }
            case Branch -> {
                CognitionNodeBranch temp_branch = (CognitionNodeBranch) node;
                connection.addAll(temp_branch.getInfoList());
            }
        }
    }

    // 返回当前结果
    public ArrayList<String> getConnection() {
        return connection;
    }

    // 返回当前类型
    public MemoryChainType getType() {
        return MemoryChainType.Cognition;
    }

    // 增强当前认知链
    public void enhance() {
        if (node.getType() == CognitionType.Branch) {
            CognitionNodeBranch temp_branch = (CognitionNodeBranch) node;
            temp_branch.addPortListWeight(connection);
        }
    }

    // 抑制当前认知链
    public void restrain() {
        if (node.getType() == CognitionType.Branch) {
            CognitionNodeBranch temp_branch = (CognitionNodeBranch) node;
            temp_branch.subPortListWeight(connection);
        }
    }

    // 打印当前认知链信息
    @Override
    public String toString() {
        return "CognitionChain{" +
                "node=" + node +
                ", connection=" + connection +
                '}';
    }
}
