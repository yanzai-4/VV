package AI.Memory.Experience.Function;

// ##### 简介 #####
// 经验节点搜索指令
// 继承超线程功能
// 检索经验树中相对应的节点到链池

// ##### 日志 #####
// 1.0 版本
// 构筑完成
// 2.0 版本
// 增加单节点树
// 3.0 版本
// 去多线程
// done

import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Mind.General.Debug;
import AI.Memory.Experience.Model.Chain.ExperienceChain;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeKnot;

import java.util.ArrayList;

public class ExperienceMsgSearch {

    // ##### 变量 #####
    // 经验树主树
    private ArrayList<ExperienceNodeKnot> experienceTree;
    // 经验副主树
    private ArrayList<ExperienceNode> experienceSubTree;
    // 检索标签
    private ArrayList<String> situation;
    // 检索结果
    private ExperienceChain result;
    // 报错信息
    private String info;

    private int rate;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public ExperienceMsgSearch(ArrayList<ExperienceNodeKnot> input_experienceTree, ArrayList<ExperienceNode> input_experienceSubTree, ArrayList<String> input_situation, int input_rate) {
        experienceTree = input_experienceTree;
        experienceSubTree = input_experienceSubTree;
        situation = input_situation;
        result = null;
        rate = input_rate;
        info = this.toInfo(input_situation);
    }

    // ##### 功能函数 #####
    // 执行当前指令
    public void start() {
        try {
            if (this.search()) {
                debug.log("#ExperienceTree      <Search>       -Done: " + info);
                return;
            }
            debug.log("#ExperienceTree      <Search>       -Fail: " + info);
        }
        catch (Exception e) {
            debug.error(e);
            debug.error("#ExperienceTree      <Search>       -Error: " + info);
        }
    }

    // 执行回忆节点搜索任务
    private boolean search() {
        int maxRate = rate;
        boolean first = true;
        ExperienceNode maxNode= null;
        ExperienceNodeKnot maxKnot= null;
        for(ExperienceNodeKnot temp_knot : experienceTree) {
            int temp_rate = temp_knot.getSituation().match(situation);
            if (temp_rate > maxRate) {
                maxRate = temp_rate;
                maxKnot = temp_knot;
                first = true;
            }
        }
        for(ExperienceNode temp_node : experienceSubTree) {
            try{
                int temp_rate = temp_node.getSituation().match(situation);
                if (temp_rate > maxRate) {
                    maxRate = temp_rate;
                    maxNode = temp_node;
                    first = false;
                }
            }
            catch (Exception e) {
                debug.notice(e);
            }
        }
        if (maxNode != null || maxKnot != null) {
            if (first) {
                ExperienceNode temp_node= maxKnot.getNode(situation);
                result = new ExperienceChain(temp_node, situation, maxRate);
                return true;
            }
            result = new ExperienceChain(maxNode, situation, maxRate);
            return true;
        }
        return false;
    }

    // 返回搜索结果
    public ExperienceChain getResult() {
        return result;
    }

    // 返回打印信息
    public String getInfo() {
        return info;
    }

    // 返回检索标签信息
    private String toInfo(ArrayList<String> input_tagList) {
        String text = "";
        for (int i = 1; i < situation.size(); i ++) {
            text += situation.get(i) + "_";
        }
        return text.substring(0, text.length() - 1);
    }
}