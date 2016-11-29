package wear.sunshine.android.example.com.capstone_1.response;

/**
 * Created by jibin on 25/11/16.
 */
public class TokenSuccess {

    private String status;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    private int returnCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
