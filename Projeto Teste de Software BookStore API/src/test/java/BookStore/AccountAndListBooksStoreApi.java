package BookStore;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mozilla.javascript.tools.shell.Global;
import resources.*;


import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountAndListBooksStoreApi {

    UserRequest2 userRequest = new UserRequest2("Alberta" + Instant.now().toString(),"Mestre12087!");

    // usuario pra o metodo java POSTUser
    UserRequest2 userRequestPostMethod = new UserRequest2("Gabrieltoledo" + Instant.now().toString(),"Fallen2@");
    UserRequest2 userLogin = new UserRequest2("arthur12087","Mestre12087!");

    //usuario só pro token
    UserRequest2 userLoginToToken = new UserRequest2("manodotoken","Mestretoken1!");
    String token;

    String userId;


    @BeforeAll
    public void configTest() {
        baseURI="https://bookstore.toolsqa.com/";

        TokenBodyResponse tokenBodyResponse = generatorToken();
        token = tokenBodyResponse.getToken();

        UserResponse userResponse = POSTUser();
        userId = userResponse.getUserID();


        given().header("Authorization","Bearer " +token).auth().preemptive().basic(userResponse.getUsername(), userResponse.getPassword());


    }

    @Test
    @DisplayName("Cadastrar um usuário")
    public void testPOSTUser() {

        UserResponse respostaBody = given().contentType(ContentType.JSON)
                .when()
                .body(userRequest)
                .post("Account/v1/User")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and().extract().response().as(UserResponse.class);

        assertEquals(userRequest.getUserName(),respostaBody.getUsername());
        assertNotNull(respostaBody.getUserID());


    }

    public UserResponse POSTUser() {

        UserResponse userResponse = given().contentType(ContentType.JSON)
                .when()
                .body(userRequestPostMethod)
                .post("Account/v1/User")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and().extract().response().as(UserResponse.class);

                userResponse.setPassword(userRequestPostMethod.getPassword());
                return userResponse;
    }

    @Test
    @DisplayName("Realizar login de usuário")
    public void testPOSTRealizarLoginUser() {

        given().contentType(ContentType.JSON)
                .when()
                .body(userLogin)
                .post("Account/v1/Authorized")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }


    @Test
    @DisplayName("Gerar Token")
    public void testGenerateToken() {

        TokenBodyResponse tokenBodyResponse = given().contentType(ContentType.JSON)
                .when()
                .body(userLoginToToken)
                .post("Account/v1/GenerateToken")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().extract().response().as(TokenBodyResponse.class);

                assertNotNull(tokenBodyResponse.getToken());

    }

    public TokenBodyResponse generatorToken() {

        return given().contentType(ContentType.JSON)
                .when()
                .body(userLoginToToken)
                .post("Account/v1/GenerateToken")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().extract().response().as(TokenBodyResponse.class);

    }


    @Test
    @DisplayName("Buscar usuário por ID")
    // não está funcionando na api
    public void testBuscarUsuarioPorId() {


        UserResponse respostaBody = given().contentType(ContentType.JSON)
                .when()
                .pathParam("UUID",userId)
                .get("Account/v1/User/{UUID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and().extract().response().as(UserResponse.class);

                assertEquals(userId,respostaBody.getUserID());
                assertNotNull(respostaBody.getUsername());

    }

    @Test
    @DisplayName("Buscar todos os livros sem usar parametros")
    public void testBuscarTodosOsLivrosSemParametros() {

        ListBooks respostaBody = given().contentType(ContentType.JSON)
                .when()
                .get("BookStore/v1/Books")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().as(ListBooks.class);

                assertNotNull(respostaBody);

   }

    @Test
    @DisplayName("Adicionar livro ao usuário")
    public void testAdicionarLivrosAoUsuario() {
        Book book = new Book("9781449325862");
        ArrayList listOfBooks = new ArrayList();
        listOfBooks.add(book);

        InsertBooksBody insertBooksBody = new InsertBooksBody(userId,listOfBooks);

        Gson gson = new Gson();

       ListBooks respostaBody = given().contentType(ContentType.JSON)
               .when()
               .body(gson.toJson(insertBooksBody))
               .post("BookStore/v1/Books")
               .then()
               .statusCode(HttpStatus.SC_CREATED)
               .and().extract().response().as(ListBooks.class);

                assertNotNull(respostaBody);

   }

    @Test
    @DisplayName("Buscar livro por isbn")
    public void testBuscarLivro() {
        File arquivoBookSchema = new File("src/test/java/resources/bookSchema.json");

        given().contentType(ContentType.JSON)
                .when()
                .queryParam("ISBN","9781449337711")
                .get("BookStore/v1/Book")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(JsonSchemaValidator.matchesJsonSchema(arquivoBookSchema)).log().all();

    }


    @Test
    @DisplayName("Deleção de todos os livros de usuario por Id")
    public void testDeleteAllLivrosDoUsuarioById() {

        given().contentType(ContentType.JSON)
                .when()
                .queryParam("UserId",userId)
                .delete("BookStore/v1/Books")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
