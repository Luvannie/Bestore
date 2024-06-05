export class UpdateUserDTO {
    username: string;    
    password: string;    
    retype_password: string;    
    phone_number: string;
    facebook_account_id: number;
    google_account_id: number;
   
    
    constructor(data: any) {
        this.username = data.username;
        this.password = data.password;
        this.retype_password = data.retype_password;
        this.phone_number = data.phone_number;
        this.facebook_account_id = data.facebook_account_id;
        this.google_account_id = data.google_account_id;
    }
}
