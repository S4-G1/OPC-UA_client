package dk.s4_g1.common.data;

public class Response {
    public final int statusCode;
    public final String body;

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public boolean isOK() {
        return this.statusCode >= 200 && this.statusCode < 300;
    }
}
