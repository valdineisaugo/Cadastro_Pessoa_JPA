package com.cadastro.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Usando o caminho correto para o arquivo FXML
        URL fxmlLocation = getClass().getResource("/fxml/PessoaView.fxml");
        if (fxmlLocation == null) {
            System.err.println("Erro: Arquivo FXML não encontrado!");
            System.err.println("Locais verificados: " + getClass().getResource("/"));
            throw new FileNotFoundException("Arquivo FXML não encontrado");
        }
        Parent root = FXMLLoader.load(fxmlLocation);
        primaryStage.setTitle("Cadastro de Pessoas");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // Fechar conexão com o banco ao encerrar a aplicação
        try {
            PessoaViewController.fecharConexao();
        } catch (Exception e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}