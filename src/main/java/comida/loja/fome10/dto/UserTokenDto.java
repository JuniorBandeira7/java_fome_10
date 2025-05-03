package comida.loja.fome10.dto;

import comida.loja.fome10.model.User;

public class UserTokenDto {
    private String token;
    private User user;

    public UserTokenDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
