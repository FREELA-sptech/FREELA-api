package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.repositories.AvaliationRepository;
import freela.api.FREELAAPI.resourses.entities.Avaliation;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AvaliationServiceImplTest {

    @Test
    public void testGetUserAvaliation() {
        AvaliationRepository avaliationRepository = Mockito.mock(AvaliationRepository.class);

        Users user = new Users();
        List<Avaliation> rates = new ArrayList<>();
        rates.add(createAvaliation(1, 5, user));
        rates.add(createAvaliation(2, 4, user));
        rates.add(createAvaliation(3, 3, user));

        when(avaliationRepository.getAllByUser(user)).thenReturn(rates);

        AvaliationServiceImpl avaliationService = new AvaliationServiceImpl(avaliationRepository);

        Double result = avaliationService.getUserAvaliation(user);

        assertEquals(4.0, result, 0.01);
    }

    @Test
    public void testGetUserAvaliation_WithEmptyList() {
        AvaliationRepository avaliationRepository = Mockito.mock(AvaliationRepository.class);

        Users user = new Users();
        List<Avaliation> rates = new ArrayList<>();

        when(avaliationRepository.getAllByUser(user)).thenReturn(rates);

        AvaliationServiceImpl avaliationService = new AvaliationServiceImpl(avaliationRepository);

        Double result = avaliationService.getUserAvaliation(user);

        assertEquals(5.0, result, 0.01);
    }

    private Avaliation createAvaliation(Integer id, Integer rate, Users user) {
        Avaliation avaliation = new Avaliation();
        avaliation.setId(id);
        avaliation.setRate(rate);
        avaliation.setUser(user);
        return avaliation;
    }
}