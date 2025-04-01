package com.cadastro.view;

import com.cadastro.controller.PessoaController;
import com.cadastro.model.Pessoa;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PessoaViewController implements Initializable {

    private static PessoaController controller = new PessoaController();
    private Pessoa pessoaSelecionada;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEndereco;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private TableView<Pessoa> tblPessoas;

    @FXML
    private TableColumn<Pessoa, Long> colId;

    @FXML
    private TableColumn<Pessoa, String> colNome;

    @FXML
    private TableColumn<Pessoa, String> colTelefone;

    @FXML
    private TableColumn<Pessoa, String> colEndereco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
        loadTableData();

        // Adicionar listener para selecionar uma pessoa na tabela
        tblPessoas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                pessoaSelecionada = newSelection;
                preencherCampos();
                btnExcluir.setDisable(false);
            } else {
                limparCampos();
                btnExcluir.setDisable(true);
            }
        });
    }

    private void configureTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
    }

    private void loadTableData() {
        List<Pessoa> pessoas = controller.buscarTodos();
        ObservableList<Pessoa> observableList = FXCollections.observableArrayList(pessoas);
        tblPessoas.setItems(observableList);
    }

    @FXML
    private void handleSalvar(ActionEvent event) {
        if (validarCampos()) {
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String endereco = txtEndereco.getText();

            try {
                if (pessoaSelecionada == null) {
                    // Criar nova pessoa
                    controller.salvar(nome, telefone, endereco);
                    mostrarAlerta("Pessoa cadastrada com sucesso!", Alert.AlertType.INFORMATION);
                } else {
                    // Atualizar pessoa existente
                    controller.atualizar(pessoaSelecionada.getId(), nome, telefone, endereco);
                    mostrarAlerta("Pessoa atualizada com sucesso!", Alert.AlertType.INFORMATION);
                }

                limparCampos();
                loadTableData();
            } catch (Exception e) {
                mostrarAlerta("Erro ao salvar: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleExcluir(ActionEvent event) {
        if (pessoaSelecionada != null) {
            try {
                controller.excluir(pessoaSelecionada.getId());
                mostrarAlerta("Pessoa excluída com sucesso!", Alert.AlertType.INFORMATION);
                limparCampos();
                loadTableData();
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleLimpar(ActionEvent event) {
        limparCampos();
    }

    private void preencherCampos() {
        txtNome.setText(pessoaSelecionada.getNome());
        txtTelefone.setText(pessoaSelecionada.getTelefone());
        txtEndereco.setText(pessoaSelecionada.getEndereco());
    }

    private void limparCampos() {
        txtNome.clear();
        txtTelefone.clear();
        txtEndereco.clear();
        pessoaSelecionada = null;
        tblPessoas.getSelectionModel().clearSelection();
        btnExcluir.setDisable(true);
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty() || txtTelefone.getText().isEmpty() || txtEndereco.getText().isEmpty()) {
            mostrarAlerta("Todos os campos são obrigatórios!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Cadastro de Pessoas");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void fecharConexao() {
        controller.fecharConexao();
    }
}