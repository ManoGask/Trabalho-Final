package Redesocial;

import java.time.LocalDateTime;

public class Mensagem {
    private String remetente;
    private String destinatario;
    private String conteudo;
    private LocalDateTime dataEnvio;

    public Mensagem(String remetente, String destinatario, String conteudo, LocalDateTime dataEnvio) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.dataEnvio = LocalDateTime.now();
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    @Override
    public String toString() {
        return "De: " + remetente + ", Para: " + destinatario + ", Conteúdo: " + conteudo + ", Enviado em: " + dataEnvio;
    }
}


