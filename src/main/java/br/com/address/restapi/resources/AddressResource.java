package br.com.address.restapi.resources;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import br.com.address.restapi.models.Address;
import br.com.address.restapi.repository.AddressRepository;

@RestController @RequestMapping(value="/api")
public class AddressResource {
	
	@Autowired
	AddressRepository addressRepository;
	
	@GetMapping("/address/list")
	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	@GetMapping("/address/list/{id}")
	public Address findById(@PathVariable(value="id") long id) {
		return addressRepository.findById(id);
	}
	
	@PostMapping("/address/save")
	public JSONObject save(@RequestBody Address address) {
		JSONObject result = new JSONObject();
		result = validateAddress(address);
		if(result.get("status").equals("OK")) {
			if(address.getLongitude().equals("") || address.getLatitude().equals("")) {
				address = getLatitudeAndLongitude(address);
			}
			addressRepository.save(address);
		}
		return result;
	}

	@DeleteMapping("/address/delete/{id}")
	public void delete(@PathVariable(value="id") long id) {
		addressRepository.deleteById(id);
	}

	@PutMapping("/address/update")
	public JSONObject update(@RequestBody Address address) {
		JSONObject result = new JSONObject();
		
		result = validateAddress(address);
		if(result.get("status").equals("OK")) {
			if(address.getLongitude().equals("") || address.getLatitude().equals("")) {
				address = getLatitudeAndLongitude(address);
			}
			addressRepository.save(address);
		}
		
		return result;
	}
	
	private JSONObject validateAddress(Address address) {
		
		boolean existsError = false;
		JSONObject result = new JSONObject();
		
		if(address.getStreetName().trim().equals("")) {
			existsError = true;
			result.put("errorStreetName", "Preencha o campo streetName");
		}
		if(address.getNumber().trim().equals("")) {
			existsError = true;
			result.put("errorNumber", "Preencha o campo number");
		}
		if(address.getNeighbourhood().trim().equals("")) {
			existsError = true;
			result.put("errorNeighbourhood", "Preencha o campo neighbourhood");
		}
		if(address.getCity().trim().equals("")) {
			existsError = true;
			result.put("errorCity", "Preencha o campo city");
		}
		if(address.getState().trim().equals("")) {
			existsError = true;
			result.put("errorState", "Preencha o campo state");
		}
		if(address.getCountry().trim().equals("")) {
			existsError = true;
			result.put("errorCountry", "Preencha o campo country");
		}
		if(address.getStreetName().trim().equals("")) {
			existsError = true;
			result.put("errorZipcode", "Preencha o campo zipcode");
		}
		
		if(existsError) {
			result.put("status", "NOK");
		} else {
			result.put("status", "OK");
		}
		
		return result;
	}
	
	private Address getLatitudeAndLongitude(Address address) {
		
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw").build();
		String addressTxt = getFormattedAddress(address);
		
		try {
			GeocodingResult[] results = GeocodingApi.geocode(context, addressTxt).await();
			if(results.length > 0) {
				address.setLatitude(String.valueOf(results[0].geometry.location.lat));
				address.setLongitude(String.valueOf(results[0].geometry.location.lng));
				return address;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	private String getFormattedAddress(Address address) {
		
		String formattedAddress = "";
		formattedAddress = formattedAddress.concat(address.getNumber());
		formattedAddress = formattedAddress.concat(" ");
		formattedAddress = formattedAddress.concat(address.getStreetName());
		formattedAddress = formattedAddress.concat(" ");
		formattedAddress = formattedAddress.concat(address.getCity());
		formattedAddress = formattedAddress.concat(", ");
		formattedAddress = formattedAddress.concat(address.getState());
		return formattedAddress;
	}
}
