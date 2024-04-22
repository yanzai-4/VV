package AI.Mind.Editor;

// ##### 简介 #####
// 认知节点网编辑器
// 用于手动编辑节点网

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Mind.General.Timer;
import AI.Memory.Cognition.CognitionSector;
import AI.Memory.Cognition.Model.Node.CognitionNodeBranch;
import AI.Memory.Cognition.Model.Node.CognitionNodeLeaf;
import AI.Memory.Cognition.Model.Node.CognitionNodeRoot;

import java.io.File;
import java.util.ArrayList;

public class CognitionEditor {

    // ##### 变量 #####
    private static CognitionSector editor;

    // ##### 主函数 #####
    public CognitionEditor(String path) throws InterruptedException {
        editor = new CognitionSector(path);

        // ##### 根节点 #####
        // ##### 特殊 #####
        // color // r-y-b-s
        // 透明
        addRoot("COLOR_0_0_0_0", "&color");
        // 白
        addRoot("COLOR_0_0_0_1", "&color");
        // 青
        addRoot("COLOR_0_0_1_0", "&color");
        // 蓝
        addRoot("COLOR_0_0_1_1", "&color");
        // 浅黄
        addRoot("COLOR_0_1_0_0", "&color");
        // 黄
        addRoot("COLOR_0_1_0_1", "&color");
        // 草绿
        addRoot("COLOR_0_1_1_0", "&color");
        // 绿
        addRoot("COLOR_0_1_1_1", "&color");
        // 肉
        addRoot("COLOR_1_0_0_0", "&color");
        // 红
        addRoot("COLOR_1_0_0_1", "&color");
        // 粉
        addRoot("COLOR_1_0_1_0", "&color");
        // 紫
        addRoot("COLOR_1_0_1_1", "&color");
        // 橙
        addRoot("COLOR_1_1_0_0", "&color");
        // 棕
        addRoot("COLOR_1_1_0_1", "&color");
        // 灰色
        addRoot("COLOR_1_1_1_0", "&color");
        // 黑色
        addRoot("COLOR_1_1_1_1", "&color");

        // taste
        // 甜
        addRoot("TASTE_6", "&taste");
        // 鲜
        addRoot("TASTE_5", "&taste");
        // 咸
        addRoot("TASTE_4", "&taste");
        // 酸
        addRoot("TASTE_3", "&taste");
        // 辣
        addRoot("TASTE_2", "&taste");
        // 苦
        addRoot("TASTE_1", "&taste");


        // 触感-范围-压力
        // 无
        addRoot("TOUCH_0_0_0", "&haptic");
        // 触
        addRoot("TOUCH_0_0_1", "&haptic");
        // 痒
        addRoot("TOUCH_0_1_0", "&haptic");
        // 软
        addRoot("TOUCH_0_1_1", "&haptic");
        // 滑
        addRoot("TOUCH_1_0_0", "&haptic");
        // 平
        addRoot("TOUCH_1_0_1", "&haptic");
        // 钝
        addRoot("TOUCH_1_1_0", "&haptic");
        // 硬
        addRoot("TOUCH_1_1_1", "&haptic");
        // 毛
        addRoot("TOUCH_2_0_0", "&haptic");
        // 糙
        addRoot("TOUCH_2_0_1", "&haptic");
        // 尖
        addRoot("TOUCH_2_1_0", "&haptic");
        // 疼
        addRoot("TOUCH_2_1_1", "&haptic");

        // 指令类
        addRoot("DIFFAT", "&code");
        addRoot("SAMEAT", "&code");
        addRoot("NORELARION", "&code");
        addRoot("NOMEMORY", "&code");
        addRoot("NOREPLY", "&code");
        addRoot("UNKNOW", "&code");
        addRoot("REPLY", "&code");
        addRoot("LISTEN", "&code");
        addRoot("THINK", "&code");
        addRoot("SAY", "&code");
        addRoot("ANY", "&code");

        // ##### 两级 #####
        // sound
        addRoot("SOUND_5", "&sound");
        addRoot("SOUND_4", "&sound");
        addRoot("SOUND_3", "&sound");
        addRoot("SOUND_2", "&sound");
        addRoot("SOUND_1", "&sound");

        // comfort to discomfort
        addRoot("FEEL_5", "&feel");
        addRoot("FEEL_4", "&feel");
        addRoot("FEEL_3", "&feel");
        addRoot("FEEL_2", "&feel");
        addRoot("FEEL_1", "&feel");

        // happy to sad
        addRoot("MOOD_5", "&mood");
        addRoot("MOOD_4", "&mood");
        addRoot("MOOD_3", "&mood");
        addRoot("MOOD_2", "&mood");
        addRoot("MOOD_1", "&mood");

        // excite to ease
        addRoot("EMOTION_5", "&emotion");
        addRoot("EMOTION_4", "&emotion");
        addRoot("EMOTION_3", "&emotion");
        addRoot("EMOTION_2", "&emotion");
        addRoot("EMOTION_1", "&emotion");

        // bright to dark
        addRoot("BRIGHT_5", "&brightness");
        addRoot("BRIGHT_4", "&brightness");
        addRoot("BRIGHT_3", "&brightness");
        addRoot("BRIGHT_2", "&brightness");
        addRoot("BRIGHT_1", "&brightness");

        // large to small
        addRoot("SIZE_5", "&size");
        addRoot("SIZE_4", "&size");
        addRoot("SIZE_3", "&size");
        addRoot("SIZE_2", "&size");
        addRoot("SIZE_1", "&size");

        // high to low
        addRoot("ALTITUDE_5", "&altitude");
        addRoot("ALTITUDE_4", "&altitude");
        addRoot("ALTITUDE_3", "&altitude");
        addRoot("ALTITUDE_2", "&altitude");
        addRoot("ALTITUDE_1", "&altitude");

        // long to short
        addRoot("LENGTH_5", "&length");
        addRoot("LENGTH_4", "&length");
        addRoot("LENGTH_3", "&length");
        addRoot("LENGTH_2", "&length");
        addRoot("LENGTH_1", "&length");

        // wild to narrow
        addRoot("WIDTH_5", "&width");
        addRoot("WIDTH_4", "&width");
        addRoot("WIDTH_3", "&width");
        addRoot("WIDTH_2", "&width");
        addRoot("WIDTH_1", "&width");

        // far to near
        addRoot("DISTANCE_5", "&distance");
        addRoot("DISTANCE_4", "&distance");
        addRoot("DISTANCE_3", "&distance");
        addRoot("DISTANCE_2", "&distance");
        addRoot("DISTANCE_1", "&distance");

        // high to low
        addRoot("PITCH_5", "&pitch");
        addRoot("PITCH_4", "&pitch");
        addRoot("PITCH_3", "&pitch");
        addRoot("PITCH_2", "&pitch");
        addRoot("PITCH_1", "&pitch");

        // hot to cold
        addRoot("TEMP_5", "&temperature");
        addRoot("TEMP_4", "&temperature");
        addRoot("TEMP_3", "&temperature");
        addRoot("TEMP_2", "&temperature");
        addRoot("TEMP_1", "&temperature");

        // wet to dey
        addRoot("MOISTURE_5", "&moisture");
        addRoot("MOISTURE_4", "&moisture");
        addRoot("MOISTURE_3", "&moisture");
        addRoot("MOISTURE_2", "&moisture");
        addRoot("MOISTURE_1", "&moisture");

        // good to bad
        addRoot("SMELL_5", "&smell");
        addRoot("SMELL_4", "&smell");
        addRoot("SMELL_3", "&smell");
        addRoot("SMELL_2", "&smell");
        addRoot("SMELL_1", "&smell");

        // ##### 阶梯 #####
        // heave to light to nothing
        addRoot("WEIGHT_5", "&weight");
        addRoot("WEIGHT_4", "&weight");
        addRoot("WEIGHT_3", "&weight");
        addRoot("WEIGHT_2", "&weight");
        addRoot("WEIGHT_1", "&weight");

        // fast to slow to stop
        addRoot("SPEED_5", "&speed");
        addRoot("SPEED_4", "&speed");
        addRoot("SPEED_3", "&speed");
        addRoot("SPEED_2", "&speed");
        addRoot("SPEED_1", "&speed");

        // more to less to nothing
        addRoot("VALUE_5", "&value");
        addRoot("VALUE_4", "&value");
        addRoot("VALUE_3", "&value");
        addRoot("VALUE_2", "&value");
        addRoot("VALUE_1", "&value");

        // loud to quiet to silence
        addRoot("VOLUME_5", "&volume");
        addRoot("VOLUME_4", "&volume");
        addRoot("VOLUME_3", "&volume");
        addRoot("VOLUME_2", "&volume");
        addRoot("VOLUME_1", "&volume");

        // same to different
        addRoot("COMPARE_5", "&compare");
        addRoot("COMPARE_4", "&compare");
        addRoot("COMPARE_3", "&compare");
        addRoot("COMPARE_2", "&compare");
        addRoot("COMPARE_1", "&compare");

        // yes not sure no
        addRoot("STATUS_3", "&status");
        addRoot("STATUS_2", "&status");
        addRoot("STATUS_1", "&status");

        // ##### 叶节点 #####
        // ##### 基础 #####
        addLeaf(".");
        addLeaf(",");
        addLeaf("?");
        addLeaf("!");

        addLeaf("yes");
        addLeaf("no");
        addLeaf("have");
        addLeaf("with");
        addLeaf("without");
        addLeaf("include");
        addLeaf("exclude");
        addLeaf("and");
        addLeaf("or");
        addLeaf("be");
        addLeaf("will");
        addLeaf("should");
        addLeaf("about");
        addLeaf("for");
        addLeaf("of");
        addLeaf("by");
        addLeaf("to");
        addLeaf("do");
        addLeaf("go");
        addLeaf("trough");
        addLeaf("any");
        addLeaf("some");
        addLeaf("part");

        addLeaf("what");
        addLeaf("why");
        addLeaf("where");
        addLeaf("who");
        addLeaf("whose");
        addLeaf("which");
        addLeaf("when");
        addLeaf("how");

        addLeaf("this");
        addLeaf("that");
        addLeaf("here");
        addLeaf("there");
        addLeaf("these");
        addLeaf("those");

        addBranch("i", toArray("i", "me"));
        addBranch("me", toArray("i", "me"));
        addBranch("my", toArray("my", "mine"));
        addBranch("mine", toArray("my", "mine"));
        addLeaf("myself");
        addLeaf("you");
        addBranch("yours", toArray("your", "yours"));
        addBranch("your", toArray("your", "yours"));
        addLeaf("yourself");
        addLeaf("yourselves");
        addBranch("him", toArray("he", "him"));
        addBranch("he", toArray("he", "him"));
        addLeaf("his");
        addLeaf("himself");
        addBranch("her", toArray("she", "her"));
        addBranch("she", toArray("she", "her"));
        addLeaf("hers");
        addLeaf("herself");
        addLeaf("it");
        addLeaf("its");
        addLeaf("itself");
        addBranch("us", toArray("we", "us"));
        addBranch("we", toArray("we", "us"));
        addBranch("ours", toArray("our", "ours"));
        addBranch("our", toArray("our", "ours"));
        addLeaf("ourselves");
        addBranch("they", toArray("they", "them"));
        addBranch("them", toArray("they", "they"));
        addLeaf("theirs");
        addLeaf("themselves");

        // ##### 词汇 #####
        addLeaf("hi");
        addLeaf("bye");
        addLeaf("color");
        addLeaf("taste");
        addLeaf("sound");
        addLeaf("direction");
        addLeaf("position");
        addLeaf("act");
        addLeaf("feeling");
        addLeaf("mood");
        addLeaf("emotion");
        addLeaf("brightness");
        addLeaf("size");
        addLeaf("altitude");
        addLeaf("length");
        addLeaf("width");
        addLeaf("distance");
        addLeaf("pitch");
        addLeaf("temperature");
        addLeaf("smell");
        addLeaf("weight");
        addLeaf("speed");
        addLeaf("value");
        addLeaf("volume");
        addLeaf("compare");
        addLeaf("moisture");
        addLeaf("process");
        addLeaf("vision");
        addLeaf("number");
        addLeaf("letter");
        addLeaf("order");
        addLeaf("act");
        addLeaf("name");
        addLeaf("reflect");
        addLeaf("vivi");
        addLeaf("know");

        addLeaf("reply");
        addLeaf("answer");
        addLeaf("question");
        addLeaf("something");

        addLeaf("between");
        addLeaf("pet");
        addLeaf("animal");

        addLeaf("mean");
        addLeaf("ok");
        addLeaf("remember");

        addLeaf("hear");
        /*reflect



        addLeaf("");
        addLeaf("");
        addLeaf("");
        addLeaf("");
        addLeaf("");
        addLeaf("");

         */


        // ##### 枝节点 #####
        addBranch("red", toArray("COLOR_1_0_0_1", "red", "color"));
        addBranch("blue", toArray("COLOR_0_0_1_1", "blue", "color"));
        addBranch("green", toArray("COLOR_0_1_1_1", "green", "color"));
        addBranch("yellow", toArray("COLOR_0_1_0_1", "yellow", "color"));
        addBranch("brown", toArray("COLOR_1_1_0_1", "brown", "color"));
        addBranch("purple", toArray("COLOR_1_0_1_1", "purple", "color"));
        addBranch("orange", toArray("COLOR_1_1_0_0", "orange", "color"));
        addBranch("pink", toArray("COLOR_1_0_1_0", "pink", "color"));
        addBranch("white", toArray("COLOR_0_0_0_1", "white", "color"));
        addBranch("black", toArray("COLOR_1_1_1_1", "black", "color"));
        addBranch("grey", toArray("COLOR_1_1_1_0", "grey", "color"));

        addBranch("sour", toArray("TASTE_3", "sour", "taste"));
        addBranch("sweet", toArray("TASTE_6", "sweet", "taste"));
        addBranch("bitter", toArray("TASTE_1", "bitter", "taste"));
        addBranch("spicy", toArray("TASTE_2", "spicy", "taste"));
        addBranch("salty", toArray("TASTE_4", "salty", "taste"));
        addBranch("yummy", toArray("TASTE_5", "yummy", "taste"));

        addBranch("touch", toArray("TOUCH_0_0_1", "touch"));
        addBranch("pain", toArray("TOUCH_2_1_1", "pain", "touch"));
        addBranch("itch", toArray("TOUCH_0_1_0", "itch", "touch"));
        addBranch("soft", toArray("TOUCH_0_1_1", "soft", "touch"));
        addBranch("hard", toArray("TOUCH_1_1_1", "hard", "touch"));
        addBranch("flat", toArray("TOUCH_1_0_1", "flat", "touch"));
        addBranch("slide", toArray("TOUCH_1_0_0", "slide", "touch"));
        addBranch("rough", toArray("TOUCH_2_0_1", "rough", "touch"));
        addBranch("sharp", toArray("TOUCH_2_1_0", "sharp", "touch"));
        addBranch("blunt", toArray("TOUCH_1_1_0", "blunt", "touch"));

        addBranch("melody", toArray("SOUND_5", "melody", "sound"));
        addBranch("music", toArray("SOUND_4", "music", "sound"));
        addBranch("noise", toArray("SOUND_1", "noise", "sound"));

        addBranch("comfort", toArray("FEEL_4", "comfort", "feeling"));
        addBranch("discomfort", toArray("FEEL_2", "discomfort", "feeling"));

        addBranch("happy", toArray("FEEL_4", "mood", "feeling"));
        addBranch("sad", toArray("FEEL_2", "mood", "feeling"));

        addBranch("excite", toArray("EMOTION_4", "emotion", "feeling"));
        addBranch("ease", toArray("EMOTION_2", "emotion", "feeling"));

        addBranch("bright", toArray("BRIGHT_4", "brightness", "vision"));
        addBranch("dark", toArray("BRIGHT_2", "brightness", "vision"));

        addBranch("huge", toArray("SIZE_5", "huge", "size"));
        addBranch("large", toArray("SIZE_4", "large", "size"));
        addBranch("big", toArray("SIZE_4", "big", "size"));
        addBranch("small", toArray("SIZE_2", "small", "size"));
        addBranch("little", toArray("SIZE_2", "little", "size"));
        addBranch("tiny", toArray("SIZE_1", "tiny", "size"));

        addBranch("high", toArray("ALTITUDE_4", "high", "altitude", "PITCH_4", "pitch"));
        addBranch("middle", toArray("ALTITUDE_3", "middle", "altitude", "PITCH_3", "pitch"));
        addBranch("low", toArray("ALTITUDE_2", "low", "altitude", "PITCH_2", "pitch"));

        addBranch("long", toArray("LENGTH_4", "long", "length"));
        addBranch("short", toArray("LENGTH_2", "short", "length"));

        addBranch("wild", toArray("WIDTH_4", "wild", "width"));
        addBranch("narrow", toArray("WIDTH_2", "wild", "width"));

        addBranch("far", toArray("DISTANCE_4", "far", "distance"));
        addBranch("near", toArray("DISTANCE_2", "near", "distance"));

        addBranch("hot", toArray("TEMP_4", "hot", "temperature"));
        addBranch("warm", toArray("TEMP_3", "warm", "temperature"));
        addBranch("cold", toArray("TEMP_2", "cold", "temperature"));

        addBranch("fragrant", toArray("SMELL_4", "fragrant", "smell"));
        addBranch("perfume", toArray("SMELL_5", "perfume", "smell"));
        addBranch("stink", toArray("SMELL_1", "stink", "smell"));

        addBranch("heave", toArray("WEIGHT_4", "heave", "weight"));
        addBranch("light", toArray("WEIGHT_3", "light", "weight"));
        addBranch("empty", toArray("WEIGHT_1", "empty", "weight"));

        addBranch("fast", toArray("SPEED_4", "fast", "speed"));
        addBranch("slow", toArray("SPEED_3", "slow", "speed"));
        addBranch("stop", toArray("SPEED_1", "stop", "speed"));

        addBranch("all", toArray("VALUE_5", "more", "value"));
        addBranch("more", toArray("VALUE_4", "more", "value"));
        addBranch("less", toArray("VALUE_3", "less", "value"));
        addBranch("nothing", toArray("VALUE_1", "nothing", "value"));

        addBranch("loud", toArray("VOLUME_4", "loud", "volume"));
        addBranch("quiet", toArray("VOLUME_2", "quiet", "volume"));
        addBranch("silence", toArray("VOLUME_1", "silence", "volume"));

        addBranch("same", toArray("COMPARE_5", "same", "compare"));
        addBranch("different", toArray("COMPARE_1", "different", "compare"));

        addBranch("1", toArray("1", "number"));
        addBranch("2", toArray("2", "number"));
        addBranch("3", toArray("3", "number"));
        addBranch("4", toArray("4", "number"));
        addBranch("5", toArray("5", "number"));
        addBranch("6", toArray("6", "number"));
        addBranch("7", toArray("7", "number"));
        addBranch("8", toArray("8", "number"));
        addBranch("9", toArray("9", "number"));
        addBranch("0", toArray("0", "number"));

        addBranch("a", toArray("a", "letter", "one"));
        addBranch("b", toArray("b", "letter"));
        addBranch("c", toArray("c", "letter"));
        addBranch("d", toArray("d", "letter"));
        addBranch("e", toArray("e", "letter"));
        addBranch("f", toArray("f", "letter"));
        addBranch("g", toArray("g", "letter"));
        addBranch("h", toArray("h", "letter"));
        addBranch("i", toArray("i", "letter"));
        addBranch("j", toArray("j", "letter"));
        addBranch("k", toArray("k", "letter"));
        addBranch("l", toArray("l", "letter"));
        addBranch("m", toArray("m", "letter"));
        addBranch("n", toArray("n", "letter"));
        addBranch("o", toArray("o", "letter"));
        addBranch("p", toArray("p", "letter"));
        addBranch("q", toArray("q", "letter"));
        addBranch("r", toArray("r", "letter"));
        addBranch("s", toArray("s", "letter"));
        addBranch("t", toArray("t", "letter"));
        addBranch("u", toArray("u", "letter"));
        addBranch("v", toArray("v", "letter"));
        addBranch("w", toArray("w", "letter"));
        addBranch("x", toArray("x", "letter"));
        addBranch("y", toArray("y", "letter"));
        addBranch("z", toArray("z", "letter"));

        addBranch("zero", toArray("0", "number"));
        addBranch("one", toArray("1", "number"));
        addBranch("two", toArray("2", "number"));
        addBranch("three", toArray("3", "number"));
        addBranch("four", toArray("4", "number"));
        addBranch("five", toArray("5", "number"));
        addBranch("six", toArray("6", "number"));
        addBranch("seven", toArray("7", "number"));
        addBranch("eight", toArray("8", "number"));
        addBranch("nine", toArray("9", "number"));
        addBranch("ten", toArray("number"));
        addBranch("eleven", toArray("number"));
        addBranch("twelve", toArray("number"));
        addBranch("thirteen", toArray("number"));
        addBranch("fourteen", toArray("number"));
        addBranch("fifteen", toArray("number"));
        addBranch("sixteen", toArray("number"));
        addBranch("seventeen", toArray("number"));
        addBranch("eighteen", toArray("number"));
        addBranch("nineteen", toArray("number"));
        addBranch("twenty", toArray("number"));
        addBranch("thirty", toArray("number"));
        addBranch("forty", toArray("number"));
        addBranch("fifty", toArray("number"));
        addBranch("sixty", toArray("number"));
        addBranch("seventy", toArray("number"));
        addBranch("eighty", toArray("number"));
        addBranch("ninety", toArray("number"));
        addBranch("hundred", toArray("number"));
        addBranch("thousand", toArray("number"));

        addBranch("first", toArray("1", "order"));
        addBranch("second", toArray("2", "order"));
        addBranch("third", toArray("3", "order"));
        addBranch("fourth", toArray("4", "order"));
        addBranch("fifth", toArray("5", "order"));
        addBranch("sixth", toArray("6", "order"));
        addBranch("seventh", toArray("7", "order"));
        addBranch("eighth", toArray("8", "order"));
        addBranch("ninth", toArray("9", "order"));

        addBranch("front", toArray("front", "direction"));
        addBranch("back", toArray("back", "direction"));
        addBranch("left", toArray("left", "direction"));
        addBranch("right", toArray("right", "direction"));
        addBranch("up", toArray("up", "direction"));
        addBranch("down", toArray("down", "direction"));

        addBranch("above", toArray("above", "position"));
        addBranch("below", toArray("below", "position"));
        addBranch("around", toArray("around", "position"));
        addBranch("among", toArray("among", "position"));
        addBranch("between", toArray("between", "position"));
        addBranch("opposite", toArray("opposite", "position"));

        addBranch("am", toArray("COMPARE_5", "am"));
        addBranch("is", toArray("COMPARE_5", "is"));
        addBranch("are", toArray("COMPARE_5", "are"));
        addBranch("not", toArray("COMPARE_1", "not"));
        addBranch("was", toArray("is"));
        addBranch("were", toArray("are"));
        addBranch("an", toArray("an", "a", "one"));
        addBranch("the", toArray("the", "a", "one"));
        addBranch("has", toArray("have"));
        addBranch("had", toArray("have"));
        addBranch("been", toArray("be"));
        addBranch("being", toArray("be"));
        addBranch("would", toArray("will"));
        addBranch("in", toArray( "in", "position"));
        addBranch("out", toArray("out", "position"));
        addBranch("at", toArray("at", "position"));
        addBranch("on", toArray("on", "position"));
        addBranch("off", toArray("off", "position"));
        addBranch("into", toArray("in", "process"));
        addBranch("onto", toArray("on", "process"));

        addBranch("listen", toArray("listen", "act"));
        addBranch("think", toArray("think", "act"));
        addBranch("smell", toArray("smell", "act"));
        addBranch("taste", toArray("taste", "act"));
        addBranch("feel", toArray("feel", "act"));
        addBranch("move", toArray("move", "act"));
        addBranch("say", toArray("say", "act"));
        addBranch("see", toArray("see", "act"));

        addBranch("hello", toArray("hello", "hi"));
        addBranch("hey", toArray("hey", "hi"));
        addBranch("goodbye", toArray("goodbye", "bye"));
        addBranch("bye", toArray("goodbye", "bye"));


        addBranch("dog", toArray("animal", "pet", "dog", "bark"));
        addBranch("cat", toArray("animal", "pet", "cat", "meow"));
        addBranch("bark", toArray("bark", "sound"));
        addBranch("meow", toArray("meow", "sound"));
        /*
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        addBranch("", toArray("", ""));
        */

        // ##### 结束 #####
        editor.save();
    }

    // ##### 功能函数 #####
    // 添加根节点
    private static void addRoot(String input_info, String input_code){
        CognitionNodeRoot node = new CognitionNodeRoot(input_info, input_code);
        editor.addNode(node);
    }

    // 添加叶节点
    private static void addLeaf(String input_info){
        CognitionNodeLeaf node = new CognitionNodeLeaf(input_info);
        editor.addNode(node);
    }

    // 添加枝节点
    private static void addBranch(String input_info, ArrayList<String> input_portList){
        CognitionNodeBranch node = new CognitionNodeBranch(input_info);
        for (String temp_port : input_portList) {
            node.addPort(temp_port);
        }
        editor.addNode(node);
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
