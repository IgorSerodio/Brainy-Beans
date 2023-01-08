package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

    /*
        Classe responsável pela parte lógica do Jogo do Macaco ~ Chimp Game
    */
public class ChimpLogic {

    /*
        Lista contendo o número de botões, que variam de acordo
        com a dificuldade.
    */
    private List<Integer> buttons;
    /*
        Inteiro que guarda o número de botões restantes para vencer
        um round do Jogo do Macaco.
    */
    private int buttonsLeft;
    /*
        Float que guarda o tempo passado para calcular corretamente
        os pontos ganhos pelo usuário.
     */
    private float timer;
    /*
        Inteiro que guarda o número de pontos que o usuário deixa de ganhar
        a cada segundo que passa; varia de acordo com a dificuldade.
     */
    private int pointsLostPerSec;
    /*
        Inteiro que guarda o número sendo verificado, para saber se
        o próximo é de fato seu sucessor.
     */
    private int iterator;
    /*
        Inteiro que guarda um número representando a dificuldade, de 0 a 2.
     */
    private int difficulty;

    /*
        Construtor que inicializa as variáveis.
     */
    ChimpLogic(Difficulty difficulty) {
        difficultyConfig(difficulty);
        this.buttonsLeft = this.buttons.size();
        this.iterator = 1;
        this.timer = 0;
    }
    /*
        Disponibiliza novos botões e reinicia o temporizador.
     */
    public void reset() {
        Collections.shuffle(this.buttons);
        this.buttonsLeft = this.buttons.size();
        this.iterator = 1;
        this.timer = 0;
    }
    /*
        Configura a dificuldade do mini jogo (fácil, médio, difícil).
     */
    private void difficultyConfig(Difficulty difficulty) {
        switch (difficulty){
            case EASY:
                this.buttons = Arrays.asList(1,2,3,4,5,6);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 200;
                this.difficulty = 0;
                break;
            case MEDIUM:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 100;
                this.difficulty = 1;
                break;
            case HARD:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 50;
                this.difficulty = 2;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
    /*
        Incrementa o temporizador conforme o tempo passado.
     */
    public void incrementTimer(float delta) {
        this.timer += delta;
    }
    /*
        Retorna se não existem botões restantes, ou seja
        se o jogador acertou todos os números dos botões em ordem
        ascendente.
     */
    public boolean noButtonsLeft() {
        return this.buttonsLeft == 0;
    }
    /*
        Recebe o número do botão clicado pelo jogador e verifica
        se foi de fato o correto. Caso seja, calcula a quantia de
        pontos para enfim retorná-la, reiniciando o temporizador.
     */
    public int tryButton(int button) {
        int points = 0;
        if(button == iterator) {
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - (int)(this.timer * this.pointsLostPerSec)), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.buttonsLeft -= 1;
            this.iterator += 1;
        }

        return points;
    }
}
