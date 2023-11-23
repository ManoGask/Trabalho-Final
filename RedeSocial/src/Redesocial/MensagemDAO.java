package Redesocial;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensagemDAO {
    private final String url = "jdbc:postgresql://localhost:5432/seu_banco_de_dados";
    private final String usuarioBD = "seu_usuario";
    private final String senhaBD = "sua_senha";

    public void enviarMensagem(Mensagem mensagem) {
        String sql = "INSERT INTO mensagens (remetente, destinatario, conteudo, data_envio) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, usuarioBD, senhaBD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, mensagem.getRemetente());
            pstmt.setString(2, mensagem.getDestinatario());
            pstmt.setString(3, mensagem.getConteudo());
            pstmt.setTimestamp(4, Timestamp.valueOf(mensagem.getDataEnvio()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mensagem> getMensagensPorDestinatario(String destinatario) {
        List<Mensagem> mensagensDoDestinatario = new ArrayList<>();
        String sql = "SELECT remetente, conteudo, data_envio FROM mensagens WHERE destinatario = ?";

        try (Connection con = DriverManager.getConnection(url, usuarioBD, senhaBD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, destinatario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String remetente = rs.getString("remetente");
                String conteudo = rs.getString("conteudo");
                Timestamp timestamp = rs.getTimestamp("data_envio");
                LocalDateTime dataEnvio = timestamp.toLocalDateTime();

                Mensagem mensagem = new Mensagem(remetente, destinatario, conteudo, dataEnvio);
                mensagensDoDestinatario.add(mensagem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mensagensDoDestinatario;
    }
}

