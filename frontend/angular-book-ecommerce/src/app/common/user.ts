export class User {
    account: string;
    password: string;
    username: string;
    email: string;
    phoneNumber: string;

    constructor(account: string, password: string, userName: string, email: string, phoneNumber: string) {
        this.account = account;
        this.password = password;
        this.username = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
