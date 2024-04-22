package AI.Memory.Experience.Model.Node;

// ##### 简介 #####
// 经验节点结
// 用于储存情况样本和衍生节点
// 数据结构： situation#node>node>node

// ##### 日志 #####
// 1.0 版本
// 添加对比方法
// 1.1 版本
// 添加防止重复添加
// done

import java.util.ArrayList;

public class ExperienceNodeKnot {

    // ##### 变量 #####
    // 情况样本
    private ExperienceNodeSituation situation;
    // 经验节点集
    private ArrayList<ExperienceNode> nodes;

    // ##### 构造函数 #####
    // 创建新节点
    public ExperienceNodeKnot(ArrayList<String> input_situation) {
        situation = new ExperienceNodeSituation(input_situation);
        nodes = new ArrayList<>();
    }

    // ##### 功能函数 #####
    // 返回当前情况样本
    public ExperienceNodeSituation getSituation() {
        return situation;
    }

    public ArrayList<ExperienceNode> getNodes() {
        return nodes;
    }

    // 返回最接近目标的经验节点
    public ExperienceNode getNode(ArrayList<String> input_situation) {
        int maxRate = 0;
        ExperienceNode maxNode = null;
        for (ExperienceNode temp_node : nodes) {
            int temp_rate = temp_node.getSituation().match(input_situation);
            if (temp_rate > maxRate) {
                maxRate = temp_rate;
                maxNode = temp_node;
            }
        }
        if(maxRate != 0) {
            return  maxNode;
        }
        return null;
    }

    // 手动设置经验节点
    public void setNode(ArrayList<ExperienceNode> input_nodes) {
        nodes = input_nodes;
    }

    // 增加经验节点
    public void addNode(ExperienceNode input_node) {
        for (ExperienceNode temp_node : nodes) {
            if (temp_node.getSituation().isSame(input_node.getSituation().getSituation())) {
                temp_node.addSolution(input_node.getSolution());
            }
        }
        nodes.add(input_node);
    }

    // 筛查失效节点
    public void check() {
        for (int i = 0; i < nodes.size(); i ++) {
            nodes.get(i).check();
            if (nodes.get(i).isDead()) {
                nodes.remove(i);
                i--;
            }
        }
    }

    // 返回当前节点是否存活
    public boolean isDead() {
        return nodes.isEmpty();
    }

    // 返回当前样本摘要信息
    public String toInfo() {
        return situation.toInfo();
    }

    // 返回数据储存结构
    public String toData() {
        String text = "";
        for (ExperienceNode temp_node : nodes) {
            text += temp_node.toData() + ">";
        }
        return situation.toData() + "#" + text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceNodeKnot{" +
                "situation=" + situation +
                ", nodes=" + nodes +
                '}';
    }
}
