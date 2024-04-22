package AI.Memory.Cognition.Model.Node;

// ##### 简介 #####
// 认知节点枝
// 用于保存中间节点信息
// 通过链的方式储存端口信息标签
// 数据储存结构：info#type#a-1=b-2=c-3 / info#type#*

// ##### 日志 #####
// 3.0 版本
// 改为多态继承架构
// 3.1 版本
// 美化
// 4.0 版本
// 修正
// done


import AI.Memory.Cognition.Model.Message.CognitionMsgActive;
import AI.Mind.General.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CognitionNodeBranch extends CognitionNode {

    // ##### 继承 #####
    // 变量： String type, String info
    // 功能： getType(), getInfo(), getChain(), toData(), toString()

    // ##### 变量 #####
    // 连接端口集: 端口名称 port/ 权重 weight
    private HashMap<String, Integer> portList;
    // 节点降活线程
    private CognitionMsgActive timer;
    // 节点激活值
    private Boolean active;

    // ##### 构造函数 #####
    // 创建新节点
    public CognitionNodeBranch(String input_info) {
        super(input_info);
        portList = new HashMap<>();
        type = CognitionType.Branch;
        active = false;
        this.init();
    }

    // ##### 功能函数 #####
    // 初始化计时器
    private void init() {
        timer = new CognitionMsgActive(this);
    }

    // 判断激活值并返回认知链集
    public ArrayList<String> getInfoList() {
        try{
            if (active) {
                if (timer.isAlive()) {
                    timer.interrupt();
                    timer = new CognitionMsgActive(this);
                }
                timer.start();
                return this.getAllInfo();
            }
            else {
                if (portList.size() > 2) {
                    active = true;
                    timer = new CognitionMsgActive(this);
                    timer.start();
                }
                return this.getThreeInfo();
            }
        }
        catch (Exception e) {
            Debug debug = new Debug();
            debug.notice(e);
            debug.notice("here");
        }
        return null;
    }
/*
    // 返回当前节点优先认知链集
    private ArrayList<String> getPriorInfo() {
        ArrayList<String> temp_portList = new ArrayList<>();
        for (int i = 6; i > 0; i --) {
            for (Map.Entry<String, Integer> temp_set : portList.entrySet()) {
                if (temp_set.getValue() == i) {
                    temp_portList.add(temp_set.getKey());
                    return temp_portList;
                }
            }
        }
        return temp_portList;
    }

 */


    // 返回当前节点全部认知链集
    private ArrayList<String> getThreeInfo() {
        ArrayList<String> temp_portList = new ArrayList<>();
        int total = 0;
        for (int i = 6; i > 0; i --) {
            for (Map.Entry<String, Integer> temp_set : portList.entrySet()) {
                if (temp_set.getValue() == i) {
                    temp_portList.add(temp_set.getKey());
                    total ++;
                    if (total == 3){
                        return temp_portList;
                    }
                }
            }
        }
        return temp_portList;
    }

    private ArrayList<String> getAllInfo() {
        ArrayList<String> temp_portList = new ArrayList<>();
        for (Map.Entry<String, Integer> temp_set : portList.entrySet()) {
            temp_portList.add(temp_set.getKey());
        }
        return temp_portList;
    }

    // 加载现有连接端口
    public void addPort(String input_port, int input_weight) {
        portList.put(input_port, input_weight);
    }

    // 创建新连接端口
    // 并设定初始权重为 4
    public void addPort(String input_port) {
        portList.put(input_port, 4);
    }

    // 增加目标连接组端口权重
    public void addPortListWeight(ArrayList<String> input_portList) {
        for (String temp_port : input_portList) {
            this.addPortWeight(temp_port);
        }
    }

    // 降低目标连接组端口权重
    public void subPortListWeight(ArrayList<String> input_portList) {
        for (String temp_port : input_portList) {
            this.subPortWeight(temp_port);
        }
    }

    // 增加目标连接端口权重
    // 上限为6
    private void addPortWeight(String input_port) {
        if (portList.containsKey(input_port)) {
            int weight = portList.get(input_port);
            if (weight < 5) {
                weight += 1;
                portList.replace(input_port, weight);
            }
        }
    }

    // 降低目标连接端口权重
    // 下限为1
    private void subPortWeight(String input_port) {
        if (portList.containsKey(input_port)) {
            int weight = portList.get(input_port);
            if (weight > 0) {
                weight -= 1;
                portList.replace(input_port, weight);
            }
            else {
                portList.remove(input_port);
            }
        }
    }

    // 检查空连接端口集
    public boolean isEmpty() {
        return(portList.isEmpty());
    }

    // 检查满连接端口集
    public boolean isFull() {
        return(portList.size() >= 6);
    }

    // 执行降活
    public void deActive() {
        active = false;
    }

    // 移除弱连接
    public void removeWeekConnection() {
        String port = null;
        int min = 10;
        for (Map.Entry<String, Integer> temp_set : portList.entrySet()) {
            if (temp_set.getValue() < min) {
                min = temp_set.getValue();
                port = temp_set.getKey();
            }
        }
        if (port != null){
            portList.remove(port);
        }
    }

    // 返回当前节点保存信息
    @Override
    public String toData() {
        String text_portList = "";
        if (!portList.isEmpty()) {
            for (Map.Entry<String, Integer> temp_set : portList.entrySet()) {
                text_portList += (temp_set.getKey() + "-" + temp_set.getValue() + "=");
            }
            text_portList = text_portList.substring(0, text_portList.length() - 1);
        }
        else {
            text_portList += "*";
        }
        return info + "#" + type.name() + "#"  + text_portList;
    }

    @Override
    public String toString() {
        return "CognitionNodeBranch{" +
                "info='" + info + '\'' +
                ", type=" + type +
                ", portList=" + portList +
                '}';
    }
}
