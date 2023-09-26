package org.bahmnihwc.esanjeevaniservice.contract;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EsanjeevaniLoginRequest {
    private String userName;
    private String password;
    private String salt;
    private String source;

    public EsanjeevaniLoginRequest(String userName, String password, String salt, String source) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
