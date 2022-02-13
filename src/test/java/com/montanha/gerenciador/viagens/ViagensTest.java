package com.montanha.gerenciador.viagens;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;


public class ViagensTest {
    @Test
    public void testDadoUmAdministradorQuandoCadastroViagensEntaoObtenhoStatusCode201(){
        // configurar o caminho comum  de acesso a minha API Rest
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        // Login na API Rest com Administrador
        String token = given()
                .body("{\n" +
                        "  \"email\": \"admin@email.com\",\n" +
                        "  \"senha\": \"654321\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                .path("data.token");

        // Cadastrar a Viagem
        given()
                .header("Authorization", token)
                .body("{\n" +
                        "  \"acompanhante\": \"Priscila\",\n" +
                        "  \"dataPartida\": \"2021-02-02\",\n" +
                        "  \"dataRetorno\": \"2021-03-02\",\n" +
                        "  \"localDeDestino\": \"Maceio\",\n" +
                        "  \"regiao\": \"Nordeste\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("/v1/viagens")
            .then()
                .log().all()
                .assertThat()
                .statusCode(201);

    }
}
