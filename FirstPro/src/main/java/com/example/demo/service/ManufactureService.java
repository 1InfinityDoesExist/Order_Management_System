package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.ManufacturingUnit;
import com.example.demo.repository.ManufactureRepository;

@Service
public class ManufactureService {

    @Autowired
    private ManufactureRepository manufactureRepository;

    public ManufacturingUnit saveManufactureData(ManufacturingUnit manuUnit) {
	return manufactureRepository.save(manuUnit);
    }

    public ManufacturingUnit getManufacturingUnitById(Long id) {
	return manufactureRepository.getManuUnitById(id);
    }

    public List<ManufacturingUnit> getAllManuUnit() {
	return manufactureRepository.findAll();
    }

    public void deleteManufaturingUnit(Long id) {
	manufactureRepository.deleteById(id);
    }
}
