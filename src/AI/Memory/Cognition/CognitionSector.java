package AI.Memory.Cognition;

// ##### 简介 #####
// 认知节点池
// 用于储存调度认知节点
// 可以通过功能来调节每个节点
// 自动读取并定时保存

// ##### 日志 #####
// 1.0 版本
// 完成所以报错信息
// 1.1 版本
// 自动激活改为主动激活
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Chain.CognitionChain;
import AI.Memory.Cognition.Function.CognitionMsgLoad;
import AI.Memory.Cognition.Function.CognitionMsgSave;
import AI.Memory.Cognition.Function.CognitionMsgSearch;
import AI.Memory.Cognition.Model.Node.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CognitionSector {

    // ##### 变量 #####
    // 认知节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 存取地址
    private String path;
    // 当前状态
    private Boolean status;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建认知池
    public CognitionSector(String input_path){
        cognitionNet = new HashMap<>();
        path = input_path;
        status = true;
    }

    // ##### 功能函数 #####
    // 修改路径
    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String, CognitionNode> getCognitionNet() {
        return cognitionNet;
    }

    // 插入新认知节点
    public boolean add(String input_node) {
        try {
            if(cognitionNet.containsKey(input_node)) {
                return false;
            }
            CognitionNode temp_node = new CognitionNodeBud(cognitionNet, input_node);
            cognitionNet.put(input_node, temp_node);
            debug.log("#CognitionNet        <Add>          -Done: " + input_node);
            return true;
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Add>          -Error " + input_node);
        }
        return false;
    }

    // 手动插入新节点
    public void addNode(CognitionNode input_node) {
        try {
            cognitionNet.put(input_node.getInfo(), input_node);
            debug.log("#CognitionNet        <Add>          -Done: " + input_node.getInfo());
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Add>          -Error " + input_node.getInfo());
        }
    }

    // 插入新端口给认知节点功能
    public void addPort(String input_node, String input_port) {
        try {
            CognitionNode temp_node = cognitionNet.get(input_node);
            switch (temp_node.getType()) {
                case Branch -> {
                    CognitionNodeBranch temp_nodeBranch = (CognitionNodeBranch) temp_node;
                    if(temp_nodeBranch.isFull()) {
                        temp_nodeBranch.removeWeekConnection();
                    }
                    temp_nodeBranch.addPort(input_port);
                    debug.log("#CognitionNet        <Port>         -done: " + input_node + "-" + input_port);
                }
                case Bud -> {
                    CognitionNodeBud temp_nodeBud = (CognitionNodeBud) temp_node;
                    if (input_node.equals(input_port)) {
                        temp_nodeBud.turnLeaf();
                        debug.log("#CognitionNet        <Port>         -ToLeaf: ");
                    }
                    else {
                        temp_nodeBud.turnBranch(input_port);
                        debug.log("#CognitionNet        <Port>         -ToBranch: ");
                    }
                    debug.log("#CognitionNet        <Port>         -done: " + input_node + "-" + input_port);
                }
                case Leaf -> {
                    CognitionNodeBranch temp_nodeBranch = new CognitionNodeBranch(input_node);
                    if (!input_node.equals(input_port)) {
                        temp_nodeBranch.addPort(input_port);
                        cognitionNet.put(temp_nodeBranch.getInfo(), temp_nodeBranch);
                        debug.log("#CognitionNet        <Port>         -ToBranch: ");
                        debug.log("#CognitionNet        <Port>         -done: " + input_node + "-" + input_port);
                    }
                }
                default -> debug.fail("#CognitionNet        <Port>         -Fail: " + input_node + "-" + input_port);
            }
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Port>         -Error: " + input_node + "-" + input_port);
        }
    }

    // 删除认知节点
    public boolean delete(String input_node) {
        try {
            if(cognitionNet.containsKey(input_node)) {
                cognitionNet.remove(input_node);
                debug.log("#CognitionNet        <Delete>       -Done: " + input_node);
                return true;
            }
            debug.fail("#CognitionNet        <Add>          -Fail: " + input_node);
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Delete>       -Fail: " + input_node);
        }
        return false;
    }

    // 搜索目标节点并返回链到队列
    public CognitionChain search(String input_node) {
        try {
            debug.log("#CognitionNet        <Search>       -Start");
            CognitionMsgSearch search = new CognitionMsgSearch(cognitionNet, input_node);
            search.start();
            return search.getResult();
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Search>       -Error");
        }
        return null;
    }


    // 读取认知网所有内容到池
    public void load() {
        try {
            debug.notice("#CognitionNet        <Load>         -Start");
            new CognitionMsgLoad(cognitionNet, path).start();
            debug.done("#CognitionNet        <Load>         -Done");
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Load>         -Error");
            status = false;
        }
    }

    // 保存认知网所有内容到文档
    public void save() {
        try {
            debug.notice("#CognitionNet        <Save>         -Start");
            new CognitionMsgSave(cognitionNet, path).start();
            debug.done("#CognitionNet        <Save>         -Done");
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Save>         -Error");
            status = false;
        }
    }

    // 初始化认知池
    public void rebuild() {
        try {
            debug.notice("#CognitionNet        <Rebuild>      -Start");
            cognitionNet.clear();
            debug.done("#CognitionNet        <Rebuild>      -Done");
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Rebuild>      -Error");
            status = false;
        }
    }

    // 返回当前区是否工作
    public boolean isAlive() {
        return status;
    }
}


