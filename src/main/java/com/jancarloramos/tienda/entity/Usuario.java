package com.jancarloramos.tienda.entity;

import com.jancarloramos.tienda.enumtypes.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres.")
    @NotBlank(message = "El nombre del usuario no puede ir vacío.")
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Size(min = 2, max = 60, message = "El apellido debe tener entre 2 y 60 caracteres.")
    @NotBlank(message = "El apellido del usuario no puede ir vacío.")
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;

    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Size(min = 5, max = 60, message = "El correo debe tener entre 5 a 60 caracteres")
    @Column(name = "email")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "password")
    private String password;

    @Min(value = 1, message = "La edad debe ser mayor o igual a 1.")
    @Max(value = 120, message = "La edad máxima es 120.")
    @NotNull(message = "La edad del usuario no puede ir vacía.")
    @Column(name = "edad_usuario")
    private Integer edadUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private UserRole rol;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getApellidoUsuario() { return apellidoUsuario; }
    public void setApellidoUsuario(String apellidoUsuario) { this.apellidoUsuario = apellidoUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getEdadUsuario() { return edadUsuario; }
    public void setEdadUsuario(Integer edadUsuario) { this.edadUsuario = edadUsuario; }

    public UserRole getRol() { return rol; }
    public void setRol(UserRole rol) { this.rol = rol; }
}