export class Address {
    street: string;
    city: string;
    district: string;
    country: string;
    zipCode: string;
    specificAddress: string;

    constructor(street: string, city: string, district: string, country: string, zipCode: string, specificAddress: string) {
        this.street = street;
        this.city = city;
        this.district = district;
        this.country = country;
        this.zipCode = zipCode;
        this.specificAddress = specificAddress;
    }

}
