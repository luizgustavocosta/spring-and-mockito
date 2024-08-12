package com.costa.luiz.mockito;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    @ElementCollection
    private List<String> seguidores;
    @ElementCollection
    private List<String> seguindo;

    public void follow(User user) {
        // Implementação do método
        // Verifica se o usuário atual já está seguindo o usuário fornecido
        if (!this.seguindo.contains(user)) {
            // Adiciona o usuário à lista de seguidos
            this.seguindo.add(user.name);
            // Adiciona este usuário à lista de seguidores do outro usuário
            user.seguidores.add(this.name);
            System.out.println(this.name + " começou a seguir " + user.getName());
        } else {
            System.out.println(this.name + " já está seguindo " + user.getName());
        }
    }

    public void unfollow(User user) {
        // Implementação do método
        // Verifica se o usuário atual está seguindo o usuário fornecido
        if (this.seguindo.contains(user.name)) {
            // Remove o usuário da lista de seguidos
            this.seguindo.remove(user.name);
            // Remove este usuário da lista de seguidores do outro usuário
            user.seguidores.remove(this);
            System.out.println(this.name + " deixou de seguir " + user.getName());
        } else {
            System.out.println(this.name + " não estava seguindo " + user.getName());
        }
    }

    String getName() {
        return name;
    }
}
