package AI.Memory.Cognition.Function;

// ##### 简介 #####
// 多线程数据保存指令
// 对全部数据进行保存
// 返回报错信息

// ##### 日志 #####
// 1.1 版本
// 美化
// 1.2 版本
// 跟进 2.0版本 MemoryNodeCrumb
// 1.3 版本
// 修改log信息
// 2.0 版本
// 不保存节点芽信息
// 3.0 版本
// 修正
// done

import AI.Mind.General.Debug;
import AI.Memory.Cognition.Model.Node.CognitionType;
import AI.Memory.Cognition.Model.Node.CognitionNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CognitionMsgSave {

    // ##### 变量 #####
    // 节点哈希库
    private HashMap<String, CognitionNode> cognitionNet;
    // 读取目标地址
    private String path;
    // 哈希库大小
    private int size;
    // 保存进度 25%, 50%, 75%
    private int point_25;
    private int point_50;
    private int point_75;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public CognitionMsgSave(HashMap<String, CognitionNode> input_cognitionNet, String input_path) {
        cognitionNet = input_cognitionNet;
        size = cognitionNet.size();
        this.init(input_path);
    }

    // ##### 功能函数 #####
    // 运行步骤
    public void start() throws IOException {
        File file = new File(path);
        FileWriter road = new FileWriter(file.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(road);
        this.save(writer);
        writer.close();
        road.close();
    }

    // 执行保存步骤任务
    private void save(BufferedWriter input_writer) throws IOException {
        int pointer = 0;
        input_writer.write("CognitionNet_NodeNum:" + size);
        input_writer.newLine();
        for (CognitionNode temp_node: cognitionNet.values()) {
            if (temp_node.getType() != CognitionType.Bud) {
                input_writer.write(temp_node.toData());
                input_writer.newLine();
            }
            this.progress(pointer);
            pointer ++;
        }
    }

    // 预载参数
    private void init(String input_path) {
        point_25 = size / 4;
        point_50 = size / 2;
        point_75 = 3 * size / 4;
        path = input_path + "/CognitionNet.txt";
    }

    // 反馈保存进度
    private void progress(int input_pointer){
        if (input_pointer == point_25) {
            debug.done("#CognitionNet        <Save>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#CognitionNet        <Save>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#CognitionNet        <Save>         -75%");
        }
    }
}
