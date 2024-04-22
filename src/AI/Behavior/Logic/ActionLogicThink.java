package AI.Behavior.Logic;

// ##### 简介 #####
// 思考逻辑
// 思考并执行任务逻辑
// 根据条件修改记忆

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import AI.Memory.Cognition.CognitionSector;
import AI.Memory.Experience.ExperienceSector;
import AI.Memory.Experience.Model.Node.ExperienceCommand.*;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Memory.MemoryChain;
import AI.Memory.Reminisce.Model.Chain.ReminisceChain;
import AI.Memory.Reminisce.ReminisceSector;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class ActionLogicThink {

    // ##### 变量 #####
    // 认知分区
    private CognitionSector cognitionSector;
    // 经验分区
    private ExperienceSector experienceSector;
    // 回忆分区
    private ReminisceSector reminisceSector;

    // ##### 构造函数 #####
    // 创建新的思考逻辑
    public ActionLogicThink(CognitionSector input_cognitionSector, ExperienceSector input_experienceSector, ReminisceSector input_reminisceSector) {
        cognitionSector = input_cognitionSector;
        experienceSector = input_experienceSector;
        reminisceSector = input_reminisceSector;
    }

    // 转换想法为语句
    public ArrayList<String> getIdea(ExperienceCommand input_command, ArrayList<String> input_origin, ArrayList<String> input_cognition) {
        ExperienceCommandIdea temp_idea = (ExperienceCommandIdea) input_command;
        ArrayList<String> new_list = new ArrayList<>();
        for (ExperienceCommand temp_command : temp_idea.getIdea()) {
            switch (temp_command.getType()) {
                case Cite -> {
                    new_list.addAll(this.getCite(temp_command, input_origin, input_cognition));
                }
                case Text -> {
                    new_list.addAll(this.getText(temp_command));
                }
            }
        }
        return new_list;
    }

    // 转换引用为语句
    public ArrayList<String> getCite(ExperienceCommand input_command, ArrayList<String> input_origin, ArrayList<String> input_cognition) {
        ExperienceCommandCite temp_cite = (ExperienceCommandCite) input_command;
        int startNum = 0;
        int endNum = input_cognition.size() - 1;
        ArrayList<String> new_list = new ArrayList<>();
        if (temp_cite.hasStart()) {
            for (int i = 0; i < input_cognition.size(); i ++) {
                if (input_cognition.get(i).equals(temp_cite.getStart().get(0))) {
                    if (this.matchCite(input_cognition, temp_cite.getStart(), i)) {
                        startNum = i + temp_cite.getStart().size() - 1;
                        break;
                    }
                }
            }
        }
        if (temp_cite.hasEnd()) {
            for (int i = startNum; i < input_cognition.size(); i ++) {
                if (input_cognition.get(i).equals(temp_cite.getEnd().get(0))) {
                    if (this.matchCite(input_cognition, temp_cite.getEnd(), i)) {
                        endNum = i - 1;
                        break;
                    }
                }
            }
        }
        for (int i = startNum; i < endNum; i ++) {
            new_list.add(input_origin.get(i));
        }
        return new_list;
    }

    // 定位引用位置
    private boolean matchCite(ArrayList<String> text, ArrayList<String> target, int index) {
        for (int i = 0; i < target.size(); i ++) {
            if (!text.get(index + i).equals(target.get(i))) {
                return false;
            }
        }
        return true;
    }

    // 转换文字为语句
    public ArrayList<String> getText(ExperienceCommand input_command) {
        ExperienceCommandText temp_text = (ExperienceCommandText) input_command;
        ArrayList<String> temp_content = new ArrayList<>();
        for (String temp_word : temp_text.getContent()) {
            temp_content.add(temp_word);
        }
        return temp_content;
    }

    // 学习新经验
    public boolean toLearn(ArrayList<String> conclusion, ArrayList<String> subConclusion) {
        String temp_act = subConclusion.get(0);
        if (temp_act.equals("relate") || temp_act.equals("learn") || temp_act.equals("compare")) {
            return false;
        }
        ArrayList<ExperienceCommand> new_solution = new ArrayList<>();
        boolean hasIdea = false;
        boolean hasCite = false;
        subConclusion.remove(0);
        ArrayList<String> temp_text = new ArrayList<>();
        ArrayList<ExperienceCommand> temp_idea = new ArrayList<>();
        for (String temp_word : subConclusion) {
            if (hasIdea) {
                if (hasCite) {
                    if (temp_word.equals("CITEEND")) {
                        hasCite = false;
                        temp_idea.add(this.toCite(conclusion, temp_text));
                        temp_text = new ArrayList<>();
                    }
                    else {
                        temp_text.add(temp_word);
                    }
                }
                else {
                    if (temp_word.equals("CITESTART")) {
                        hasCite = true;
                        if (temp_text.size() != 0) {
                            temp_idea.add(this.toText(temp_text));
                            temp_text = new ArrayList<>();
                        }
                    }
                    else if (temp_word.equals("IDEAEND")) {
                        hasIdea = false;
                        if (temp_text.size() != 0) {
                            temp_idea.add(this.toText(temp_text));
                        }
                        new_solution.add(this.toIdea(temp_idea));
                        temp_text = new ArrayList<>();
                        temp_idea = new ArrayList<>();
                    }
                    else {
                        temp_text.add(temp_word);
                    }
                }
            }
            else if (hasCite) {
                if (temp_word.equals("CITEEND")) {
                    hasCite = false;
                    new_solution.add(this.toCite(conclusion, temp_text));
                    temp_text = new ArrayList<>();
                }
                else {
                    temp_text.add(temp_word);
                }
            }
            else {
                if (temp_word.equals("IDEASTART")) {
                    hasIdea = true;
                    if (temp_text.size() != 0) {
                        new_solution.add(this.toText(temp_text));
                        temp_text = new ArrayList<>();
                    }
                }
                else if (temp_word.equals("CITESTART")) {
                    hasCite = true;
                    if (temp_text.size() != 0) {
                        new_solution.add(this.toText(temp_text));
                        temp_text = new ArrayList<>();
                    }
                }
                else {
                    temp_text.add(temp_word);
                }
            }
        }
        if (temp_text.size() != 0) {
            new_solution.add(this.toText(temp_text));
        }
        if (new_solution.size() == 0) {
            return false;
        }
        ExperienceNodeSolution temp_solution = new ExperienceNodeSolution(temp_act);
        temp_solution.setSolution(new_solution);
        experienceSector.add(conclusion, temp_solution);
        return true;
    }

    // 转换语句为思想
    private ExperienceCommandIdea toIdea(ArrayList<ExperienceCommand> input_idea) {
        ExperienceCommandIdea temp_idea = new ExperienceCommandIdea();
        temp_idea.setIdea(input_idea);
        return temp_idea;
    }

    // 转换语句为引用
    private ExperienceCommandCite toCite(ArrayList<String> input_situation, ArrayList<String> input_text) {
        ExperienceCommandCite temp_cite = new ExperienceCommandCite();
        int size = input_text.size();
        int index = 0;
        for (int i = 0; i < input_situation.size(); i++) {
            if (input_situation.get(i).equals(input_text.get(index))) {
                index++;
            }
            else {
                index = 0;
                if (input_situation.get(i).equals(input_text.get(index))) {
                    index++;
                }
            }
            if(index == size) {
                if (i > size - 1) {
                    ArrayList<String> temp_list = new ArrayList<>();
                    for (int j = 0; j < i - (size - 1); j++) {
                        temp_list.add(input_situation.get(j));
                    }
                    temp_cite.setStart(temp_list);
                }
                if (i != input_situation.size() - 1) {
                    ArrayList<String> temp_list = new ArrayList<>();
                    for (int j = i + 1; j < input_situation.size(); j ++) {
                        temp_list.add(input_situation.get(j));
                    }
                    temp_cite.setEnd(temp_list);
                }
                break;
            }
        }
        return temp_cite;
    }

    // 转换语句为文字
    private ExperienceCommandText toText(ArrayList<String> input_text) {
        ExperienceCommandText temp_text = new ExperienceCommandText();
        temp_text.setContent(input_text);
        return temp_text;
    }

    // 关联新认知
    public void toRelate(String input_left, String input_right) {
        cognitionSector.addPort(input_left, input_right);
    }

    public void toRemember(ArrayList<String> conclusion, ArrayList<String> subConclusion) {
        ArrayList<String> temp_label = new ArrayList<>(conclusion);
        temp_label.addAll(subConclusion);
        reminisceSector.add(conclusion, temp_label);
    }

    // 返回对比认知
    public ArrayList<String> toCompare(String input_left, String input_right) {
        ArrayList<String> new_list = new ArrayList<>();
        ArrayList<String> sameConnection_list = new ArrayList<>();
        ArrayList<String> diffConnection_list = new ArrayList<>();
        ArrayList<String> left_connection = cognitionSector.search(input_left).getConnection();
        ArrayList<String> right_connection = cognitionSector.search(input_right).getConnection();
        if (left_connection.contains(input_right)) {
            new_list.add(input_left);
            new_list.add("is");
            new_list.add(input_right);
            return new_list;
        }
        if (right_connection.contains(input_left)) {
            new_list.add(input_right);
            new_list.add("is");
            new_list.add(input_left);
            return new_list;
        }
        left_connection.remove(input_left);
        right_connection.remove(input_right);
        for (String temp_left : left_connection) {
            for (String temp_right : right_connection) {
                if (temp_left.equals(temp_right)) {
                    sameConnection_list.add(temp_left);
                }
                else {
                    for (String temp_leftExtend : cognitionSector.search(temp_left).getConnection()) {
                        for (String temp_rightExtend : cognitionSector.search(temp_right).getConnection()) {
                            if (!temp_leftExtend.equals(temp_left) && temp_leftExtend.equals(temp_rightExtend)) {
                                diffConnection_list.add(temp_leftExtend);
                                diffConnection_list.add(temp_left);
                                diffConnection_list.add(temp_right);
                            }
                        }
                    }
                }
            }
        }
        if (sameConnection_list.isEmpty() && diffConnection_list.isEmpty()) {
            new_list.add(input_right);
            new_list.add("and");
            new_list.add(input_left);
            new_list.add("NORELATION");
            System.out.println(new_list);
            return new_list;
        }
        if (!sameConnection_list.isEmpty()) {
            new_list.add(input_right);
            new_list.add("and");
            new_list.add(input_left);
            new_list.add("SAMEAT");
            for (String temp_same : sameConnection_list) {
                new_list.add(temp_same);
                new_list.add(",");
            }
            new_list.remove(new_list.size() - 1);
        }
        if (!diffConnection_list.isEmpty()) {
            if (sameConnection_list.isEmpty()) {
                new_list.add(input_right);
                new_list.add("and");
                new_list.add(input_left);
            }
            new_list.add("DIFFAT");
            for (int i = 0; i < diffConnection_list.size(); i += 3) {
                new_list.add(diffConnection_list.get(i));
                new_list.add(",");
            }
            new_list.remove(new_list.size() - 1);
        }
        return new_list;
    }

    // 返回回忆信息
    public ArrayList<String> toReminisce(ArrayList<MemoryChain> input_chainList, ArrayList<String> input_conclusion) {
        ReminisceChain temp_chain = reminisceSector.search(input_conclusion);
        ArrayList<String> new_list = new ArrayList<>();
        input_chainList.add(temp_chain);
        if (temp_chain != null) {
            new_list.addAll(temp_chain.getReminisce());
            return new_list;
        }
        new_list.add("NOMEMORY");
        return new_list;
    }

    // 引导回复内容
    public ArrayList<String> toFeedback(ArrayList<String> input_conclusion, ArrayList<String> input_sentence) {
        ArrayList<String> new_list = new ArrayList<>();
        new_list.addAll(input_sentence);
        new_list.add("REPLY");
        new_list.addAll(input_conclusion);
        return new_list;
    }

    // 引导未回复内容
    public ArrayList<String> toNoFeedback(ArrayList<String> input_conclusion) {
        ArrayList<String> new_list = new ArrayList<>();
        new_list.add("NOREPLY");
        new_list.addAll(input_conclusion);
        return new_list;
    }
}
