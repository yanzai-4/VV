package AI.Mind.Editor;

// ##### 简介 #####
// 经验节点树编辑器
// 用于手动编辑经验节点树

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Mind.General.Timer;
import AI.Memory.Experience.ExperienceSector;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommand;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandCite;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandIdea;
import AI.Memory.Experience.Model.Node.ExperienceCommand.ExperienceCommandText;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;

import java.io.File;
import java.util.ArrayList;

public class ExperienceEditor {

    // ##### 变量 #####
    private static ExperienceSector editor;

    public static void main(String[] args) {
    }
    // ##### 主函数 #####
    public ExperienceEditor(String path) throws InterruptedException {
        editor = new ExperienceSector(path);

        // ##### 节点结 #####
        ArrayList<ExperienceNodeSolution> Say_Hi = new ArrayList<>();
        Say_Hi.add(toSolution(
                toCommand(
                        toText(toArray("hi","."))),
                "Say"));
        Say_Hi.add(toSolution(
                toCommand(
                        toText(toArray("hello","."))),
                "Say"));
        addNode(toArray("LISTEN", "hi", "."), Say_Hi);


        ArrayList<ExperienceNodeSolution> Listen_Question_About_My_What = new ArrayList<>();
        Listen_Question_About_My_What.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toText(toArray( "say", "what", "is", "my")),
                                toCiteA(toArray("is", "your"), toArray("?")))),
                        toText(toArray("."))),
                "Say"));
        addNode(toArray("LISTEN", "what", "is", "your", "ANY", "?"), Listen_Question_About_My_What);

        ArrayList<ExperienceNodeSolution> Listen_Question_About_User_What = new ArrayList<>();
        Listen_Question_About_User_What.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toText(toArray( "say", "what", "is", "your")),
                                toCiteA(toArray("is", "my"), toArray("?")))),
                        toText(toArray("."))),
                "Say"));
        addNode(toArray("LISTEN", "what", "is", "my", "ANY", "?"), Listen_Question_About_User_What);


        ArrayList<ExperienceNodeSolution> Listen_Question_About_What = new ArrayList<>();
        Listen_Question_About_What.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toText(toArray( "say", "what", "is")),
                                toCiteA(toArray("is"), toArray("?")))),
                        toText(toArray("."))),
                "Say"));
        addNode(toArray("LISTEN", "what", "is", "ANY", "?"), Listen_Question_About_What);


        ArrayList<ExperienceNodeSolution> Answer_About_Myself = new ArrayList<>();
        Answer_About_Myself.add(toSolution(
                toCommand(
                        toText(toArray("my")),
                        toCiteS(toArray("is", "my")),
                        toText(toArray("is")),
                        toIdea(toCommand(
                                toText(toArray("what", "is", "my")),
                                toCiteS(toArray("is", "my"))))),
                "Think"));
        addNode(toArray("THINK", "say","what", "is", "my", "ANY"), Answer_About_Myself);


        ArrayList<ExperienceNodeSolution> Answer_About_Thing = new ArrayList<>();
        Answer_About_Thing.add(toSolution(
                toCommand(
                        toCiteS(toArray("is")),
                        toText(toArray("is")),
                        toIdea(toCommand(
                                toText(toArray("what", "is")),
                                toCiteS(toArray("is"))))),
                "Think"));
        addNode(toArray("THINK", "say","what", "is", "ANY"), Answer_About_Thing);


        ArrayList<ExperienceNodeSolution> Reminisce_What = new ArrayList<>();
        Reminisce_What.add(toSolution(
                toCommand(
                        toText(toArray("what")),
                        toCiteS(toArray("what", "is"))),
                "Reminisce"));
        Reminisce_What.get(0).setRoot(true);
        addNode(toArray("THINK", "what", "is", "ANY"), Reminisce_What);


        ArrayList<ExperienceNodeSolution> Reflect_Answer_my_name = new ArrayList<>();
        Reflect_Answer_my_name.add(toSolution(
                toCommand(
                        toText(toArray("i", "am")),
                        toCiteS(toArray("is"))),
                "Think"));
        Reflect_Answer_my_name.add(toSolution(
                toCommand(
                        toText(toArray("my", "name", "is")),
                        toCiteS(toArray("is"))),
                "Think"));
        addNode(toArray("THINK", "reflect", "my", "name", "is", "ANY"), Reflect_Answer_my_name);


        ArrayList<ExperienceNodeSolution> Reflect_Answer_What_Is = new ArrayList<>();
        Reflect_Answer_What_Is.add(toSolution(
                toCommand(
                        toCiteA(toArray("reflect"), toArray("is")),
                        toText(toArray("is")),
                        toCiteS(toArray("is"))),
                "Think"));
        addNode(toArray("THINK", "reflect", "ANY", "is", "ANY"), Reflect_Answer_What_Is);


        ArrayList<ExperienceNodeSolution> Reflect_No_Answer = new ArrayList<>();
        Reflect_No_Answer.add(toSolution(
                toCommand(
                        toText(toArray("i", "do", "not", "know", "what", "is")),
                        toCiteA(toArray("reflect"), toArray("is"))),
                "Think"));
        addNode(toArray("THINK", "reflect", "ANY", "is", "NOMEMORY"), Reflect_No_Answer);


        ArrayList<ExperienceNodeSolution> Response_for_question = new ArrayList<>();
        Response_for_question.add(toSolution(
                toCommand(
                        toText(toArray("say", "something"))),
                "Except"));
        addNode(toArray("SAY", "ANY"), Response_for_question);


        ArrayList<ExperienceNodeSolution> Enhance_for_say = new ArrayList<>();
        Enhance_for_say.add(toSolution(
                toCommand(
                        toText(toArray("yes"))),
                "Enhance"));
        addNode(toArray("LISTEN", "yes", ".", "REPLY", "say", "something"), Enhance_for_say);


        ArrayList<ExperienceNodeSolution> Restrain_for_say = new ArrayList<>();
        Restrain_for_say.add(toSolution(
                toCommand(
                        toText(toArray("no"))),
                "Restrain"));
        addNode(toArray("LISTEN", "no", ".", "REPLY", "say", "something"), Restrain_for_say);


        // compare
        ArrayList<ExperienceNodeSolution> Compare = new ArrayList<>();
        Compare.add(toSolution(
                toCommand(
                        toCiteA(toArray("compare"), toArray("and"))),
                toCommand(
                        toCiteS(toArray("and"))),
                "Compare"));
        addNode(toArray("THINK", "compare", "ANY", "and", "ANY"), Compare);


        ArrayList<ExperienceNodeSolution> Compare_same = new ArrayList<>();
        Compare_same.add(toSolution(
                toCommand(
                        toText(toArray("same", "between")),
                        toIdea(toCommand(
                                toText(toArray("compare")),
                                toCiteS(toArray("between"))))),
                "Think"));
        addNode(toArray("THINK", "same", "between", "ANY", "and", "ANY"), Compare_same);


        ArrayList<ExperienceNodeSolution> Compare_diff = new ArrayList<>();
        Compare_diff.add(toSolution(
                toCommand(
                        toText(toArray("different", "between")),
                        toIdea(toCommand(
                                toText(toArray("compare")),
                                toCiteS(toArray("between"))))),
                "Think"));
        addNode(toArray("THINK", "different", "between", "ANY", "and", "ANY"), Compare_diff);


        ArrayList<ExperienceNodeSolution> Compare_no_reflect = new ArrayList<>();
        Compare_no_reflect.add(toSolution(
                toCommand(
                        toCiteA(toArray("between"), toArray("NORELARION")),
                        toText(toArray("has", "no", "relation"))),
                "Think"));
        addNode(toArray("THINK", "reflect", "ANY", "between", "ANY", "and", "ANY", "NORELATION"), Compare_no_reflect);


        ArrayList<ExperienceNodeSolution> Listen_Question_About_Compare = new ArrayList<>();
        Listen_Question_About_Compare.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toCiteA(toArray("what", "is"), toArray("?")))),
                        toText(toArray("."))),
                "Say"));
        addNode(toArray("LISTEN", "what", "is", "compare", "between", "ANY", "and", "ANY", "?"), Listen_Question_About_Compare);

        ArrayList<ExperienceNodeSolution> Compare_same_reflect = new ArrayList<>();
        Compare_same_reflect.add(toSolution(
                toCommand(
                        toCiteA(toArray("between"), toArray("SAMEAT")),
                        toText(toArray("are", "same", "at")),
                        toIdea(toCommand(
                                toCiteS(toArray("SAMEAT")),
                                toText(toArray("."))))),
                "Think"));
        addNode(toArray("THINK", "reflect","same", "between", "ANY", "and", "ANY", "SAMEAT", "ANY"), Compare_same_reflect);

        ArrayList<ExperienceNodeSolution> Compare_same_reflect_2 = new ArrayList<>();
        Compare_same_reflect_2.add(toSolution(
                toCommand(
                        toCiteA(toArray("between"), toArray("SAMEAT")),
                        toText(toArray("are", "same", "at")),
                        toIdea(toCommand(
                                toCiteA(toArray("SAMEAT"), toArray("DIFFAT")),
                                toText(toArray("."))))),
                "Think"));
        addNode(toArray("THINK", "reflect","same", "between", "ANY", "and", "ANY", "SAMEAT", "ANY", "DIFFAT", "ANY"), Compare_same_reflect_2);


        ArrayList<ExperienceNodeSolution> Compare_diff_reflect = new ArrayList<>();
        Compare_diff_reflect.add(toSolution(
                toCommand(
                        toCiteA(toArray("between"), toArray("DIFFAT")),
                        toText(toArray("are", "different", "at")),
                        toIdea(toCommand(
                                toCiteS(toArray("DIFFAT")),
                                toText(toArray("."))))),
                "Think"));
        addNode(toArray("THINK", "reflect","different", "between", "ANY", "and", "ANY", "DIFFAT", "ANY"), Compare_diff_reflect);


        ArrayList<ExperienceNodeSolution> Compare_diff_reflect_2 = new ArrayList<>();
        Compare_diff_reflect_2.add(toSolution(
                toCommand(
                        toCiteA(toArray("between"), toArray("SAMEAT")),
                        toText(toArray("are", "different", "at")),
                        toIdea(toCommand(
                                toCiteS(toArray("DIFFAT")),
                                toText(toArray("."))))),
                "Think"));
        addNode(toArray("THINK", "reflect","different", "between", "ANY", "and", "ANY", "SAMEAT", "ANY", "DIFFAT", "ANY"), Compare_diff_reflect_2);


        ArrayList<ExperienceNodeSolution> mono = new ArrayList<>();
        mono.add(toSolution(
                toCommand(
                        toCiteE(toArray("."))),
                "Think"));
        addNode(toArray("THINK", "ANY", "."), mono);

        ArrayList<ExperienceNodeSolution> add_and = new ArrayList<>();
        add_and.add(toSolution(
                toCommand(
                        toCiteE(toArray(",")),
                        toText(toArray("and")),
                        toCiteA(toArray(","), toArray("."))),
                "Think"));
        addNode(toArray("THINK", "ANY", ",", "ANY", "."), add_and);


        ArrayList<ExperienceNodeSolution> add_and_more = new ArrayList<>();
        add_and_more.add(toSolution(
                toCommand(
                        toCiteE(toArray(",")),
                        toText(toArray(",")),
                        toIdea(toCommand(
                                toCiteA(toArray(","), toArray(".")))),
                                toText(toArray("."))),
                "Think"));
        addNode(toArray("THINK", "ANY", ",","ANY", ",", "ANY", "."), add_and_more);


        ArrayList<ExperienceNodeSolution> unknow_word = new ArrayList<>();
        unknow_word.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toText(toArray("what", "is")),
                                toCiteS(toArray("UNKNOW")),
                                toText(toArray("mean"))))),
                "Think"));
        addNode(toArray("LISTEN", "UNKNOW", "ANY"), unknow_word);

        ArrayList<ExperienceNodeSolution> ask_unknow = new ArrayList<>();
        ask_unknow.add(toSolution(
                toCommand(
                        toText(toArray("what", "is")),
                        toCiteS(toArray("what", "is")),
                        toText(toArray("?"))),
                "Say"));
        addNode(toArray("THINK", "what", "is", "ANY", "mean"), ask_unknow);

        ArrayList<ExperienceNodeSolution> expect_answer = new ArrayList<>();
        expect_answer.add(toSolution(
                toCommand(
                        toText(toArray("mean", "for")),
                        toCiteA(toArray("what", "is"), toArray("."))),
                "Except"));
        addNode(toArray("SAY", "what", "is", "ANY", "mean","?"), expect_answer);

/*
        ArrayList<ExperienceNodeSolution> expect_answer2 = new ArrayList<>();
        expect_answer2.add(toSolution(
                toCommand(
                        toText(toArray("mean", "for")),
                        toCiteA(toArray("what", "is"), toArray("."))),
                "Except"));
        addNode(toArray("SAY", "i", "do", "not", "know", "what", "is", "ANY","."), expect_answer2);

 */

        ArrayList<ExperienceNodeSolution> try_to_remember_answer = new ArrayList<>();
        try_to_remember_answer.add(toSolution(
                toCommand(
                        toText(toArray("ok", ".")),
                        toIdea(toCommand(
                                toText(toArray("remember")),
                                toCiteA(toArray("mean", "for"), toArray("mean", "?")),
                                toText(toArray("mean")),
                                toCiteE(toArray(".", "REPLY"))))),
                "Say"));
        addNode(toArray("LISTEN", "ANY", ".", "REPLY", "mean", "for", "ANY", "mean","?"), try_to_remember_answer);


        ArrayList<ExperienceNodeSolution> try_to_remember_answer_2 = new ArrayList<>();
        try_to_remember_answer_2.add(toSolution(
                toCommand(
                        toText(toArray("ok", ".")),
                        toIdea(toCommand(
                                toText(toArray("remember")),
                                toCiteA(toArray("mean", "for"), toArray("mean", "?")),
                                toText(toArray("mean")),
                                toCiteA(toArray("is"), toArray(".", "REPLY"))))),
                "Say"));
        addNode(toArray("LISTEN", "ANY", "is", "ANY", ".","REPLY", "mean", "for", "ANY", "mean","?"), try_to_remember_answer_2);


        ArrayList<ExperienceNodeSolution> remember_answer = new ArrayList<>();
        remember_answer.add(toSolution(
                toCommand(
                        toIdea(toCommand(
                                toText(toArray("hear")),
                                toCiteS(toArray("mean"))))),
                toCommand(
                        toText(toArray("what")),
                        toCiteA(toArray("remember"), toArray("mean"))),
                "Remember"));
        addNode(toArray("THINK", "remember", "ANY", "mean", "ANY"), remember_answer);

        ArrayList<ExperienceNodeSolution> here_my = new ArrayList<>();
        here_my.add(toSolution(
                toCommand(
                        toText(toArray("your")),
                        toCiteA(toArray("my"), toArray("."))),
                "Think"));
        addNode(toArray("THINK", "hear", "my","ANY"), here_my);

        ArrayList<ExperienceNodeSolution> here_your = new ArrayList<>();
        here_your.add(toSolution(
                toCommand(
                        toText(toArray("my")),
                        toCiteA(toArray("your"), toArray("."))),
                "Think"));
        addNode(toArray("THINK", "hear", "your","ANY"), here_your);

        ArrayList<ExperienceNodeSolution> here_any = new ArrayList<>();
        here_any.add(toSolution(
                toCommand(
                        toCiteS(toArray("hear"))),
                "Think"));
        addNode(toArray("THINK", "hear", "ANY"), here_any);


        // ##### 结束 #####
        editor.save();
    }

    // ##### 功能函数 #####
    // 转换固定指令
    private static ExperienceCommandText toText(ArrayList<String> input_content) {
        ExperienceCommandText temp_command = new ExperienceCommandText();
        temp_command.setContent(input_content);
        return temp_command;
    }

    // 转换引用指令 只开头
    private static ExperienceCommandCite toCiteS(ArrayList<String> input_start) {
        ExperienceCommandCite temp_command = new ExperienceCommandCite();
        temp_command.setStart(input_start);
        return temp_command;
    }

    // 转换引用指令 只结尾
    private static ExperienceCommandCite toCiteE(ArrayList<String> input_end) {
        ExperienceCommandCite temp_command = new ExperienceCommandCite();
        temp_command.setEnd(input_end);
        return temp_command;
    }

    // 转换引用指令
    private static ExperienceCommandCite toCiteA(ArrayList<String> input_start, ArrayList<String> input_end) {
        ExperienceCommandCite temp_command = new ExperienceCommandCite();
        temp_command.setStart(input_start);
        temp_command.setEnd(input_end);
        return temp_command;
    }

    // 转换想法指令
    private static ExperienceCommandIdea toIdea(ArrayList<ExperienceCommand> input_idea) {
        ExperienceCommandIdea temp_command = new ExperienceCommandIdea();
        temp_command.setIdea(input_idea);
        return temp_command;
    }

    // 转换单一方法指令
    private static ExperienceNodeSolution toSolution(ArrayList<ExperienceCommand> input_solution, String input_type) {
        ExperienceNodeSolution temp_solution = new ExperienceNodeSolution(input_type);
        temp_solution.setSolution(input_solution);
        return temp_solution;
    }

    private static ExperienceNodeSolution toSolution(String input_type) {
        ExperienceNodeSolution temp_solution = new ExperienceNodeSolution(input_type);
        return temp_solution;
    }

    // 转换双重方法指令
    private static ExperienceNodeSolution toSolution(ArrayList<ExperienceCommand> input_solution, ArrayList<ExperienceCommand> input_subSolution, String input_type) {
        ExperienceNodeSolution temp_solution = new ExperienceNodeSolution(input_type);
        temp_solution.setSolution(input_solution);
        temp_solution.setSubSolution(input_subSolution);
        return temp_solution;
    }

    // 添加经验节点
    private static void addNode(ArrayList<String> input_situation, ArrayList<ExperienceNodeSolution> input_solutions) {
        editor.addNode(input_situation, input_solutions);
    }

    // 添加经验节点结
    private static void addKnot(ArrayList<String> input_situation, ArrayList<ExperienceNode> input_solutions) {
        editor.addKnot(input_situation, input_solutions);
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first, ExperienceCommand second) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first, ExperienceCommand second, ExperienceCommand third) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first, ExperienceCommand second, ExperienceCommand third, ExperienceCommand forth) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first, ExperienceCommand second, ExperienceCommand third, ExperienceCommand forth, ExperienceCommand fifth) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<ExperienceCommand> toCommand(ExperienceCommand first, ExperienceCommand second, ExperienceCommand third, ExperienceCommand forth, ExperienceCommand fifth, ExperienceCommand sixth) {
        ArrayList<ExperienceCommand> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        return array;
    }
    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        return array;
    }
    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth, String seventh) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        array.add(seventh);
        return array;
    }
    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth, String seventh, String eighth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        array.add(seventh);
        array.add(eighth);
        return array;
    }

    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth, String seventh, String eighth, String ninth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        array.add(seventh);
        array.add(eighth);
        array.add(ninth);
        return array;
    }
    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth, String seventh, String eighth, String ninth, String tenth) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        array.add(seventh);
        array.add(eighth);
        array.add(ninth);
        array.add(tenth);
        return array;
    }
    // 转换为Arraylist
    private static ArrayList<String> toArray(String first, String second, String third, String forth, String fifth, String sixth, String seventh, String eighth, String ninth, String tenth, String eleventh) {
        ArrayList<String> array = new ArrayList<>();
        array.add(first);
        array.add(second);
        array.add(third);
        array.add(forth);
        array.add(fifth);
        array.add(sixth);
        array.add(seventh);
        array.add(eighth);
        array.add(ninth);
        array.add(tenth);
        array.add(eleventh);
        return array;
    }
}
