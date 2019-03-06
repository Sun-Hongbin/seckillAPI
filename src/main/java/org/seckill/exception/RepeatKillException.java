package org.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * Java异常主要分编译期异常和运行期异常，运行期异常不需要手动try-catch
 * 还有一个重要原因：spring声明式事务只接收运行期异常回滚
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message){
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
