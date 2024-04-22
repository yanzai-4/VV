package AI.Memory.Experience;

// ##### 简介 #####
// 经验节点区
// 通过时间优先库储存修改回忆节点
// 读取，保存，排序
// 输出回忆链队列

// ##### 日志 #####
// 1.0 版本
// 完成简单构建
// 2.0 版本
// 增加副树
// 3.0 版本
// 增加指令
// done

import AI.Mind.General.Debug;
import AI.Memory.Experience.Function.ExperienceFuncLoad;
import AI.Memory.Experience.Function.ExperienceFuncSave;
import AI.Memory.Experience.Model.Chain.ExperienceChain;
import AI.Memory.Experience.Function.ExperienceMsgSearch;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeKnot;
import AI.Memory.Experience.Model.Node.ExperienceNodeSituation;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ExperienceSector {

    // ##### 变量 #####
    // 经验节点群树
    private ArrayList<ExperienceNodeKnot> experienceTree;
    // 单经验节点树
    private ArrayList<ExperienceNode> experienceSubTree;
    // 存取地址
    private String path;
    // 当前状态
    private Boolean status;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新的经验分区
    public ExperienceSector(String input_path) {
        experienceTree = new ArrayList<>();
        experienceSubTree = new ArrayList<>();
        path = input_path;
        status = true;
    }

    // ##### 功能函数 #####
    // 修改路径
    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<ExperienceNodeKnot> getExperienceTree() {
        return experienceTree;
    }

    public ArrayList<ExperienceNode> getExperienceSubTree() {
        return experienceSubTree;
    }

    // 添加新节点到时间优先库
    public boolean add(ArrayList<String> input_situation, ExperienceNodeSolution input_solution) {
        try {
            ExperienceNode new_node = new ExperienceNode(input_situation);
            new_node.addSolution(input_solution);
            ExperienceNodeKnot temp_knot = this.maxKnot(input_situation);
            if (temp_knot != null) {
                temp_knot.addNode(new_node);
                debug.log("#ExperienceTree      <Add>          -Follow: " + new_node.toInfo());
                debug.log("#ExperienceTree      <Add>          -Done: " + new_node.toInfo());
                return true;
            }
            ExperienceNode temp_node = this.maxNode(input_situation);
            if (temp_node != null) {
                if (temp_node.getSituation().isSame(input_situation)) {
                    temp_node.addSolution(input_solution);
                    debug.log("#ExperienceTree      <Add>          -Follow: " + new_node.toInfo());
                    debug.log("#ExperienceTree      <Add>          -Done: " + new_node.toInfo());
                    return true;
                }
                experienceTree.add(this.toKnot(temp_node, new_node));
                experienceSubTree.remove(temp_node);
                debug.log("#ExperienceTree      <Add>          -Merge: " + new_node.toInfo());
                debug.log("#ExperienceTree      <Add>          -Done: " + new_node.toInfo());
                return true;
            }
            experienceSubTree.add(new_node);
            debug.log("#ExperienceTree      <Add>          -Done: " + new_node.toInfo());
            return true;
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Add>          -Error");
        }
        return false;
    }

    // 手动添加经验节点结
    public void addKnot(ArrayList<String> input_situation, ArrayList<ExperienceNode> input_nodes) {
        try {
            ExperienceNodeKnot temp_knot = new ExperienceNodeKnot(input_situation);
            temp_knot.setNode(input_nodes);
            experienceTree.add(temp_knot);
            debug.log("#ExperienceTree      <Add>          -Done: " + temp_knot.toInfo());
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Add>          -Error");
        }
    }

    // 手动添加经验节点
    public void addNode(ArrayList<String> input_situation, ArrayList<ExperienceNodeSolution> input_solutions) {
        try {
            ExperienceNode temp_node = new ExperienceNode(input_situation);
            temp_node.setSolution(input_solutions);
            experienceSubTree.add(temp_node);
            debug.log("#ExperienceTree      <Add>          -Done: " + temp_node.toInfo());
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Add>          -Error");
        }
    }

    // 读取经验树所有内容从文档
    public void load() {
        try {
            debug.notice("#ExperienceTree      <Load>         -Start");
            new ExperienceFuncLoad(experienceTree, experienceSubTree, path).start();
            debug.done("#ExperienceTree      <Load>         -Done");
        }
        catch (Exception e) {
            debug.error(e);
            debug.error("#ExperienceTree      <Load>         -Error");
            status = false;
        }
    }

    // 保存经验树所有内容到文档
    public void save() {
        try {
            debug.notice("#ExperienceTree      <Save>         -Start");
            new ExperienceFuncSave(experienceTree, experienceSubTree, path).start();
            debug.done("#ExperienceTree      <Save>         -Done");
        }
        catch (Exception e) {
            debug.error(e);
            debug.error("#ExperienceTree      <Save>         -Error");
            status = false;
        }
    }

    // 初始化认知池
    public void rebuild() {
        try {
            debug.notice("#ExperienceTree      <Rebuild>      -Start");
            experienceTree.clear();
            experienceSubTree.clear();
            debug.done("#ExperienceTree      <Rebuild>      -Done");
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Rebuild>      -Error");
            status = false;
        }
    }

    // 根据情况搜索节点并返回至链到队列
    public ExperienceChain search(ArrayList<String> input_situation, int input_rate) {
        try {
            debug.log("#ExperienceTree      <Search>       -Start");
            ExperienceMsgSearch search = new ExperienceMsgSearch(experienceTree, experienceSubTree, input_situation, input_rate);
            search.start();
            return search.getResult();
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Search>       -Error");
        }
        return null;
    }

    // 获取节点结从主树
    private ExperienceNodeKnot maxKnot(ArrayList<String> input_situation) {
        int maxRate = 0;
        ExperienceNodeKnot maxKnot = null;
        for(ExperienceNodeKnot temp_knot : experienceTree) {
            int temp_rate = temp_knot.getSituation().match(input_situation);
            if(temp_rate > 70 && temp_rate > maxRate) {
                maxRate = temp_rate;
                maxKnot = temp_knot;
            }
        }
        if(maxRate != 0) {
            return  maxKnot;
        }
        return null;
    }

    // 获取节点从副树
    private ExperienceNode maxNode(ArrayList<String> input_situation) {
        int maxRate = 0;
        ExperienceNode maxNode = null;
        for(ExperienceNode temp_node : experienceSubTree) {
            int temp_rate = temp_node.getSituation().match(input_situation);
            if(temp_rate > 70 && temp_rate > maxRate) {
                maxRate = temp_rate;
                maxNode = temp_node;
            }
        }
        if(maxRate != 0) {
            return  maxNode;
        }
        return null;
    }

    // 合并节点为节点结
    private ExperienceNodeKnot toKnot(ExperienceNode input_node, ExperienceNode input_subNode) {
        ArrayList<String> temp_situation = this.mergeNode(input_node.getSituation(), input_subNode.getSituation());
        ExperienceNodeKnot temp_knot = new ExperienceNodeKnot(temp_situation);
        temp_knot.addNode(input_node);
        temp_knot.addNode(input_subNode);
        return temp_knot;
    }

    // 合并情况样本
    private ArrayList<String> mergeNode(ExperienceNodeSituation input_situation, ExperienceNodeSituation input_subSituation) {
        int gap = 0;
        boolean noResult;
        ArrayList<String> temp_situation = input_situation.getSituation();
        ArrayList<String> temp_subSituation = input_subSituation.getSituation();
        ArrayList<String> temp_result = new ArrayList<>();
        for(int i = 0; i < temp_situation.size(); i ++) {
            noResult = true;
            for (int j = i + gap; j < temp_subSituation.size(); j++) {
                if (temp_subSituation.get(j).equals(temp_situation.get(i))) {
                    temp_result.add(temp_situation.get(i));
                    gap += j - (i + gap);
                    noResult = false;
                    break;
                }
            }
            if (noResult) {
                gap -= 1;
            }
        }
        return temp_result;
    }

    // 返回当前区是否工作
    public boolean isAlive() {
        return status;
    }
}
