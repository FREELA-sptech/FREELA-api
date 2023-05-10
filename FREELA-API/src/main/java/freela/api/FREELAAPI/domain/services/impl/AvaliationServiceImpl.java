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

    @Autowired
    private AvaliationRepository avaliationRepository;

    public Double getUserAvaliation(Users users){
        List<Avaliation> rates = this.avaliationRepository.getAllByUser(users);
        Integer total = 0;

        if(rates.isEmpty()){
            return  0.0;
        }

        for(Avaliation rate : rates){
            total += rate.getRate();
        }
        Double media  = Double.valueOf(total / Integer.valueOf(rates.size()));
        return media;
    }
}
