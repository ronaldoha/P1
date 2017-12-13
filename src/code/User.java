package code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable
{
    private String username;
    private String password;

    User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
