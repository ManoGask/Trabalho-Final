package Redesocial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Redesocialinterface {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Minha Rede Social");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Barra de navegação
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton homeButton = new JButton("Página Inicial");
        JButton profileButton = new JButton("Perfil");
        JButton friendsButton = new JButton("Amigos");
        JButton messagesButton = new JButton("Mensagens");

        navigationPanel.add(homeButton);
        navigationPanel.add(profileButton);
        navigationPanel.add(friendsButton);
        navigationPanel.add(messagesButton);

        frame.add(navigationPanel, BorderLayout.NORTH);

        // Área de postagens
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        JTextArea postTextArea = new JTextArea(10, 30);
        postTextArea.setLineWrap(true);
        postTextArea.setWrapStyleWord(true);
        JButton postButton = new JButton("Publicar");

        postPanel.add(postTextArea);
        postPanel.add(postButton);

        frame.add(postPanel, BorderLayout.CENTER);

        // Botões para adicionar/remover amigo e enviar mensagem
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addFriendButton = new JButton("Adicionar Amigo");
        JButton removeFriendButton = new JButton("Remover Amigo");
        JButton sendMessageButton = new JButton("Enviar Mensagem");

        actionsPanel.add(addFriendButton);
        actionsPanel.add(removeFriendButton);
        actionsPanel.add(sendMessageButton);

        frame.add(actionsPanel, BorderLayout.SOUTH);

        // Adicionar ação aos botões
        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Amigo adicionado!");
            }
        });

        removeFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Amigo removido!");
            }
        });

        sendMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Mensagem enviada!");
            }
        });

        frame.setVisible(true);
    }
}

