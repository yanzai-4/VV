package AI.Memory.Reminisce.Function;

// ##### 简介 #####
// 回忆库排序比较器
// 通过优先度和时间来进行排序

// ##### 日志 #####
// 1.0 版本
// 简单架构
// done

import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.util.Comparator;

public class ReminisceFuncSort implements Comparator<ReminisceNode> {

    // ##### 功能函数 #####
    // 继承并复写比较器功能
    // 从大到小排列
    @Override
    public int compare(ReminisceNode node_1, ReminisceNode node_2) {
        return node_2.getWeight() - node_1.getWeight();
    }
}
