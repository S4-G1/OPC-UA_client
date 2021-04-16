package dk.s4_g1.common.services;

public interface ILog {
    void Debug(Class c, String msg);
    void Error(String msg);
    void Warning(String msg);
    void Info(String msg);
}
