package com.mrcontas.javafx;


import com.microsoft.playwright.*;


import evdata.filemanager.Gerenciador_de_arquivo_html_de_NFe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.io.File;

public class Controlador_da_tela_principal{

    @FXML public TableView table;

    @FXML
    private Label welcomeText;


    @FXML
    protected void onNovoButtonClick() {
        Aplicativo.novoArquivo();
    }

    @FXML
    protected void onAbrirButtonClick() {
        Aplicativo.abrirArquivo();
    }

    @FXML
    protected void onSalvarButtonClick() {
        Aplicativo.salvarArquivo();
    }

    @FXML
    protected void onInserirContaButtonClick() {
        Aplicativo.novaConta();
    }

    @FXML
    protected void onInserirLançamentoButtonClick() {
        Aplicativo.novoLançamento();
    }

    @FXML
    protected void chButtonAction(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://www.nfe.fazenda.gov.br/portal/consultaRecaptcha.aspx"
                    //, new Page.NavigateOptions().setWaitUntil(com.microsoft.playwright.options.WaitUntilState.NETWORKIDLE)
            );
            /*try {
                page.wait(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();"#checkbox"
            }*/
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            page.fill("#ctl00_ContentPlaceHolder1_txtChaveAcessoResumo", chTextField.getText());
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);

//            FrameLocator frameLocator = page.frameLocator(".h-captcha iframe").first();

            Frame captchaCheckboxFrame = page.frameByUrl(java.util.regex.Pattern.compile(".*hcaptcha-checkbox.html*"));
            captchaCheckboxFrame.locator("#checkbox").click(
//                    new Locator.ClickOptions().setPosition(5,5)
            );


            Frame captchaChallengeFrame = page.frameByUrl(java.util.regex.Pattern.compile(".*hcaptcha-challenge.html*"));

            captchaCheckboxFrame.waitForSelector(".check img");

            captchaCheckboxFrame.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            captchaChallengeFrame.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);

//            captchaCheckboxFrame
                    page.locator("#ctl00_ContentPlaceHolder1_btnConsultarHCaptcha").click();

            captchaCheckboxFrame.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            captchaChallengeFrame.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
            page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(java.nio.file.Paths.get("screenshot.png"))
                    .setFullPage(true));

            new Gerenciador_de_arquivo_html_de_NFe().saveHtmlFileWithContent( page.content() );
        }
    }

    @FXML javafx.scene.control.TextField chTextField;
    @FXML
    protected void initialize(){
        refreshWindow();
        chTextField.textProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        /* To check if the value is null then return */
                        if (t1 == null){
                            return;
                        }
                        /* Check the length of new value length is less then defined max length then             set the new value to the text field, else keep old value in text field. */
                        if (t1.length() > 44) {
                            chTextField.setText(s);
                        } else {
                            chTextField.setText(t1);
                        }
                    }
                }
        );
    }

    private void refreshWindow(){
        File openedFile = Aplicativo.getArquivo();
        if (openedFile!=null){
//            try {
                /*new evdata.javaSqfxLite.SimplesGerenciadorDeControles<TableView>()
                        .montar_Tabela_FX(table
                                , Conta.fábrica().criar(), true
                                , openedFile.getCanonicalPath()
                        );*/
                //welcomeText.setText("Welcome to JavaFX Application!");
                //welcomeText.setText(openedFile.getCanonicalPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

}