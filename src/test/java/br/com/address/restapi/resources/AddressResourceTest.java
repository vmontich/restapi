package br.com.address.restapi.resources;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import br.com.address.restapi.models.Address;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class)
public class AddressResourceTest {
	
	@Autowired
	AddressResource resource = new AddressResource();

	@Test
	@Order(1)
	public void testSaveAddress1() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setStreetName("Dr. Adilson Rodrigues");
		address.setNumber("123");
		address.setComplement("Sala 100");
		address.setNeighbourhood("Jardim das Samambaias");
		address.setCity("Jundiaí");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13211685");
		address.setLatitude("");
		address.setLongitude("");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}
	
	@Test
	@Order(2)
	void testSaveAddress2() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setStreetName("9 de Julho");
		address.setNumber("1001");
		address.setComplement("Sala 10");
		address.setNeighbourhood("Centro");
		address.setCity("Vinhedo");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13280000");
		address.setLatitude("");
		address.setLongitude("");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}
	
	@Test
	@Order(3)
	void testSaveAddress3() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setStreetName("Ibate");
		address.setNumber("133");
		address.setComplement("");
		address.setNeighbourhood("Caixa DAgua");
		address.setCity("Vinhedo");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13282572");
		address.setLatitude("");
		address.setLongitude("");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}
	
	@Test
	@Order(4)
	void testSaveAddress4() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setStreetName("San Marino");
		address.setNumber("44");
		address.setComplement("");
		address.setNeighbourhood("Barão Geraldo");
		address.setCity("Campinas");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13085553");
		address.setLatitude("-45.84747");
		address.setLongitude("-44.89311");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}
	
	@Test
	@Order(5)
	void testSaveAddress5() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setStreetName("Atenas");
		address.setNumber("30");
		address.setComplement("");
		address.setNeighbourhood("Estância Eudóxia");
		address.setCity("Campinas");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13085558");
		address.setLatitude("");
		address.setLongitude("");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}
	
	@Test
	@Order(6)
	void testFindAll() {
		List<Address> lista = resource.findAll();
		assertEquals(5, lista.size());
	}

	@Test
	@Order(7)
	void testFindById() {
		Address address = resource.findById(3);
		assertEquals("13282572", address.getZipcode());
		assertEquals("Vinhedo", address.getCity());
		assertEquals("SP", address.getState());
	}

	@Test
	@Order(8)
	void testDelete() {
		Address address = resource.findById(1);
		resource.delete(1);
		List<Address> lista = resource.findAll();
		assertFalse(lista.contains(address));
	}

	@Test
	@Order(9)
	void testUpdate() {
		JSONObject json = new JSONObject();
		Address address = new Address();
		address.setId((long) 5);
		address.setStreetName("Nome da Rua Teste");
		address.setNumber("30");
		address.setComplement("");
		address.setNeighbourhood("Estância Eudóxia");
		address.setCity("Campinas");
		address.setState("SP");
		address.setCountry("Brazil");
		address.setZipcode("13085558");
		address.setLatitude("");
		address.setLongitude("");
		json = resource.save(address);
		
		assertEquals("OK", json.get("status"));
	}

}
