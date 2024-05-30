export class User {
    account: string;
    password: string;
    username: string;
    email: string;
    phoneNumber: string;
    facebook_account_id: number;
    google_account_id: number;
    role_id: number;

    constructor(account: string, password: string, userName: string, email: string, phoneNumber: string, facebook_account_id: number, google_account_id: number, role_id: number) {
        this.account = account;
        this.password = password;
        this.username = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.facebook_account_id = facebook_account_id;
        this.google_account_id = google_account_id;
        this.role_id = role_id;
    }
}
