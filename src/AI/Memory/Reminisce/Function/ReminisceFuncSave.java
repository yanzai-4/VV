package AI.Memory.Reminisce.Function;

// ##### 简介 #####
// 数据保存指令
// 对全部数据进行保存
// 返回报错信息

// ##### 日志 #####
// 1.0 版本
// 简单架构
// 2.0 版本
// 增加缓冲区
// 3.0 版本
// 去多线程
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReminisceFuncSave {

    // ##### 变量 #####
    // 回忆节点时间优先库
    private ArrayList<ReminisceNode> reminisceBase;
    // 回忆节点时间优先暂存库
    private ArrayList<ReminisceNode> reminisceSubBase;
    // 节点优先库大小
    private int size;
    // 保存进度 25%, 50%, 75%
    private int point_25;
    private int point_50;
    private int point_75;
    // 储存目标地址
    private String path;
    // 储存目标副地址
    private String subPath;
    // 保存模式 false = 分区保存 true = 整合保存
    private boolean merge;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新保存指令 并合并库
    public ReminisceFuncSave(ArrayList<ReminisceNode> input_reminisceBase, String input_path) {
        reminisceBase = input_reminisceBase;
        size = reminisceBase.size();
        this.init(input_path);
        merge = true;
    }

    // 创建新保存指令
    public ReminisceFuncSave(ArrayList<ReminisceNode> input_reminisceBase, ArrayList<ReminisceNode> input_reminisceSubBase, String input_path) {
        reminisceBase = input_reminisceBase;
        reminisceSubBase = input_reminisceSubBase;
        size = reminisceBase.size() + reminisceSubBase.size();
        this.init(input_path);
        merge = false;
    }

    // ##### 功能函数 #####
    // 初始化保存任务
    public void start() throws IOException {
        File file = new File(path);
        File subFile = new File(subPath);
        FileWriter road = new FileWriter(file.getAbsoluteFile());
        FileWriter subRoad = new FileWriter(subFile.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(road);
        BufferedWriter subWriter = new BufferedWriter(subRoad);
        if (merge) {
            this.mergeSave(writer, subWriter);
        }
        else {
            this.save(writer, subWriter);
        }
        subWriter.close();
        subRoad.close();
        writer.close();
        road.close();
    }

    // 执行保存步骤任务
    private void save(BufferedWriter input_writer, BufferedWriter input_subWriter) throws IOException {
        int pointer = 0;
        input_writer.write("ReminisceBase_Node_Num:" + reminisceBase.size());
        input_writer.newLine();
        input_subWriter.write("Working#");
        input_subWriter.newLine();
        input_subWriter.write("ReminisceBase_SubNode_Num:" + reminisceSubBase.size());
        input_subWriter.newLine();
        for (ReminisceNode temp_node: reminisceBase) {
            input_writer.write(temp_node.toData());
            input_writer.newLine();
            this.progress(pointer);
            pointer ++;
        }
        for (ReminisceNode temp_node: reminisceSubBase) {
            input_subWriter.write(temp_node.toData());
            input_subWriter.newLine();
            this.progress(pointer);
            pointer ++;
        }
    }

    // 执行合并保存步骤任务
    private void mergeSave(BufferedWriter input_writer, BufferedWriter input_subWriter) throws IOException {
        int pointer = 0;
        input_writer.write("ReminisceBase_Node_Num:" + size);
        input_subWriter.write("Empty*");
        input_writer.newLine();
        for (ReminisceNode temp_node: reminisceBase) {
            input_writer.write(temp_node.toData());
            input_writer.newLine();
            this.progress(pointer);
            pointer ++;
        }
    }

    // 预载参数
    private void init(String input_path) {
        point_25 = size / 4;
        point_50 = size / 2;
        point_75 = 3 * size / 4;
        path = input_path + "/ReminisceBase.txt";
        subPath = input_path + "/ReminisceSubBase.txt";
    }

    // 反馈保存进度
    private void progress(int input_pointer) {
        if (input_pointer == point_25) {
            debug.done("#ReminisceBase       <Save>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#ReminisceBase       <Save>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#ReminisceBase       <Save>         -75%");
        }
    }
}
