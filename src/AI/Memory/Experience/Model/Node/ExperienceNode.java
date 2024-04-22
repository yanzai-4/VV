package AI.Memory.Experience.Model.Node;

// ##### 简介 #####
// 经验节点
// 用于储存情况样本和方法样本
// 可返回随机方法如需要
// 数据结构： situation-situation<weight=solution-solution:weight=solution-solution

// ##### 日志 #####
// 1.0 版本
// 添加对比方法
// done

import java.util.ArrayList;

public class ExperienceNode {

    // ##### 变量 #####
    // 情况样本
    private ExperienceNodeSituation situation;
    // 方法样本表
    private ArrayList<ExperienceNodeSolution> solutions;

    // ##### 构造函数 #####
    // 创建新节点
    public ExperienceNode(ArrayList<String> input_situation) {
        situation = new ExperienceNodeSituation(input_situation);
        solutions = new ArrayList<>();
    }

    // ##### 功能函数 #####
    // 返回情况样本
    public ExperienceNodeSituation getSituation() {
        return situation;
    }

    // 返回随机方法样本
    public ExperienceNodeSolution getSolution() {
        int randomNum = (int) (Math.random() * this.totalNum()) + 1;
        for (ExperienceNodeSolution temp_solution : solutions) {
            if (temp_solution.isActive()) {
                randomNum -= temp_solution.getWeight();
            }
            else {
                randomNum -= 1;
            }
            if (randomNum <= 0) {
                return temp_solution;
            }
        }
        return null;
    }

    // 返回所有方法
    public ArrayList<ExperienceNodeSolution> getAllSolution() {
        return solutions;
    }

    // 添加方法样本集
    public void setSolution(ArrayList<ExperienceNodeSolution> input_solution) {
        solutions = input_solution;
    }

    // 添加方法样本
    public void addSolution(ExperienceNodeSolution input_solution) {
        solutions.add(input_solution);
    }

    // 计算方法样本表权重总和
    private int totalNum() {
        int num = 0;
        for (ExperienceNodeSolution temp_solution : solutions) {
            if (temp_solution.isActive()) {
                num += temp_solution.getWeight();
            }
            else {
                num += 1;
            }
        }
        return num;
    }

    // 筛查失效方法样本
    public void check() {
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).isDead()) {
                solutions.remove(i);
                i--;
            }
        }
    }

    // 返回当前样本是否存活
    public boolean isDead() {
        return solutions.isEmpty();
    }

    // 返回当前样本摘要信息
    public String toInfo() {
        return situation.toInfo();
    }

    // 返回数据储存结构
    public String toData() {
        String text = "";
        for (ExperienceNodeSolution temp_solution : solutions) {
            text += temp_solution.toData() + ":";
        }
        return situation.toData() + "<" + text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceNode{" +
                "situation=" + situation +
                ", solutions=" + solutions +
                '}';
    }
}
