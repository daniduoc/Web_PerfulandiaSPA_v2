package com.example.PerfulandiaSPA.Service;

import com.example.PerfulandiaSPA.Model.Usuario;
import com.example.PerfulandiaSPA.Repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired // Inyectar o sincronizar el repositorio de usuario
    private UsuarioRepository repo;

    //Metodo para registar a los usuarios
    public Usuario registrar(Usuario u){
        return repo.save(u);//Verificar si el usuario ya existe en la base de datos
    }
    //Metodo que autentifica a los usuarios 
    public Optional<Usuario> autenticar(String email, String password){
        return repo.findByEmail(email).filter(u -> u.getPassword().equals(password));
    } 
}
