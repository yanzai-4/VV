package AI.Memory.Reminisce;

// ##### 简介 #####
// 回忆节点区
// 通过时间优先库储存修改回忆节点
// 读取，保存，排序
// 输出回忆链队列

// ##### 日志 #####
// 1.0 版本
// 完成简单构建
// 2.0 版本
// 增加缓冲区
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Function.ReminisceFuncLoad;
import AI.Memory.Reminisce.Function.ReminisceFuncSave;
import AI.Memory.Reminisce.Function.ReminisceFuncSort;
import AI.Memory.Reminisce.Model.Chain.ReminisceChain;
import AI.Memory.Reminisce.Function.ReminisceFuncSearch;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReminisceSector {

    // ##### 变量 #####
    // 记忆节点时间优先库
    private ArrayList<ReminisceNode> reminisceBase;
    // 记忆节点时间优先暂存库
    private ArrayList<ReminisceNode> reminisceSubBase;
    // 存取地址
    private String path;
    // 当前状态
    private Boolean status;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新的回忆分区
    public ReminisceSector(String input_path) {
        reminisceBase = new ArrayList<>();
        reminisceSubBase = new ArrayList<>();
        path = input_path;
        status = true;
    }

    // ##### 功能函数 #####
    // 修改路径
    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<ReminisceNode> getReminisceBase() {
        return reminisceBase;
    }

    // 添加新节点到时间优先库
    public boolean add(ArrayList<String> input_crumb, ArrayList<String> input_labelList) {
        try {
            ReminisceNode temp_node = new ReminisceNode(input_crumb);
            temp_node.addLabelList(input_labelList);
            reminisceSubBase.add(temp_node);
            debug.log("#ReminisceBase       <Add>          -Done: " + temp_node.toInfo());
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Add>          -Error");
        }
        return false;
    }

    // 添加临时节点到时间优先库
    public boolean addTemp(ArrayList<String> input_crumb, ArrayList<String> input_labelList) {
        try {
            ReminisceNode temp_node = new ReminisceNode(input_crumb, 0);
            temp_node.addLabelList(input_labelList);
            reminisceSubBase.add(temp_node);
            debug.log("#ReminisceBase       <AddTemp>      -Done: " + temp_node.toInfo());
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <AddTemp>      -Error");
        }
        return false;
    }

    // 手动添加节点
    public void addNode(ReminisceNode input_node) {
        try {
            reminisceBase.add(input_node);
            debug.log("#ReminisceBase       <Add>          -Done: " + input_node.toInfo());
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Add>          -Error");
        }
    }

    // 读取时间优先库所有内容从文档
    public boolean load() {
        try {
            debug.notice("#ReminisceBase       <Load>         -Start");
            new ReminisceFuncLoad(reminisceBase, path).start();
            debug.done("#ReminisceBase       <Load>         -Done");
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Load>         -Error");
            status = false;
        }
        return false;
    }

    // 保存时间优先库所有内容到文档
    public boolean save() {
        try {
            debug.notice("#ReminisceBase       <Save>         -Start");
            this.sort();
            new ReminisceFuncSave(reminisceBase, reminisceSubBase, path).start();
            debug.done("#ReminisceBase       <Save>         -Done");
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Save>         -Error");
            status = false;
        }
        return false;
    }

    // 合并保存时间优先库所有内容到文档
    public boolean mergeSave() {
        try {
            debug.notice("#ReminisceBase       <Save>         -Start");
            this.merge();
            this.deepSort();
            new ReminisceFuncSave(reminisceBase, path).start();
            debug.done("#ReminisceBase       <Save>         -Done");
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Save>         -Error");
            status = false;
        }
        return false;
    }

    // 合并主库和副库
    public void merge() {
        try {
            debug.notice("#ReminisceBase       <Merge>        -Start");
            reminisceBase.addAll(reminisceSubBase);
            reminisceSubBase.clear();
            debug.done("#ReminisceBase       <Merge>        -Done");
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Merge>        -Error");
            status = false;
        }
    }

    // 对副时间优先库进行排序
    public void sort() {
        try {
            debug.notice("#ReminisceBase       <Sort>         -Start");
            for(int i = 0; i < reminisceSubBase.size(); i++) {
                reminisceSubBase.get(i).lapse();
                if(reminisceSubBase.get(i).isDead()) {
                    reminisceSubBase.remove(i);
                    i--;
                }
            }
            reminisceSubBase.sort(new ReminisceFuncSort());
            debug.done("#ReminisceBase       <Sort>         -Done");
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Sort>         -Error");
            status = false;
        }
    }

    // 对整个时间优先库进行排序
    public void deepSort() {
        try {
            debug.notice("#ReminisceBase       <Sort>         -Start");
            for(int i = 0; i < reminisceBase.size(); i++) {
                if(reminisceBase.get(i).isDead()) {
                    reminisceBase.remove(i);
                    i--;
                }
            }
            reminisceBase.sort(new ReminisceFuncSort());
            debug.done("#ReminisceBase       <Sort>         -Done");
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Sort>         -Error");
            status = false;
        }
    }

    // 初始化认知池
    public boolean rebuild() {
        try {
            debug.notice("#ReminisceBase       <Rebuild>      -Start");
            reminisceBase.clear();
            reminisceSubBase.clear();
            debug.done("#ReminisceBase       <Rebuild>      -Done");
            return true;
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Rebuild>      -Error");
            status = false;
        }
        return false;
    }

    // 根据引索搜索节点并返回至链到队列
    public ReminisceChain search(ArrayList<String> input_tagList) {
        try {
            debug.log("#ReminisceBase       <Search>       -Start");
            ReminisceFuncSearch search = new ReminisceFuncSearch(reminisceBase, reminisceSubBase, input_tagList);
            search.start();
            return search.getResult();
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Search>       -Error");
        }
        return null;
    }

    // 返回当前区是否工作
    public boolean isAlive() {
        return status;
    }
}
