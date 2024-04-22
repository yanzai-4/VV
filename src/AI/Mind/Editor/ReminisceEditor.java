package AI.Mind.Editor;

// ##### 简介 #####
// 回忆节点栈编辑器
// 用于手动编辑回忆节点栈

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Memory.Reminisce.Model.Node.ReminisceNode;
import AI.Memory.Reminisce.Model.Node.ReminisceNodeLabel;
import AI.Memory.Reminisce.ReminisceSector;

import java.util.ArrayList;

public class ReminisceEditor {

    // ##### 变量 #####
    private static ReminisceSector editor;

    // ##### 主函数 #####
    public ReminisceEditor(String path) throws InterruptedException {
        editor = new ReminisceSector(path);

        // ##### 记忆 #####
        addNode(toArray("what", "my",  "name"),
                toArray("vivi"));

        addNode(toArray("what", "vivi"),
                toArray("my", "name"));

        // ##### 结束 #####
        editor.save();
    }
    // ##### 功能函数 #####
    // 添加节点
    private static void addNode(ArrayList<String> input_label, ArrayList<String> input_reminisce) {
        ArrayList<ReminisceNodeLabel> list = new ArrayList<>();
        for (String temp : input_label) {
            list.add(new ReminisceNodeLabel(temp, 6));
        }
        ReminisceNode temp_node = new ReminisceNode(input_reminisce, 20);
        temp_node.setLabel(list);
        editor.addNode(temp_node);
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
}
