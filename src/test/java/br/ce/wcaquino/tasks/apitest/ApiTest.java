package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void shouldReturnTasks() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void shouldAddNewTaskWithSuccess() {
		RestAssured.given()
			.body("{\"task\" : \"Teste via API\", \"dueDate\" : \"2021-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo/")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void shouldNotAddInvalidTask() {
		RestAssured.given()
			.body("{\"task\" : \"Teste via API\", \"dueDate\" : \"2010-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo/")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		
		//Inserir
		Integer id = RestAssured.given()
		.body("{\"task\" : \"Teste Remo��o de tarefa\", \"dueDate\" : \"2021-12-30\"}")
		.contentType(ContentType.JSON)
		.when()
			.post("/todo/")
		.then()
//			.log().all()
			.statusCode(201)
			.extract().path("id");
		
		System.out.println(id);
		
		//Remover tarefa
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204);
	}
	
	

}


