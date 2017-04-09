package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CoordinatesRepository;
import domain.Coordinates;

@Service
@Transactional
public class CoordinatesService {
	
	//Managed repository -----------------------------------------
	
	private CoordinatesRepository coordinatesRepository;
	
	//Supporting Services----------------------------------------
	
	
	//Constructors----------------------------------------------
	
	public CoordinatesService(){
		super();
	}
	
	//Simple CRUD methods----------------------------------------
	
	public Coordinates findOne(int coordinatesId){
		Assert.notNull(coordinatesId);
		Assert.isTrue(coordinatesId != 0);
		
		Coordinates result = this.coordinatesRepository.findOne(coordinatesId);
		
		return result;
	}
	
	public Collection<Coordinates> findAll(){
		
		Collection<Coordinates> result = this.coordinatesRepository.findAll();
		
		return result;
	}
	
	public Coordinates create(){
		
		Coordinates result = new Coordinates();
		
		return result;
		
	}
	
	public Coordinates save(Coordinates coordinates){
		Assert.notNull(coordinates);
		
		this.coordinatesRepository.save(coordinates);
		
		return coordinates;
	}
	
	//Other business methods-----------------------------------------------------------

}
