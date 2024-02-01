package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByBeerNameAndBeerStyle() {
        List<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%IPA%", BeerStyle.ALE);

        assertThat(beerList.size()).isEqualTo(11);
    }
    @Test
    void testGetBeerListByBeerStyle() {
        List<Beer> beerList = beerRepository.findAllByBeerStyle(BeerStyle.ALE);

        assertThat(beerList.size()).isEqualTo(400);
    }
    @Test
    void testGetBeerListByName() {
        List<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(beerList.size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrowsExactly(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer 012345678901234567890123456789012345678901234567890123456789")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("89457575543289")
                    .price(new BigDecimal("11.99"))
                    .build());
            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("89457575543289")
                        .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}