package model;

/**
 * <h1>Codes</h1>
 * 0. Successful
 * 1. Username or email already exists
 * 2. data error
 * 3. unknown error
 * */
public class SignupResponse {
    private final int errorCode;

    public SignupResponse(int err) {
        errorCode = err;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
