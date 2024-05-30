export class UpdateUserDTO {
    username: string;    
    password: string;    
    retype_password: string;    
   
    
    constructor(data: any) {
        this.username = data.username;
        this.password = data.password;
        this.retype_password = data.retype_password;
    }
}
