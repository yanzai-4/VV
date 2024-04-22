package AI.Memory;

// ##### 简介 #####
// 记忆链
// 抽象类

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

public interface MemoryChain {

    // ##### 抽象函数 #####
    // 返回记忆链类型
    MemoryChainType getType();

    // 增强记忆链
    void enhance();

    // 抑制记忆链
    void restrain();
}
