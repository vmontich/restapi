package br.com.address.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.address.restapi.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findById(long id);
	
}
