package com.utils.Dino;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    private final int LARGURA = 800;
    private final int ALTURA = 300;
    private final int CHAO = 250;
    private Timer timer;
    private Dino dino;
    private List<Obstacle> obstaculos;
    private List<Cloud> nuvens;
    private boolean jogoIniciado = false;
    private int pontuacao = 0;
    private Random random;

    public Game() {
        JFrame frame = new JFrame("Dino Game");
        frame.setSize(LARGURA, ALTURA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setVisible(true);

        dino = new Dino(CHAO);
        obstaculos = new ArrayList<>();
        nuvens = new ArrayList<>();
        timer = new Timer(20, this);
        random = new Random();

        inicializarObstaculos();
        inicializarNuvens();
    }

    private void inicializarObstaculos() {
        // Cria obstáculos iniciais
        for (int i = 0; i < 3; i++) {
            obstaculos.add(new Obstacle(LARGURA + i * 300, CHAO, 20, 40));
        }
    }

    private void inicializarNuvens() {
        // Cria nuvens iniciais
        for (int i = 0; i < 3; i++) {
            nuvens.add(new Cloud(LARGURA + i * 200, random.nextInt(150), 50, 20));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        // Desenha o chão
        g.setColor(Color.GRAY);
        g.fillRect(0, CHAO + 20, LARGURA, ALTURA - CHAO);

        // Desenha o dinossauro e obstáculos
        dino.draw(g);
        for (Obstacle obstaculo : obstaculos) {
            obstaculo.draw(g);
        }

        // Desenha as nuvens
        for (Cloud nuvem : nuvens) {
            nuvem.draw(g);
        }

        // Desenha a pontuação
        g.setColor(Color.WHITE);
        g.drawString("Pontuação: " + pontuacao, LARGURA - 120, 20);

        // Desenha mensagem de início
        if (!jogoIniciado) {
            g.setColor(Color.WHITE);
            g.drawString("Pressione ESPAÇO para começar", LARGURA / 2 - 50, ALTURA / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jogoIniciado) {
            atualizarJogo();
            repaint();
        }
    }

    private void atualizarJogo() {
        dino.update();
        atualizarObstaculos();
        atualizarNuvens();
        verificarColisao();
        pontuacao++;
    }

    private void atualizarObstaculos() {
        for (Obstacle obstaculo : obstaculos) {
            obstaculo.update();
            if (obstaculo.isOffScreen()) {
                obstaculo.reset(LARGURA);
            }
        }
    }

    private void atualizarNuvens() {
        for (Cloud nuvem : nuvens) {
            nuvem.update();
        }
    }

    private void verificarColisao() {
        for (Obstacle obstaculo : obstaculos) {
            if (dino.getBounds().intersects(obstaculo.getBounds())) {
                timer.stop();
                int opcao = JOptionPane.showConfirmDialog(this, "Game Over! Pontuação: " + pontuacao + "\nJogar novamente?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    reiniciarJogo();
                } else {
                    System.exit(0);
                }
            }
        }
    }

    private void reiniciarJogo() {
        dino = new Dino(CHAO);
        obstaculos.clear();
        inicializarObstaculos();
        pontuacao = 0;
        jogoIniciado = false;
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!jogoIniciado) {
                jogoIniciado = true;
                timer.start();
            } else {
                dino.jump();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Game();
    }
}