package org.zeniafrik.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.zeniafrik.models.UserLocalObject;

public class AuthenticationResponse {
    String msg;
    int code;
    MicroResponse data;

    public AuthenticationResponse(@NonNull int code, @NonNull String msg, @Nullable MicroResponse data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


    public String getMessage() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public MicroResponse getData() {
        return data;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "message='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public final class MicroResponse {
        ClientInfo clientinfo;
        UserLocalObject profile;

        @Override
        public String toString() {
            return "microResponse{" +
                    "clientinfo=" + clientinfo +
                    ", profile=" + profile +
                    '}';
        }

        public ClientInfo getClientinfo() {
            return clientinfo;
        }

        public UserLocalObject getProfile() {
            return profile;
        }
    }

    public final class ClientInfo {
        String access_token, refresh_token;
        int expires_in;

        public ClientInfo(String access_token, String refresh_token, int expires_in) {
            this.access_token = access_token;
            this.refresh_token = refresh_token;
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public int getExpires_in() {
            return expires_in;
        }
    }
}
