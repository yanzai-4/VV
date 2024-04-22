package AI.Memory.Cognition.Function;

// ##### 简介 #####
// 单线程数据加载指令
// 对全部数据进行加载
// 包含初始化功能

// ##### 日志 #####
// 1.0 版本
// 跟进 2.0版本 MemoryNodeCrumb
// 1.1 版本
// 修改log信息
// 1.2 版本
// 删除节点权重
// 2.0 版本
// 增加分类读取功能
// 3.0 版本
// 修正
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Node.CognitionNode;
import AI.Memory.Cognition.Model.Node.CognitionNodeBranch;
import AI.Memory.Cognition.Model.Node.CognitionNodeLeaf;
import AI.Memory.Cognition.Model.Node.CognitionNodeRoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CognitionMsgLoad {

    // ##### 变量 #####
    // 节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 读取进度 25%, 50%, 75%
    int point_25;
    int point_50;
    int point_75;
    // 储存目标地址
    private String path;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新保存指令
    public CognitionMsgLoad(HashMap<String, CognitionNode> input_cognitionNet, String input_path) {
        cognitionNet = input_cognitionNet;
        path = input_path + "/CognitionNet.txt";
    }

    // ##### 功能函数 #####
    // 初始化读取任务
    public void start() throws IOException {
        File file = new File(path);
        FileReader road = new FileReader(file.getAbsoluteFile());
        BufferedReader reader = new BufferedReader(road);
        this.load(reader);
        reader.close();
        road.close();
    }

    // 执行读取步骤
    private void load(BufferedReader input_reader) throws IOException {
        String text = input_reader.readLine();
        int size = Integer.parseInt(text.split(":")[1]);
        int pointer = 0;
        this.init(size);
        text = input_reader.readLine();
        while (text != null) {
            this.valuation(text);
            this.progress(pointer);
            text = input_reader.readLine();
            pointer ++;
        }
    }

    // 根据类型创建对应节点
    private void valuation(String input_text){
        String[] node_field = input_text.split("#");
        String temp_info = node_field[0];
        String temp_type = node_field[1];
        switch (temp_type) {
            case "Branch" -> {
                CognitionNodeBranch temp_node = new CognitionNodeBranch(temp_info);
                String text_portList = node_field[2];
                if (!text_portList.contains("*")) {
                    String[] portList_field = text_portList.split("=");
                    for (String text_port : portList_field) {
                        String[] port_field = text_port.split("-");
                        temp_node.addPort(port_field[0], Integer.parseInt(port_field[1]));
                    }
                }
                cognitionNet.put(temp_info, temp_node);
            }
            case "Leaf" -> {
                CognitionNodeLeaf temp_node = new CognitionNodeLeaf(temp_info);
                cognitionNet.put(temp_info, temp_node);
            }
            case "Root" -> {
                String temp_code = node_field[2];
                CognitionNodeRoot temp_node = new CognitionNodeRoot(temp_info, temp_code);
                cognitionNet.put(temp_info, temp_node);
            }
        }
    }

    // 预载参数
    private void init(int input_size) {
        point_25 = input_size/4;
        point_50 = input_size/2;
        point_75 = 3*input_size/4;
    }

    // 反馈读取进度
    private void progress(int input_pointer){
        if (input_pointer == point_25) {
            debug.done("#CognitionNet        <Load>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#CognitionNet        <Load>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#CognitionNet        <Load>         -75%");
        }
    }
}
