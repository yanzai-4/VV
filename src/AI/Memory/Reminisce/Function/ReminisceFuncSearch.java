package AI.Memory.Reminisce.Function;

// ##### 简介 #####
// 回忆节点搜索指令
// 继承超线程功能
// 检索回忆库中相对应的节点到链池

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 2.0
// 重命名
// 3.0 版本
// 增加浅搜索
// 4.0 版本
// 去多线程
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Model.Chain.ReminisceChain;
import AI.Memory.Reminisce.Model.Message.ReminisceMsgCheck;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.util.ArrayList;

public class ReminisceFuncSearch {

    // ##### 变量 #####
    // 回忆节点时间优先库
    private ArrayList<ReminisceNode> reminisceBase;
    // 回忆节点时间优先暂存库
    private ArrayList<ReminisceNode> reminisceSubBase;
    // 检索标签
    private ArrayList<String> tagList;
    // 回忆链池
    private ReminisceChain result;
    // 打断标签
    private boolean interrupt;
    // 报错信息
    private String info;
    // 报错开关
    private Debug debug = new Debug();


    // ##### 构造函数 #####
    // 创建新指令
    public ReminisceFuncSearch(ArrayList<ReminisceNode> input_reminisceBase, ArrayList<ReminisceNode> input_reminisceSubBase, ArrayList<String> input_tagList) {
        reminisceBase = input_reminisceBase;
        reminisceSubBase = input_reminisceSubBase;
        tagList = input_tagList;
        result = null;
        interrupt = false;
        info = this.toInfo(tagList);
    }

    // ##### 功能函数 #####
    // 执行当前指令
    public void start() {
        try {
            if (this.search()) {
                debug.done("#ReminisceBase       <Search>       -Done: " + info);
                return;
            }
            ReminisceMsgCheck check = new ReminisceMsgCheck(this);
            check.start();
            if (this.deepSearch()) {
                check.interrupt();
                debug.done("#ReminisceBase       <Search>       -Done: " + info);
                return;
            }
            check.interrupt();
            debug.fail("#ReminisceBase       <Search>       -Fail: " + info);
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Search>       -Error: " + info);
        }
    }

    // 执行回忆节点浅搜索任务
    private boolean search() {
        for (ReminisceNode temp_node : reminisceSubBase) {
            if (temp_node.isActive() && temp_node.match(tagList)) {
                result = new ReminisceChain(temp_node, tagList);
                return true;
            }
        }
        return false;
    }

    // 执行回忆节点深搜索任务
    private boolean deepSearch() {
        for (ReminisceNode temp_node : reminisceBase) {
            if (interrupt) {
                return false;
            }
            if (temp_node.isActive() && temp_node.match(tagList)) {
                result = new ReminisceChain(temp_node, tagList);
                return true;
            }
        }
        return false;
    }


    // 打断当前搜索
    public void interrupt() {
        interrupt = true;
    }

    // 返回搜索结果
    public ReminisceChain getResult() {
        return result;
    }

    // 返回打印信息
    public String getInfo() {
        return info;
    }

    // 返回检索标签信息
    private String toInfo(ArrayList<String> input_tagList) {
        String text = "";
        for (String temp_teg : input_tagList) {
            text += (temp_teg + ":");
        }
        return text.substring(0, text.length() - 1);
    }
}
