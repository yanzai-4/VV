package AI.Mind.General;

// ##### 简介 #####
// 报错显示器
// 根据情况显示不同颜色的报错信息

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

public class Debug {

    // ##### 变量 #####
    // 常规报错开关
    final boolean log_status = true;
    // 失败报错开关
    final boolean fail_status = true;
    // 成功报错开关
    final boolean done_status = true;
    // 错误报错开关
    final boolean error_status = true;
    // 提示报错开关
    final boolean notice_status = true;

    // ##### 功能函数 #####
    // 打印常规消息
    public void log(Object input){
        if(this.log_status){
            System.out.println("$N" + input.toString());
        }
    }

    // 打印失败消息
    public void fail(Object input){
        if(this.fail_status){
            System.out.println("$Y" + input.toString());
        }
    }

    // 打印成功消息
    public void done(Object input){
        if(this.done_status){
            System.out.println("$G" + input.toString());
        }
    }

    // 打印报错消息
    public void error(Object input){
        if(this.error_status){
            System.out.println("$R" + input.toString());
        }
    }

    // 打印提示消息
    public void notice(Object input){
        if(this.notice_status){
            System.out.println("$B" + input.toString());
        }
    }

    public void title(Object input){
        System.out.println("$N" + input.toString());
    }
}
