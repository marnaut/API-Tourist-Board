package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CountryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void whenFindByActivity_thenReturnAllActiveCountries(){
        //given
        Country bosnia = new Country("Bosna","ba",true);
        Country serbia = new Country("Srbija","rs",true);
        Country croatia = new Country("Hrvatska","hr",false);

        entityManager.persist(bosnia);
        entityManager.persist(serbia);
        entityManager.persist(croatia);
        entityManager.flush();

        List<Country> found = countryRepository.findByActivity(true);

        //then
        assertThat(found.toArray().length).isEqualTo(2);
    }

    @Test
    public void whenFindByIdAndActivity_thenReturnOneCountryWhereIdAndActivityIsTrue(){
        //given
        Country bosnia = new Country("Bosnia","ba",true);
        Country serbia = new Country("Serbia","rs",true);
        Country croatia = new Country("Croatia","hr",false);

        entityManager.persist(bosnia);
        entityManager.persist(serbia);
        entityManager.persist(croatia);
        entityManager.flush();

        Country found = countryRepository.findByIdAndActivity(bosnia.getId(),true).orElse(croatia);

        //than
        assertThat(found).isEqualTo(bosnia);
    }

    @Test
    public void whenFindByIdAndActivity_thenReturnOneCountryWhereIdAndActivityIsFalse(){
        Country bosnia = new Country("Bosnia","ba",true);
        Country serbia = new Country("Serbia","rs",true);
        Country croatia = new Country("Croatia","hr",false);

        entityManager.persist(bosnia);
        entityManager.persist(serbia);
        entityManager.persist(croatia);
        entityManager.flush();

        Country found = countryRepository.findByIdAndActivity(bosnia.getId(),false).orElse(croatia);

        assertEquals(found,croatia);
    }
}
