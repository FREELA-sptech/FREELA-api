package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.repositories.AvaliationRepository;
import freela.api.FREELAAPI.domain.services.AvaliationService;
import freela.api.FREELAAPI.resourses.entities.Avaliation;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliationServiceImpl implements AvaliationService {


    private final AvaliationRepository avaliationRepository;

    public AvaliationServiceImpl(AvaliationRepository avaliationRepository) {
        this.avaliationRepository = avaliationRepository;
    }

    public Double getUserAvaliation(Users users){
        List<Avaliation> rates = this.avaliationRepository.getAllByUser(users);
        Integer total = 0;

        if(rates.isEmpty()){
            return  5.0;
        }

        for(Avaliation rate : rates){
            total += rate.getRate();
        }
        return (double) (total / rates.size());
    }
}
