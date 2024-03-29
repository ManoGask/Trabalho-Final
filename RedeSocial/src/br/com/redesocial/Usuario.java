package br.com.redesocial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private static int idCounter = 1;
    private String nome;
    private String email;
    private String senha;
    private int idUsuario;

    public Usuario() {
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha, String dataDeNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = (int) idUsuario;
    }
    // Método para realizar o login
    public boolean fazerLogin() {
        Database bancoDeDados = new Database();
        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "SELECT * FROM usuario WHERE DES_EMAIL = ? AND DES_SENHA = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, senha);
                try (ResultSet resultado = statement.executeQuery()) {
                    if (resultado.next()) {
                        this.idUsuario = resultado.getInt("COD_USUARIO");
                        this.nome = resultado.getString("NOM_USUARIO");
                        this.email = resultado.getString("DES_EMAIL");
                        this.senha = resultado.getString("DES_SENHA");
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Falha ao efetuar login: " + e.getMessage());
            return false;
        }
    }

    // Método para realizar o registro
    public boolean fazerRegistro(String email, String senha, String nome) {
        Database bancoDeDados = new Database();
        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "INSERT INTO usuario (COD_USUARIO, NOM_USUARIO, DES_EMAIL, DES_SENHA) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setLong(1, idUsuario);
                statement.setString(2, nome);
                statement.setString(3, email);
                statement.setString(4, senha);
                int resultado = statement.executeUpdate();

                return resultado > 0;
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro durante o registro: " + e.getMessage());
            return false;
        }
    }

    // Método para adicionar amigo
    public void adicionarAmigo(long codAmigo) {
        Database bancoDeDados = new Database();
        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "INSERT INTO amigo (COD_USUARIO, ID_AMIGO) VALUES (?, ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setLong(1, this.idUsuario);
                statement.setLong(2, codAmigo);
                int linhasAfetadas = statement.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("O amigo foi adicionado com êxito.");
                } else {
                    System.out.println("Ocorreu uma falha ao adicionar o amigo.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao tentar adicionar o amigo: " + e.getMessage());
        }
    }

    // Método para listar amigos
    public List<Usuario> listarAmigos() {
        Database bancoDeDados = new Database();
        Connection conexao = bancoDeDados.conectar();
        List<Usuario> amigos = new ArrayList<>();

        String query = "SELECT u.COD_USUARIO, u.NOM_USUARIO FROM AMIGO a JOIN USUARIO u ON a.COD_AMIGO = u.COD_USUARIO WHERE a.COD_USUARIO = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, this.idUsuario);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                int codAmigo = resultado.getInt("COD_USUARIO");
                Long nomeAmigo = resultado.getLong("NOM_USUARIO");
                Usuario amigo = new Usuario("Nome", "Email", "Senha");
                amigo.getIdUsuario();
                amigo.getNome();

                amigos.add(amigo);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao listar amigos: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }
        return amigos;
    }
    // Método para excluir amigo
    public boolean excluirAmigo(long codAmigo) {
        // Verificação para ver se há amizade entre os usuários
        if (!saoAmigos(codAmigo)) {
            System.out.println("Erro ao excluir amigo: Usuários não são amigos.");
            return false;
        }

        Database bancoDeDados = new Database();
        boolean sucesso = false;

        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "DELETE FROM amigo WHERE (COD_USUARIO = ? AND ID_AMIGO = ?) OR (COD_USUARIO = ? AND ID_AMIGO = ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setLong(1, this.idUsuario);
                statement.setLong(2, codAmigo);
                statement.setLong(3, codAmigo);
                statement.setLong(4, this.idUsuario);

                int linhasAfetadas = statement.executeUpdate();

                if (linhasAfetadas > 0) {
                    sucesso = true;
                    System.out.println("Amigo excluído com sucesso!");
                } else {
                    System.out.println("Falha ao excluir amigo.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir amigo: " + e.getMessage());
        }

        return sucesso;
    }
    // Método privado para verificar se dois usuários são amigos
    private boolean saoAmigos(long codAmigo) {
        Database bancoDeDados = new Database();
        boolean saoAmigos = false;

        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "SELECT COUNT(1) FROM amigo WHERE (COD_USUARIO = ? AND ID_AMIGO = ?) OR (COD_USUARIO = ? AND ID_AMIGO = ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setLong(1, this.idUsuario);
                statement.setLong(2, codAmigo);
                statement.setLong(3, codAmigo);
                statement.setLong(4, this.idUsuario);

                try (ResultSet resultado = statement.executeQuery()) {
                    if (resultado.next() && resultado.getInt(1) > 0) {
                        saoAmigos = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar amizade: " + e.getMessage());
        }

        return saoAmigos;
    }

    // Método privado para verificar se dois usuários são amigos
    private boolean saoAmigos2(long codAmigo) {
        Database bancoDeDados = new Database();
        boolean saoAmigos = false;

        try (Connection conexao = bancoDeDados.conectar()) {
            String query = "SELECT COUNT(1) FROM amigo WHERE (COD_USUARIO = ? AND ID_AMIGO = ?) OR (COD_USUARIO = ? AND ID_AMIGO = ?)";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setLong(1, this.idUsuario);
                statement.setLong(2, codAmigo);
                statement.setLong(3, codAmigo);
                statement.setLong(4, this.idUsuario);

                try (ResultSet resultado = statement.executeQuery()) {
                    if (resultado.next() && resultado.getInt(1) > 0) {
                        saoAmigos = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar amizade: " + e.getMessage());
        }

        return saoAmigos;
    }
}