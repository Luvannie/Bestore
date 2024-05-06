import { Injectable } from '@angular/core';
import { Observable, map, of } from 'rxjs';
import { Country } from '../common/country';
import { HttpClient } from '@angular/common/http';
import { City } from '../common/city';

@Injectable({
  providedIn: 'root'
})
export class BeFormService {

  private countryUrl = 'http://localhost:8080/api/countries';
  private cityUrl = 'http://localhost:8080/api/cities';
  constructor(private httpClient: HttpClient) { }

  getCreditCardMonths(startMonth: number): Observable<number[]> {
    let months: number[] = [];

    for (let month = startMonth; month <= 12; month++) {
      months.push(month);
    }

    return of(months);
  }

  getCreditCardYears(): Observable<number[]> {
    let years: number[] = [];
    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 50;

    for (let year = startYear; year <= endYear; year++) {
      years.push(year);
    }

    return of(years);
  }

  getCountries(): Observable<Country[]> {
    return this.httpClient.get<GetResponseCountries>(this.countryUrl).pipe(
      map(response => response._embedded.countries)
    );
  }

  getCities(theCountryCode: string): Observable<City[]> {
    const searchCityUrl = `${this.cityUrl}/search/findByCountryCode?code=${theCountryCode}`;
    return this.httpClient.get<GetResponseCities>(searchCityUrl).pipe(
      map(response => response._embedded.cities)
    );
  }
}


interface GetResponseCountries {
  _embedded: {
    countries: Country[];
  }
}

interface GetResponseCities {
  _embedded: {
    cities: City[];
  }
}
