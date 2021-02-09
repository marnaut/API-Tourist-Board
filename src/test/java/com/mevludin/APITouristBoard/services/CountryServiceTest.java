package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    public void whenGetAll_thenReturnAllCountries() throws Exception {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("C1","c1",true));
        countries.add(new Country("C2","c2",true));
        countries.add(new Country("C3","c3",false));

        when(countryRepository.findAll()).thenReturn(countries);

        List<Country> found = countryService.getAll();

        assertThat(found).isEqualTo(countries);
    }

    @Test
    public void whenGetById_thenReturnCountryByIdAndActivityIsTrue() throws Exception{
        Country country = new Country();
        when(countryRepository.findByIdAndActivity(1l,true))
                .thenReturn(java.util.Optional.of(country));

        Country found = countryService.getById(1l);

        assertThat(found).isEqualTo(country);
    }


    @Test
    public void whenGetAllWhereActiveIsTrue_thenReturnAllActiveCountries(){
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("C1","c1",true));
        countries.add(new Country("C2","c2",true));

        when(countryRepository.findByActivity(true)).thenReturn(countries);

        List<Country> found = countryService.getAllWhereActiveIs(true);

        assertThat(found).isEqualTo(countries);
    }

    @Test
    public void whenSaveCountryWithNameAndAbbreviationsAndActivity_thenReturnCountry() {
        Country country = new Country("C1","c1",true);

        when(countryRepository.save(any(Country.class)))
                .thenReturn(country);

        Country saved = countryService.save(country);

        assertThat(saved).isEqualTo(country);
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveCountryWithoutName_thenReturnException() {
        Country country = new Country();
        country.setCountryAbbreviations("c1");

        countryService.save(country);
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveCountryWithoutAbbreviations_thenReturnException() throws Exception{
        Country country = new Country();
        country.setCountryName("C1");

        countryService.save(country);
    }

    @Test
    public void whenSaveCountry_thenReturnCountry(){
        Country country = new Country();
        country.setCountryAbbreviations("c1");
        country.setCountryName("C1");

        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country saved = countryService.save(country);

        assertThat(saved.getCountryName()).isSameAs(country.getCountryName());
    }

    @Test
    public void whenUpdateCountry_thenReturnCountry(){
        Country country = new Country("C1","c1",true);
        country.setId(1l);

        when(countryRepository.findById(any(Long.class)))
                .thenReturn(java.util.Optional.of(country));

        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country countryDetails = new Country();
        countryDetails.setCountryName("C2");
        countryDetails.setCountryAbbreviations("c2");
        countryDetails.setActivity(false);

        Country updated = countryService.updateWhereId(1l,countryDetails);

        assertThat(updated.getCountryName()).isSameAs(countryDetails.getCountryName());
    }


    @Test
    public void whenGetAllWhereActiveIsFalse_thenReturnAllActiveCountries(){
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("C1","c1",false));
        countries.add(new Country("C2","c2",false));

        when(countryRepository.findByActivity(false)).thenReturn(countries);

        List<Country> found = countryService.getAllWhereActiveIs(false);

        assertThat(found).isEqualTo(countries);
    }


}
