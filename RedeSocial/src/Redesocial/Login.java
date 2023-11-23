package Redesocial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {
    private List<Usuario> usuarios;
    private List<Sessao> sessoesAtivas;

    public Login() {
        usuarios = new ArrayList<>();
        // Adicione alguns usuários fictícios para teste
        usuarios.add(new Usuario("Exemplo", "exemplo@email.com", "senha123"));

        sessoesAtivas = new ArrayList<>();
    }

    public boolean fazerLogin(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                // Cria uma nova sessão para o usuário
                Sessao novaSessao = new Sessao(usuario);
                sessoesAtivas.add(novaSessao);
                return true; // Login bem-sucedido
            }
        }
        return false; // Login falhou
    }

    public void encerrarSessao(Usuario usuario) {
        // Encerra a sessão do usuário
        sessoesAtivas.removeIf(sessao -> sessao.getUsuario().equals(usuario));
    }

    public static void main(String[] args) {
        Login sistema = new Login();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (sistema.fazerLogin(email, senha)) {
            System.out.println("Login bem-sucedido!");

            // Simula a permanência do usuário conectado por um tempo
            // Aqui você pode adicionar a lógica para continuar a interação com o sistema
            // enquanto o usuário estiver conectado
            // Por exemplo: menu, interações com a rede social, etc.

            // Para encerrar a sessão
            Usuario usuarioLogado = sistema.sessoesAtivas.get(0).getUsuario(); // Obtém o usuário logado
            sistema.encerrarSessao(usuarioLogado);
            System.out.println("Sessão encerrada para o usuário: " + usuarioLogado.getNome());
        } else {
            System.out.println("Credenciais inválidas. Login falhou.");
        }
    }
}

class Sessao {
    private Usuario usuario;

    public Sessao(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}

