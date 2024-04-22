package AI.Memory.Reminisce.Model.Chain;

// ##### 简介 #####
// 回忆链
// 返回回忆库内搜索的信息
// 可用于反推赋值

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Memory.MemoryChain;
import AI.Memory.MemoryChainType;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.util.ArrayList;

public class ReminisceChain implements MemoryChain {

    // ##### 变量 #####
    // 回忆节点地址
    ReminisceNode node;
    // 回忆链搜索标签
    ArrayList<String> searchLabel;

    // ##### 构造函数 #####
    // 创建新回忆链
    public ReminisceChain(ReminisceNode input_node, ArrayList<String> input_labelList) {
        node = input_node;
        searchLabel = input_labelList;
    }

    // ##### 功能函数 #####
    // 返回当前回忆搜索标签
    public ArrayList<String> getSearchLabel() {
        return searchLabel;
    }

    // 返回当前回忆节点地址
    public ArrayList<String> getReminisce() {
        return node.getCrumb();
    }

    // 返回当前节点状态
    public MemoryChainType getType() {
        return MemoryChainType.Reminisce;
    }

    public void addLabel() {

    }

    // 增强当前回忆链
    public void enhance() {
        node.addWeight();
    }

    // 抑制当前回忆链
    public void restrain() {
        node.subWeight();
    }

    // 打印当前回忆链信息
    @Override
    public String toString() {
        return "ReminisceChain{" +
                "node=" + node +
                ", searchLabel=" + searchLabel +
                '}';
    }
}
