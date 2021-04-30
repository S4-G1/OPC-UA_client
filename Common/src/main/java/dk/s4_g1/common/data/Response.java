package dk.s4_g1.common.data;

public class Response {
    public final int statusCode;
    public final String body;

    public Response(int statusCode, String body){
        this.statusCode = statusCode;
        this.body = body;
    }

    public boolean isClientError(){
        return this.statusCode > 300 && this.statusCode < 400;
    }
}
