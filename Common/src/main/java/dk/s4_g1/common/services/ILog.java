package dk.s4_g1.common.services;

public interface ILog {
    void Debug(String className, String msg);
    void Error(String className, String msg);
    void Warning(String className, String msg);
    void Info(String className, String msg);
}
