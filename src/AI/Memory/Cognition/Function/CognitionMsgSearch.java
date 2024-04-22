package AI.Memory.Cognition.Function;

// ##### 简介 #####
// 认知节点搜索指令
// 继承超线程功能
// 搜索哈希库中相对应的节点到节点池

// ##### 日志 #####
// 1.0 版本
// 取消多态继承
// 2.0 版本
// 修改构造函数输入
// 3.0 版本
// 删除深搜索
// 4.0 版本
// 修正
// 5.0 版本
// 去多线程
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Chain.CognitionChain;
import AI.Memory.Cognition.Model.Node.CognitionNode;

import java.util.HashMap;

public class CognitionMsgSearch {

    // ##### 变量 #####
    // 认知节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 节点信息
    private String target;
    // 认知链
    private CognitionChain result;
    // 报错信息
    private String info;
    // 报错检查
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public CognitionMsgSearch(HashMap<String, CognitionNode> input_cognitionNet, String input_target) {
        cognitionNet = input_cognitionNet;
        target = input_target;
        result = null;
        info = input_target;
    }

    // ##### 功能函数 #####
    // 执行当前指令
    public void start() {
        try {
            if (this.search(cognitionNet.get(target))) {
                debug.log("#CognitionNet        <Search>       -Done: " + info);
                return;
            }
            debug.fail("#CognitionNet        <Search>       -NoResult: " + info);
        }
        catch (Exception e) {
            debug.error(e);
            debug.error("#CognitionNet        <Search>       -Error: " + info);
        }
    }

    // 执行认知节点搜索任务
    private boolean search(CognitionNode input_node) {
        if (input_node != null) {
            result = new CognitionChain(input_node);
            return true;
        }
        return false;
    }

    // 返回搜索结果
    public CognitionChain getResult() {
        return result;
    }

    // 返回打印信息
    public String getInfo() {
        return info;
    }
}

