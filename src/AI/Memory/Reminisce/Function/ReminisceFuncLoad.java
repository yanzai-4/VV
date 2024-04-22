package AI.Memory.Reminisce.Function;

// ##### 简介 #####
// 数据读取指令
// 读取全部数据通过文件
// 返回报错信息

// ##### 日志 #####
// 1.0 版本
// 简单架构
// 2.0 版本
// 增加缓冲区
// 2.1 版本
// 完成标签
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReminisceFuncLoad {

    // ##### 变量 #####
    // 回忆节点时间优先库
    private ArrayList<ReminisceNode> reminisceBase;
    // 读取进度 25%, 50%, 75%
    int point_25;
    int point_50;
    int point_75;
    // 读取目标地址
    private String path;
    // 读取目标副地址
    private String subPath;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    public ReminisceFuncLoad(ArrayList<ReminisceNode> input_reminisceBase, String input_path) {
        reminisceBase = input_reminisceBase;
        path = input_path + "/ReminisceBase.txt";
        subPath = input_path + "/ReminisceSubBase.txt";
    }

    // ##### 功能函数 #####
    // 初始化读取任务
    public void start() throws IOException {
        File file = new File(path);
        File subFile = new File(subPath);
        FileReader road = new FileReader(file.getAbsoluteFile());
        FileReader subRoad = new FileReader(subFile.getAbsoluteFile());
        BufferedReader reader = new BufferedReader(road);
        BufferedReader subReader = new BufferedReader(subRoad);
        String mergeCheck = subReader.readLine();
        if(mergeCheck.contains("#")) {
            this.mergeLoad(reader, subReader);
        }
        else if(mergeCheck.contains("*")) {
            this.load(reader);
        }
        subReader.close();
        subRoad.close();
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

    private void mergeLoad(BufferedReader input_reader, BufferedReader input_subReader) throws IOException {
        String text = input_reader.readLine();
        int size = Integer.parseInt(text.split(":")[1]);
        text = input_subReader.readLine();
        size += Integer.parseInt(text.split(":")[1]);
        int pointer = 0;
        this.init(size);
        text = input_reader.readLine();
        while (text != null) {
            this.valuation(text);
            this.progress(pointer);
            text = input_reader.readLine();
            pointer ++;
        }
        text = input_subReader.readLine();
        while (text != null) {
            this.valuation(text);
            this.progress(pointer);
            text = input_subReader.readLine();
            pointer ++;
        }
    }

    // 根据类型创建对应节点
    private void valuation(String input_text) {
        String[] node_field = input_text.split("#");
        String[] crumb_field = node_field[2].split("-");
        ArrayList<String> temp_crumb = new ArrayList<>(Arrays.asList(crumb_field));
        ReminisceNode temp_node = new ReminisceNode(temp_crumb, Integer.parseInt(node_field[0]));
        String[] labelList_field = node_field[1].split("=");
        for(String temp_label : labelList_field) {
            String[] label_field = temp_label.split("-");
            temp_node.addLabel(label_field[0], Integer.parseInt(label_field[1]));
        }
        reminisceBase.add(temp_node);
    }

    // 预载参数
    private void init(int input_size) {
        point_25 = input_size/4;
        point_50 = input_size/2;
        point_75 = 3*input_size/4;
    }

    // 反馈读取进度
    private void progress(int input_pointer) {
        if (input_pointer == point_25) {
            debug.done("#ReminisceBase       <Load>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#ReminisceBase       <Load>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#ReminisceBase       <Load>         -75%");
        }
    }
}