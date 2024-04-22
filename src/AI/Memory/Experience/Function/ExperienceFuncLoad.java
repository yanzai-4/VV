package AI.Memory.Experience.Function;

// ##### 简介 #####
// 多线程数据保存指令
// 读取全部数据通过文件
// 返回报错信息

// ##### 日志 #####
// 1.0 版本
// 简单架构
// 2.0 版本
// 添加复杂方法
// pass

import AI.Mind.General.Debug;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommand;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandCite;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandIdea;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandText;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeKnot;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ExperienceFuncLoad {

    // ##### 变量 #####
    // 经验节点群树
    private ArrayList<ExperienceNodeKnot> experienceTree;
    // 单经验节点树
    private ArrayList<ExperienceNode> experienceSubTree;
    // 读取进度 25%, 50%, 75%
    int point_25;
    int point_50;
    int point_75;
    // 读取目标地址
    private String path;
    // 储存目标副地址
    private String subPath;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    public ExperienceFuncLoad(ArrayList<ExperienceNodeKnot> input_experienceTree, ArrayList<ExperienceNode> input_experienceSubTree, String input_path) {
        experienceTree = input_experienceTree;
        experienceSubTree = input_experienceSubTree;
        path = input_path + "/ExperienceTree.txt";
        subPath = input_path + "/ExperienceSubTree.txt";
    }

    // ##### 功能函数 #####
    // 初始化读取任务
    public void start() throws Exception {
        File file = new File(path);
        File subFile = new File(subPath);
        FileReader road = new FileReader(file.getAbsoluteFile());
        FileReader subRoad = new FileReader(subFile.getAbsoluteFile());
        BufferedReader reader = new BufferedReader(road);
        BufferedReader subReader = new BufferedReader(subRoad);
        this.load(reader, subReader);
        subReader.close();
        subRoad.close();
        reader.close();
        road.close();
    }

    // 执行读取步骤
    private void load(BufferedReader input_reader, BufferedReader input_subReader) throws Exception {
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
            this.subValuation(text);
            this.progress(pointer);
            text = input_subReader.readLine();
            pointer ++;
        }
    }

    // 根据类型创建对应节点
    private void valuation(String input_text) throws Exception {
        String[] knot_field = input_text.split("#");
        String[] knotSituation_field = knot_field[0].split("-");
        ArrayList<String> temp_situation = new ArrayList<>();
        temp_situation.addAll(Arrays.asList(knotSituation_field));
        String[] nodes_field = knot_field[1].split(">");
        ExperienceNodeKnot temp_knot = new ExperienceNodeKnot(temp_situation);
        for (String text_node : nodes_field) {
            temp_knot.addNode(this.toNode(text_node));
        }
        experienceTree.add(temp_knot);
    }

    // 根据类型创建对应节点
    private void subValuation(String input_text) throws Exception {
        experienceSubTree.add(this.toNode(input_text));
    }

    // 转换文本为节点
    private ExperienceNode toNode(String input_text) throws Exception {
        String[] node_field = input_text.split("<");
        String[] situation_field = node_field[0].split("-");
        ArrayList<String> temp_nodeSituation = new ArrayList<>();
        temp_nodeSituation.addAll(Arrays.asList(situation_field));
        ExperienceNode temp_node = new ExperienceNode(temp_nodeSituation);
        String[] nodeSolutions_field = node_field[1].split(":");
        for (String temp_nodeSolution : nodeSolutions_field) {
            temp_node.addSolution(this.toSolution(temp_nodeSolution));
        }
        return temp_node;
    }

    // 转换文本为方法
    private ExperienceNodeSolution toSolution(String input_text) throws Exception {
        String[] solution_field = input_text.split("&");
        ExperienceNodeSolution temp_solution = new ExperienceNodeSolution(solution_field[0], Integer.parseInt(solution_field[1]));
        if (solution_field[2].equals("T")) {
            temp_solution.setRoot(true);
        }
        String[] command_field = solution_field[3].split(";");
        for (String temp_command : command_field) {
            temp_solution.addSolution(this.toCommand(temp_command));
        }
        if (solution_field[0].equals("Learn") || solution_field[0].equals("Compare") || solution_field[0].equals("Relate") || solution_field[0].equals("Remember")) {
            String[] subCommand_field = solution_field[4].split(";");
            for (String temp_command : subCommand_field) {
                temp_solution.addSubSolution(this.toCommand(temp_command));
            }
        }
        return temp_solution;
    }

    // 转换文本为指令
    private ExperienceCommand toCommand(String input_text) {
        if (input_text.startsWith("Idea")) {
            ExperienceCommandIdea temp_command = new ExperienceCommandIdea();
            String[] idea_field = input_text.substring(5).split("~");
            for (String temp_idea : idea_field) {
                temp_command.addIdea(this.toCommand(temp_idea));
            }
            return temp_command;
        }
        String[] content_field = input_text.split("=");
        if (content_field[0].equals("Cite")) {
            ExperienceCommandCite temp_command = new ExperienceCommandCite();
            if (!content_field[1].startsWith("*")) {
                String[] cite_field = content_field[1].split("-");
                for (String temp_cite : cite_field) {
                    temp_command.addStart(temp_cite);
                }
            }
            if (!content_field[2].startsWith("*")) {
                String[] cite_field = content_field[2].split("-");
                for (String temp_cite : cite_field) {
                    temp_command.addEnd(temp_cite);
                }
            }
            return temp_command;
        }
        if (content_field[0].equals("Text")) {
            ExperienceCommandText temp_command = new ExperienceCommandText();
            String[] text_field = content_field[1].split("-");
            for (String temp_text : text_field) {
                temp_command.addContent(temp_text);
            }
            return temp_command;
        }
        return null;
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
            debug.done("#ExperienceTree      <Load>         -25%");
        }
        else if (input_pointer == point_50) {
            debug.done("#ExperienceTree      <Load>         -50%");
        }
        else if (input_pointer == point_75) {
            debug.done("#ExperienceTree      <Load>         -75%");
        }
    }
}
