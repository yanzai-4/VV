package AI.Memory.Experience.Function;

// ##### 简介 #####
// 数据读取指令
// 对全部数据进行保存
// 返回报错信息

// ##### 日志 #####
// 1.0 版本
// 简单架构
// done

import AI.Mind.General.Debug;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeKnot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExperienceFuncSave {

    // ##### 变量 #####
    // 经验节点群树
    private ArrayList<ExperienceNodeKnot> experienceTree;
    // 单经验节点树
    private ArrayList<ExperienceNode> experienceSubTree;
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
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新保存指令
    public ExperienceFuncSave(ArrayList<ExperienceNodeKnot> input_experienceTree, ArrayList<ExperienceNode> input_experienceSubTree, String input_path) {
        experienceTree = input_experienceTree;
        experienceSubTree = input_experienceSubTree;
        size = experienceTree.size() + experienceSubTree.size();
        this.init(input_path);
    }

    // ##### 功能函数 #####
    // 运行步骤
    public void start() throws IOException {
        File file = new File(path);
        File subFile = new File(subPath);
        FileWriter road = new FileWriter(file.getAbsoluteFile());
        FileWriter subRoad = new FileWriter(subFile.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(road);
        BufferedWriter subWriter = new BufferedWriter(subRoad);
        this.save(writer, subWriter);
        subWriter.close();
        subRoad.close();
        writer.close();
        road.close();
    }

    // 执行保存步骤任务
    private void save(BufferedWriter input_writer, BufferedWriter input_subWriter) throws IOException {
        int pointer = 0;
        input_writer.write("ExperienceTree_NodeNum:" + experienceTree.size());
        input_writer.newLine();
        input_subWriter.write("ExperienceSubTree_NodeNum:" + experienceSubTree.size());
        input_subWriter.newLine();
        for (ExperienceNodeKnot temp_knot: experienceTree) {
            temp_knot.check();
            if (temp_knot.isDead()) {
                experienceTree.remove(temp_knot);
            }
            else {
                input_writer.write(temp_knot.toData());
                input_writer.newLine();
            }
            this.progress(pointer);
            pointer ++;
        }
        for (ExperienceNode temp_node: experienceSubTree) {
            if (temp_node.isDead()) {
                experienceSubTree.remove(temp_node);
            }
            else {
                input_subWriter.write(temp_node.toData());
                input_subWriter.newLine();
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
        path = input_path + "/ExperienceTree.txt";
        subPath = input_path + "/ExperienceSubTree.txt";
    }

    // 反馈保存进度
    private void progress(int input_pointer){
        if (input_pointer == point_25) {
            debug.done("#ExperienceTree      <Save>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#ExperienceTree      <Save>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#ExperienceTree      <Save>         -75%");
        }
    }
}
