package Redesocial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Registro {

    // Métodos para registrar um novo usuário no banco de dados
    public static void registrarUsuario(String nome, String email, String senha) {
        String url = "jdbc:postgresql://localhost:5432/seu_banco_de_dados";
        String usuarioBD = "seu_usuario";
        String senhaBD = "sua_senha";

        try (Connection con = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String query = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.executeUpdate();
            System.out.println("Usuário registrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar o usuário: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Formulário de Registro ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        registrarUsuario(nome, email, senha);
    }
}

