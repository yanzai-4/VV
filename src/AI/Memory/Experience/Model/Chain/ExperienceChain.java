package AI.Memory.Experience.Model.Chain;

import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Memory.MemoryChain;
import AI.Memory.MemoryChainType;

import java.util.ArrayList;

// ##### 简介 #####
// 经验链
// 保存认知节点返回的信息

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

public class ExperienceChain implements MemoryChain {

    // ##### 变量 #####
    // 节点地址
    ExperienceNode node;
    // 当前情况
    ArrayList<String> searchSituation;
    // 对应方法
    ExperienceNodeSolution solution;
    // 当前权重
    int weight;

    // ##### 构造函数 #####
    // 创建新经验链
    public ExperienceChain(ExperienceNode input_node, ArrayList<String> input_situation, int input_weight) {
        node = input_node;
        searchSituation = input_situation;
        solution = input_node.getSolution();
        weight = input_weight;
    }

    // ##### 功能函数 #####
    // 返回当前方法
    public ExperienceNodeSolution getSolution() {
        solution.deActive();
        return solution;
    }

    // 返回当前搜索情况
    public ArrayList<String> getSearchSituation() {
        return searchSituation;
    }

    // 返回所有方法
    public ArrayList<ExperienceNodeSolution> getAllSolution() {
        return node.getAllSolution();
    }

    // 返回当前类型
    public MemoryChainType getType() {
        return MemoryChainType.Experience;
    }

    // 返回当前权重
    public int getWeight() {
        return weight;
    }

    // 增强当前经验链
    public void enhance() {
        solution.addWeight();
    }

    // 抑制当前经验链
    public void restrain() {
        solution.subWeight();
    }

    public boolean isWork() {
        return solution != null;
    }

    // 打印当前经验链信息
    @Override
    public String toString() {
        return "ExperienceChain{" +
                "searchSituation=" + searchSituation +
                ", solution=" + solution +
                '}';
    }
}
