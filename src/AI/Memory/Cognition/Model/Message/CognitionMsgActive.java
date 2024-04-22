package AI.Memory.Cognition.Model.Message;

// ##### 简介 #####
// 认知节点节点枝重复搜索延时指令
// 继承超线程功能
// 用于决定节点枝重复搜索的时长

// ##### 日志 #####
// 1.0 版本
// 修改等待时间
// 2.0 版本
// 修正
// 2.1 版本
// 修改interrupt判断
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Node.CognitionNodeBranch;

public class CognitionMsgActive extends Thread {

    // ##### 变量 #####
    // 节点枝地址
    private CognitionNodeBranch node;
    // 等待时间
    // 30秒
    private final int time = 30000;
    // 节点信息
    private String info;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public CognitionMsgActive(CognitionNodeBranch input_node) {
        node = input_node;
        info = node.getInfo();
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        try {
            debug.log("#CognitionNet        <Node>         -Active: " + info);
            Thread.sleep(time);
            node.deActive();
            debug.log("#CognitionNet        <Node>         -DeActive: " + info);
        }
        catch (InterruptedException e) {
            debug.notice("#CognitionNet        <Node>         -Interrupt: " + info);
        }
        catch (Exception e) {
            debug.error("#CognitionNet        <Node>         -Error: " + info);
        }
    }
}
