package AI.Memory.Cognition.Model.Message;

// ##### 简介 #####
// 认知节点检查节点芽权重指令
// 继承超线程功能
// 通过时长并修改节点状态

// ##### 日志 #####
// 1.0 版本
// 新增二次等待
// 2.0 版本
// 修正
// 2.1 版本
// 修改interrupt判断
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Node.CognitionNode;
import AI.Memory.Cognition.Model.Node.CognitionNodeBud;

import java.util.HashMap;

public class CognitionMsgCheck extends Thread {

    // ##### 变量 #####
    // 节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 节点芽地址
    private CognitionNodeBud node;
    // 节点信息
    private String info;
    // 等待时间
    // 3分 - 5分钟
    private final int time = 200000;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public CognitionMsgCheck(HashMap<String, CognitionNode> input_cognitionNet, CognitionNodeBud input_node) {
        cognitionNet = input_cognitionNet;
        node = input_node;
        info = node.getInfo();
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        try {
            if (this.check()) {
                debug.log("#CognitionNet        <Node>         -ToLeaf: " + info);
                return;
            }
            debug.log("#CognitionNet        <Node>         -Remove: " + info);
        }
        catch (InterruptedException e) {
            debug.notice("#CognitionNet        <Node>         -Interrupt: " + info);
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Node>         -Error: " + info);
        }
    }

    // 检查节点芽权重并修改节点状态
    private boolean check() throws InterruptedException {
        Thread.sleep(time / 2);
        if (node.getWeight() > 0) {
            if (node.getWeight() > 3) {
                node.turnLeaf();
                return true;
            }
            else {
                Thread.sleep(time);
                if (node.getWeight() > 5) {
                    node.turnLeaf();
                    return true;
                }
            }
        }
        node.delete();
        return false;
    }
}
