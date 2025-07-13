package com.example.PerfulandiaSPA.Repository;

import com.example.PerfulandiaSPA.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    //Metodo para buscar por usuario por email
    Optional<Usuario> findByEmail(String email);    
}
